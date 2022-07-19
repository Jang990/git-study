# Spring 5 입문 Chapter 5 컴포넌트 스캔

컴포넌트 스캔은 스프링이 직접 클래스를 검색해서 빈으로 등록해주는 기능이다.

설정 클래스에 빈으로 등록하지 않아도 원하는 클래스를 빈으로 등록할 수 있으므로 컴포넌트 스캔을 사용하면 설정코드가 크게 줄어든다.

<br>

## @Component 애노테이션으로 스캔 대상 지정

**`@Component` 애노테이션은 해당 클래스를 스캔 대상으로 표시한다.**

설정클래스에 빈으로 등록하지 않아도 원하는 클래스를 빈으로 등록할 수 있으므로 컴포넌트 스캔 기능을 사용하면 설정 코드가 크게 줄어든다.

<br>

아래 코드와 같이 클래스에 `@Component` 애노테이션을 붙이면 된다.
```java
@Component
public class MyPet {
	private Pet pet = new Pet();
    ...
}
```

<br>

아래와 같이 `@Component` 애노테이션에 값을 주었는지에 따라 빈으로 등록할 때 사용할 이름이 결정된다.
```java
@Component("notMyPet")
public class YourPet {
	private Pet pet = new Pet();
    ...
}
```

만약 `MyPet` 클래스와 같이 `@Component` 애노테이션에 값을 주지 않았다면 첫 글자를 소문자로 바꾼 "myPet" 이라는 이름을 빈 이름으로 사용한다.<br>
하지만 `YourPet` 클래스와 같이 `@Component` 애노테이션에 "notMyPet" 이라는 값을 주면 "notMyPet" 이라는 이름을 빈 이름으로 사용한다.

<br><br>

## @ComponentScan 애노테이션으로 스캔 설정

`@Component` 애노테이션을 붙인 클래스를 스캔해서 스프링 빈으로 등록하려면 설정 클래스에 `@ComponentScan` 애노테이션 애노테이션을 적용해야 한다.
<br>

`@Component` 애노테이션과 `@ComponentScan` 애노테이션을 이용하여 기존 설정 파일의 소스코드를 줄여보겠다.

<br>

 * 기존 코드들
```java
 //기존 설정파일
@Configuration
public class Chapter5Conf {
	@Bean
	public Pet pet1() {
		return new Pet();
	}
	
	@Bean
	public Person person() {
		return new Person();
	}
}
```
```java
 //클래스 소스코드들
public class Person {
	@Autowired
	private Pet pet;
    ...
}
/* ==================== */
public class Pet {
	private String name;
    ...
}
```
```java
 //실행 소스코드
ApplicationContext ctx = new AnnotationConfigApplicationContext(Chapter3Conf.class);
Person jang = ctx.getBean("person", Person.class);
```

<br>

* 변경 코드들
```java
 //변경 설정파일
@Configuration
//com.example.demo.chapter5 패키지 위치를 스캔
@ComponentScan(basePackages = {"com.example.demo.chapter5"})
public class Chapter5Conf {
    //전부 주석 처리
//  @Bean
//  public Pet pet1() {
//  	return new Pet();
//  }
//  @Bean
//  public Person person() {
//  	return new Person();
//  }
}
```
```java
 //클래스 소스코드들

@Component("jang") //애노테이션 추가
public class Person {
	@Autowired
	private Pet pet;
    ...
}
/* ==================== */
@Component //애노테이션 추가
public class Pet {
	private String name;
    ...
}
```
```java
 //실행 소스코드
ApplicationContext ctx = new AnnotationConfigApplicationContext(Chapter3Conf.class);
Person jang = ctx.getBean("jang", Person.class);
```

변경 후의 코드는 `com.example.demo.chapter5` 위치에 있는 패키지의 컴포넌트를 스캔하여 빈으로 만들어준다. 그래서 설정 파일에서 일일이 빈을 만들 필요가 없기 때문에 소스코드가 줄어들었다.

<br>

## 스캔 대상에서 제외하거나 포함하기

`@Component` 애노테이션
`@ComponentScan` 애노테이션