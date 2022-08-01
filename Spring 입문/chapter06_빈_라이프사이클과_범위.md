# Spring 5 입문 Chapter 5 빈 라이프사이클과 범위

<br>

## 컨테이너 초기화와 종료
스프링 컨테이너는 초기화와 종료라는 라이프 사이클을 갖는다.

```java
//2장 소스코드

//1. 설정 정보를 이용해 빈 객체 생성
AbstractApplicationContext ctx = new AnnotaionConfigApplicationContext(AppContext.class);

//2. 컨테이너에서 빈 객체를 구해서 사용
Greeter g = ctx.getBean("greeter", Greeter.class);
...

//3. 컨테이너 종료
ctx.close(); //close()는 AbstractApplicationContext에 정의되어 있음
```

`AnnotaionConfigApplicationContext` 생성자를 이용하여 컨텍스트 객체를 생성하는데 이 시점에서 스프링 컨테이너를 초기화한다. 스프링 컨테이너는 설정 클래스에서 정보를 읽어와 알맞은 빈 객체를 생성하고 각 빈을 연결(의존주입)하는 작업을 수행한다.

컨테이너 초기화가 완료되면 컨테이너를 사용할 수 있다. 컨테이너를 사용한다는 것은 `getBean()`과 같은 메서드를 이용해 컨테이너에 보관된 빈 객체를 구한다는 것을 뜻한다.

컨테이너 사용이 끝나면 컨테이너를 종료한다. 컨테이너를 종료할 때 사용하는 메서드는 `close()` 메서드이다.

컨테이너를 초기화하고 종료할 때에는 다음 작업도 함께 수행한다.
* 컨테이너 초기화 -> 빈 객체의 생성, 의존 주입, 초기화
* 컨테이너 종료 -> 빈 객체의 소멸

<br>

## 스프링 빈 객체의 라이프사이클

빈 객체의 라이프 사이클 다음과 같다.
1. 객체 생성
2. 의존 설정
3. 초기화
4. 소멸

스프링 컨테이너를 초기화할 때 스프링 컨테이너는 가장 먼저 빈 객체를 생성하고 의존을 설정한다. 의존 자동 주입을 통한 의존 설정이 `2. 의존 설정` 시점에서 수행된다. 모든 의존 설정이 완료되면 빈 객체의 초기화를 수행한다. 빈 객체를 초기화하기 위해 빈 객체의 지정된 메서드를 호출한다. 스프링 컨테이너를 종료하면 스프링 컨테이너는 빈 객체의 소멸을 처리한다. 이때도 지정한 메서드를 호출한다.

### 1. 빈 객체의 초기화와 소멸: 스프링 인터페이스

* org.springframework.beans.factory.InitializingBean
* org.springframework.beans.factory.DisposableBean

빈 객체를 생성한 뒤 초기화 과정이 필요하면 `InitializingBean` 인터페이스를 상속하고 `afterPropertiesSet()` 메서드를 알맞게 구현하면 된다.

빈 객체의 소멸 과정이 필요하면 `DisposableBean` 인터페이스를 상속하고 `destory()` 메서드를 알맞게 구현하면 된다.

예를 들어 채팅 클라이언트는 시작할 때 서버와 연결을 생성하고 종료할 때 연결을 끊는다. 이때 작업을 초기화 시점과 소멸 시점에 수행하면 된다.

<br>

다음 코드들은 인터페이스들을 구현한 예시이다.
```java
//설정 코드는 생략.
//객체 코드
@Component
public class ChatClient implements InitializingBean, DisposableBean{
	private String user;
	
	public ChatClient() {
		user = "jang"; 
		System.out.println("생성자");
	}
	
    public String getUser() {
		return user;
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("객체 소멸");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("객체 생성");
	}
	
}
```

```java
//메인 코드
public class Chapter6_Main {
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Chapter6Conf.class);
		ChatClient c = ctx.getBean(ChatClient.class);
        System.out.println(c.getUser());
		ctx.close();
	}
}
```
<br>

* 결과
```
생성자
객체 생성
jang
...
객체 소멸
```

만약 메인 코드에 `ctx.close()` 코드가 없다면 컨테이너의 종료 과정을 수행하지 않기 때문에 빈 객체의 소멸 과정(`destroy()`)도 실행되지 않는다

<br>

### 2. 빈 객체의 초기화와 소멸: 커스텀 메서드

`InitializingBean`, `DisposableBean` 두 인터페이스를 구현하기 싫다면 스프링 설정에서 직접 메서드를 지정해주면 된다. 

다음과 같이 `@Bean` 애노테이션에서 `initMethod`, `destroyMethod` 속성을 사용해서 초기화 메서드와 소멸 메서드의 이름을 지정하면 된다.

```java
//설정 코드
@Configuration
@ComponentScan(basePackages = {"com.example.demo.chapter6"})
public class Chapter6Conf {
	@Bean(initMethod = "init", destroyMethod = "close")
	public ChatClient2 chatClient2() {
		ChatClient2 c = new ChatClient2();
		return c;
	}
}
```


```java
//객체 코드
public class ChatClient2 {
	private String user;
	
	public ChatClient2() {
		user = "jang"; 
		System.out.println("생성자");
	}
	
	public String getUser() {
		return user;
	}

	public void init() {
		System.out.println("객체 생성");
	}

	public void close() {
		System.out.println("객체 소멸");
	}
}
```

```java
//메인 코드
public class Chapter6_Main {
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Chapter6Conf.class);
		ChatClient2 c = ctx.getBean(ChatClient2.class);
		System.out.println(c.getUser());
		ctx.close();
	}
}
```

* 결과
```
생성자
객체 생성
jang
...
객체 소멸
```

<br><br>

위 코드와 같이 사용하지 않고 다음과 같이 설정 코드에서 직접 초기화 메서드를 부르는 방법도 있다.
```java
@Bean(/*initMethod = "init",*/destroyMethod = "close")
public ChatClient2 chatClient2() {
	ChatClient2 c = new ChatClient2();\
    c.init();
	return c;
}
```

여기서 주의할 점은 초기화 관련 메서드를 위 코드와 같이 주석처리하지 않거나, `InitializingBean`, `DisposableBean`를 구현한 후 설정코드에서 `afterPropertiesSet()`, `destory()`를 직접 호출하여 메서드가 2번 호출되지 않도록 주의하여야 한다.

`afterPropertiesSet()`, `destory()`는 파라미터가 없어야 한다. 파라미터가 존재할 경우 익셉션을 발생시킨다.

## 빈 객체의 생성과 관리 범위

```java
Client c1 = ctx.getBean("client", Client.class);
Client c2 = ctx.getBean("client", Client.class);
// c1 == c2 -> true
```
앞장에서 말했듯 위 코드와 같이 빈 객체는 기본적으로 싱글톤 범위를 갖는다.

사용 빈도가 낮긴 하지만 빈 객체를 구할 때마다 새로운 객체를 생성하도록 할 수 있다. 아래 코드와 같이 `@Scope`를 프로토타입으로 지정해주면 된다.
```java
@Bean
@Scope("prototype")
public Client client() {
    ...
}
```

프로토타입 범위를 갖는 빈은 완전한 라이프사이클을 따르지 않는다는 점을 주의하자. 컨테이너를 종료한다고 해서 생성한 프로토타입 빈 객체의 소멸 메서드를 실행하지 않는다. 따라서 프로토타입 범위의 빈을 사용할 때에는 빈 객체의 소멸 처리를 코드에서 직접해야 한다.
