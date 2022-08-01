# Spring 5 입문 Chapter8 DB 연동

JDBC API로 트랜잭션을 처리하려면 `commit()`과 `rollback()` 메서드를 이용해서 트랜잭션을 커밋하거나 롤백해야 한다.

스프링을 사용하면 트랜잭션을 적용하고 싶은 메서드에 다음과 같이 `@Transactional` 애노테이션을 붙이기만 하면 된다. 커밋과 롤백 처리는 스프링이 알아서 처리하므로 코드를 작성하는 사람은 트랜잭션 처리를 제외한 핵심 코드만 집중해서 작성하면 된다.

```java
@Transactional
public void insert(Member member) {
    ...
}
```

<br>

### **커넥션 풀**이란?
자바 프로그램에서 DBMS로 커넥션을 생성하는 시간은 매우 길기 때문에 DB 커넥션을 생성하는 시간은 전체 성능에 영향을 줄 수 있다. 또한 동시 접속하는 사용자 수가 많으면 사용자마다 DB 커넥션을 생성해서 DBMS에 부하를 준다.

최초 연결에 따른 응답 속도 저하와 동시 접속자가 많을 때 발생하는 부하를 줄이기 위해 사용하는 것이 커넥션 풀이다. 커넥션 풀은 일정 개수의 DB 커넥션을 미리 만들어두는 기법이다. DB 커넥션이 필요한 프로그램은 커넥션 풀에서 커넥션을 가져와 사용한 뒤 커넥션을 다시 풀에 반납한다. 커넥션을 미리 생성해두기 때문에 커넥션을 사용하는 시점에서 커넥션을 생성하는 시간을 아낄 수 있다.

실제 서비스 운영 환경에서는 매번 커넥션을 생성하지 않고 커넥션 풀을 사용해서 DB 연결을 관리한다. DB 커넥션 풀 기능을 제공하는 모듈로는 Tomcat JDBC, HikariCP, DBCP, c3p0 등이 존재한다.

<br><br>

~~JDBC, JDBC Template 사용법은 공부 목표가 아니기 때문에 따로 정리하지 않고 책을 읽고 넘어간다...(MyBatis나 JPA 공부. 나중에 필요하면 살펴볼 것.)~~

~~스프링 부트를 사용하기 때문에 스프링에 커넥션 풀 설정 부분은 넘어간다.~~

먼저 Spring 설정을 살펴보고 Spring Boot에서는 어떻게 설정하는지 살펴본다.

<br><br>

## DataSource 설정

책에서는 JDBC에 DataSource를 이용한 방식을 예시로 사용한다.

JDBC API는 `DriverManager`외에 `DataSource`를 이용해서 DB 연결을 구하는 방법을 정의하고 있다. `DataSource`를 사용하면 다음 방식으로 `Connection`을 구할 수 있다.

```java
Connection conn = null;
try {
    //dataSource는 생성자나 설정 메서드를 이용해 주입받는다.
    conn = dataSource.getConnection();
}
```

DB 연동에 사용할 `DataSource`를 스프링 빈으로 등록하고 DB 연동 기능을 구현한 빈 객체는 `DataSource`를 주입받아 사용한다.

책에서는 Tomcat JDBC에 DataSource를 사용하지만 나는 내가 쓰는 `HikariDataSource`를 사용함.

* 스프링에서의 설정
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DBConfig {
	
	@Bean(destroyMethod = "close")
	public HikariDataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setJdbcUrl("jdbc:mysql//localhost:3306/testTable");
		ds.setUsername("test");
		ds.setPassword("test");
		ds.setValidationTimeout(10000);
		ds.setConnectionTimeout(10000);
		return ds;
	}
	
}
```
* 스프링부트에서의 설정
```properties
# application.properties 파일 내용
# 내가 프로젝트에 직접 쓰고 있는 properties 설정 파일 내용 중 일부
spring.datasource.hikari.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
spring.datasource.hikari.jdbc-url=jdbc:log4jdbc:oracle:thin:@127.0.0.1:1521:xe
spring.datasource.hikari.username=test
spring.datasource.hikari.password=test
spring.datasource.hikari.connection-timeout=10000
spring.datasource.hikari.validation-timeout=10000
```

<br><br>

### 커넥션의 상태

커넥션 풀에 커넥션을 요청하면 해당 커넥션은 활성(active) 상태가 되고, 커넥션을 다시 커넥션 풀에 반환하면 유휴(idle) 상태가 된다. `DataSource`에서 `getConnection()`을 실행하면 커넥션 풀에서 커넥션을 가져와 커넥션이 활성 상태가 된다. 반대로 커넥션을 종료(close)하면 커넥션은 풀로 돌아가 유휴 상태가 된다. 

`Connection`을 구하고 종료하는 코드를 명시적으로 보여주기 위해 다음과 같이 `Connection` 관련 코드와 `Statement`, `ResultSet` 코드를 별도의 `try` 블록으로 나눴다.

```java
public class DbQuery {
	@Autowired
	private HikariDataSource dataSource;
	
