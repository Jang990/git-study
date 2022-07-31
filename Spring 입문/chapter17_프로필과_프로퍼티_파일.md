# Spring 5 입문 Chapter17 프로필과 프로퍼티 파일

## 프로필

개발을 진행하는 동안에는 실제 서비스 목적으로 운영중인 DB를 이용할 수는 없다. 개발하는 동안에는 개발용 DB를 따로 사용하거나 개발 PC에 직접 DB를 설치해서 사용한다. 

<br>

실제 서비스 환경에서는 웹 서버와 DB 서버가 서로 다른 장비에 설치된 경우가 만핟. 개발 환경에서 사용한 DB 계정과 실 서비스 환경에서 사용할 DB 계정이 다른 경우로 흔하다. 즉 개발을 완료한 어플리케이션을 실제 서버에 배포하려면 실 서비스 환경에 맞는 JDBC 연결 정보를 사용해야 한다.

<br>

실 서비스 장비에 배포하기 전에 설정 정보를 변경하고 배포하는 방법을 사용할 수도 있지만 이 방법은 너무 원시적이다. 이 방법은 실수하기 쉽다.

<br>

실수를 방지하는 방법은 처음부터 개발 목적 설정과 실 서비스 목적의 설정을 구분해서 작성하는 것이다. 이를 위한 스프링 기능이 프로필(profile)이다.

<br>

**프로필**을 논리적인 이름으로서 설정 집합에서 프로필을 지정할 수 있다. 스프링 컨테이너는 설정 집합 중에서 지정한 이름을 사용하는 프로필을 선택하고 해당 프로필에 속한 설정을 이용해서 컨테이너를 초기화 할 수 있다.

예를 들어 로컬 개발 환경의 DataSource 설정을 "dev", 실 서비스 환경의 DataSource 설정을 "real"로 지정한 뒤 "dev" 프로필을 사용해서 스프링 컨테이너를 초기화하여 사용할 수 있다.

다음 코드는 8장의 `DBConfig` 설정 파일에 프로필을 추가 했다.
```java
@Configuration
@Profile("dev") //프로필 추가.
public class DBConfig {
	
	@Bean(destroyMethod = "close")
	public HikariDataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
		...
		return ds;
	}
	
}
```

<br>


특정 프로필을 선택하려면 컨테이너를 초기화하기 전에 `setActiveProfiles()` 메서드를 사용해서 프로필을 선택해야 한다.

```java
AnnotaionConfigApplicationContext context = new AnnotaionConfigApplicationContext();
context.getEnvironment().setActiveProfiles("dev");
context.register(MemberConfig.class, DsDevConfig.class, DsRealConfig.class);
context.refresh();
```

`getEnvironment()` 메서드는 스프링 실행 환경을 설정하는데 사용되는 `Environment`를 리턴한다. 이 `Environment`의 `setActiveProfiles()` 메서드를 사용해서 사용할 프로파일을 선택할 수 있다. 위 코드는 `"dev"`를 값으로 주었으므로 `"dev"` 프로필에 속한 설정이 사용된다.

따라서 `DsDevConfig` 클래스와 `DsRealConfig` 클래스에 정의 되어 있는 "dataSource" 중에서 `"dev"` 프로필에 속하는 `DsDevConfig`에 정의된 "dataSource" 빈을 사용한다.

<br>

프로필을 사용할 때 주의할 점은 설정 정보를 전달하기 전에 어떤 프로필을 사용할지 지정해야 한다는 점이다. 위 코드를 보면 `setActiveProfiles()` 메서드로 `"dev"` 프로필을 사용한다고 설정한 뒤에 `register()` 메서드로 설정 파일 목록을 지정했다. 그런 뒤 `refresh()` 메서드를 실행해서 컨테이너를 초기화 했다. 이 순서를 지키지 않고 프로필을 선택하기 전에 설정 정보를 먼저 전달하면 프로필을 지정한 설정이 사용되지 않기 때문에 설정을 읽어오는 과정에서 빈을 찾지 못해 익셉션이 발생한다.

<br>
<br>