	public int count() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection(); // 풀에서 구함
			try(Statement stmt = conn.createStatement(); 
					ResultSet rs = stmt.executeQuery("Select count(*) From Member")) {
				rs.next();
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if(conn != null) 
				try { conn.close(); /*풀에 반환*/ } 
				catch (SQLException e2) { }
		}
	}
}
```
위 코드 중 `conn = dataSource.getConnection();` 이 코드는 DataSource에서 커넥션을 구하는데 이때 풀에서 커넥션을 가져온다. 이 시점에서 커넥션 `conn`은 활성 상태이다. 커넥션 사용이 끝나고 `conn.close();` 코드를 사용해 커넥션을 종료하면 실제 커넥션을 끊지 않고 풀에 반환한다. 풀에 반환된 커넥션은 다시 유휴 상태가 된다.

<br>

커넥션 풀을 사용하는 이유는 성능 때문이다. 매번 새로운 커넥션을 생성하면 그때마다 연결 시간이 소모된다. 커넥션 풀을 사용하면 미리 커넥션을 생성했다가 필요할 때 커넥션을 꺼내 쓰므로 커넥션을 구하는 시간이 줄어 전체 응답 시간도 짧아진다. 

<br>

커넥션 풀에 생성된 커넥션은 지속적으로 재사용된다. 그런데 한 커넥션이 영원히 유지되는 것은 아니다. DBMS 설정에 따라 일정 시간 내에 쿼리를 실행하지 않으면 연결을 끊기도 한다. 예를 들어 DBMS에서 5분 동안 쿼리를 실행하지 않으면 DB 연결을 끊도록 설정했는데, 커넥션 풀에 특정 커넥션이 5분 넘게 유휴 상태로 존재했다고 하자. 이 경우 DBMS는 해당 커넥션의 연결을 끊지만 커넥션은 여전히 풀 속에 남아있다. 이 상태에서 해당 커넥션을 풀에서 가져와 사용하면 연결이 끊어진 커넥션이므로 익셉션이 발생하게 된다.

<br>

이런 상황을 방지하려면 커넥션 풀의 커넥션이 유효한지 주기적으로 검사를 하도록 설정을 해야한다. (책에서는 설정클래스에서 `ds.setMinEvictableldleTimeMillis(1000 * 60 * 3)` 이런식으로 설정했다.)

<br>

커넥션 풀의 여러 설정은 책에서 설명하나 너무 많으니 필요할 때 찾아보자.

<br><br>


<br>

## 트랜잭션 처리

은행에서 "A사용자가 B사용자에게 1000원 이체"라는 과정을 다음과 같이 실행한다고 가정하자.

```
A사용자에게 1000원 출금
B사용자에게 1000원 입금
```

A사용자가에게 1000원을 출금하고 B사용자에게 1000원을 입금하는 과정을 성공한다면 저장(commit)해야 한다.

하지만 개발자의 실수로 입금 소스코드에 오류가 있거나 B사용자의 계좌가 없어서 입금 실패했다면 A사용자 계좌에서 1000원을 출금한 것도 취소(rollback)해주어야 할 것이다.

이렇게 두 개 이상의 작업을 한 작업으로 실행해야 할 때 사용하는 것이 **트랜잭션**이다.

위에 글로 작성한 상황을 다음과 같은 소스코드로 작성했다.
```java
//글로는 단순화 했지만 A출금()과 B입금() 메서드에는 복잡한 소스코드와 쿼리문이 들어간다고 가정한다.
Connection conn;
...
try {
    ...
    A출금(money);
    B입금(money);
    conn.commit();
}
catch(SQLException ex) {
    if(conn != null) conn.rollback();
}
...
```
위와 같은 방식은 직접 트랜잭션 범위를 관리하기 때문에 개발자가 트랜잭션을 커밋하는 코드나 롤백하는 코드를 누락하기 쉽다. 또한 구조적인 중복이 반복되는 문제도 있다. 스프링이 제공하는 트랜잭션 기능을 사용하면 중복이 없는 매우 간단한 트랜잭션 범위를 지정할 수 있다.

<br>

### @Transactional을 이용한 트랜잭션 처리

트랜잭션 범위에서 실행하고 싶은 메서드에 `@Transactional` 애노테이션만 붙이면 된다.

```java
@Transactional
public void 이체(int money) {
    A입금(money);
    B출금(money);
} 
```

스프링은 `@Transactional` 애노테이션이 붙은 `이체()` 메서드에 `A입금(money)` `B출금(money)`은 한 트랜잭션에 묶인다.


`@Transactional` 애노테이션이 제대로 동작하기 위한 설정
1. PlatformTransactionManager
2. `@Trasaction` 어노테이션 활성화 설정

```java
@Configuration
@EnableTransactionManagement //2번 설정
public class AppCtx {
	@Bean //1번 설정
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}

    @Bean(destroyMethod = "close")
	public DataSource dataSource() {
		...
	}
	...
}
```

`PlatformTransactionManager`은 스프링이 제공하는 인터페이스입니다. JDBC는 `DataSourceTransactionManager` 클래스를 `PlatformTransactionManager`로 사용하게 됩니다. 또한 connection 설정을 위해 `DataSource`를 주입하여 사용하게 됩니다.

@EnableTransactionManagement 어노테이션은 @Transactional 어노테이션을 찾아 트랜잭션 범위를 활성화하는 기능을 하게 됩니다.

트랜잭션 처리를 위한 설정을 완료하면 트랜잭션 범위에서 실행하고 싶은 스프링 빈 객체의 메서드에 `@Transactional` 애노테이션을 붙이면 된다.

<br>

스프링은 `@Transactional` 애노테이션을 이용해 트랜젝션을 처리하기 위해 내부적으로 AOP를 사용한다.

<br>

## @Transactional 적용 메서드의 롤백 처리

`@Transactional` 애노테이션을 적용한 메서드에서 별도 설정을 하지 않을 경우 `RuntimeException`을 상속받는 예외가 발생할 경우 rollback 처리를 한다.

만약 `RuntimeException`을 상속하고 있지 않은 `SQLException`이 발생하는 경우에도 rollback 처리를 하고 싶다면 다음과 같이 `rollbackFor`속성을 설정하면 된다.

```java
@Transactional(rollbackFor = SQLException.class)
public void someMethod() {
    ...
}
```
여러 익셉션 타입을 지정하고 싶다면 `{SQLException.class, IOException.class}`와 같이 배열로 지정하면 된다.

rollback과 반대 설정을 제공하는 것이 `noRollbackFor`속성이다. 이 속성은 익셉션이 발생해도 롤백시키지 않고 커밋할 익셉션 타입을 지정할 때 사용한다.

<br>

### @Transactional의 주요 속성

간혹 사용할 때가 있으니 있다는 것만 알고 가자.

* value: PlatformTransactionManager 빈 이름 지정
* propagation: 트랜잭션 전파 타입 지정
* isolation: 트랜잭션 격리 레벨 지정
* timeout: 제한시간 지정

## 트랜잭션 전파

1. 
```java
public class Service1 {
    private Service2 service2;

    @Transactional
    public void workAnyThing() {
        service2.any();
    }
    ...
}
/* ================ */
public class Service2 {
    @Transactional
    public void any() {
        doAnyThing();
    }
    ...
}
```
`Service1`에서는 `Service2`에 `any()` 메서드를 사용하고 있다. 하지만 `workAnyThing()`, `any()` 메서드 모두 `@Transactional` 애노테이션이 붙어있다. 그렇다면 `any()` 메서드는 `workAnyThing()` 메서드와 같은 트랜잭션이 아닌 새로운 트랜잭션에서 실행될까? 

아니다. 같은 트랜잭션에서 실행된다.

하지만 만약 `any()` 메서드에 `@Transactional` 애노테이션에 `propagation` 속성값이 `REQUIRES_NEW`라면 기존 트랜잭션 존재 여부에 상관없이 항상 새로운 트랜잭션을 시작한다.

<br>

2. 
```java
@Transactional
public void 이체(int money) {
    A입금(money);
    B출금(money);
} 

//@Transactional 없음
public void A입금(){
    ...
}

//@Transactional 없음
public void B출금(){
    ...
}
```

초반부에 살펴본 위 코드 또한 트랜잭션 전파가 되었다. `@Transactional` 애노테이션이 `A입금()`, `B출금()` 메서드에 존재하지 않지만 하나의 트랜잭션으로 처리가 된다. 즉 `A입금()`, `B출금()` 메서드에서 `RuntimeException`이 발생한다면 rollback 처리된다.

<br>