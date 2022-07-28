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

`@ComponentScan` 애노테이션의 `excludeFilters`속성을 사용하면 대상을 자동 등록 대상에서 제외할 수 있다.

```java
@Configuration
@ComponentScan(basePackages = {"com.example.demo.chapter5"}, 
	excludeFilters = @Filter(type=FilterType.REGEX, pattern = "spring//..*Dao"))
public class Chapter5Conf {
	...
}
```
위 코드는 `@Filter` 애노테이션의 `type` 속성값으로 `FilterType.REGEX`를 주었다. 이는 정규표현식을 사용해서 제외 대상을 지정한다는 것을 의미한다. `pattern` 속성은 `FilterType`에 적용할 값을 설정한다. 위 코드 중 일부인 `pattern = "spring.*Dao"`설정은 spring으로 시작하고 Dao로 끝나는 정규표현식을 지정했다.

```java
@Configuration
@ComponentScan(basePackages = {"com.example.demo.chapter5"}, 
	excludeFilters = @Filter(type=FilterType.ASPECTJ, pattern = "spring.*Dao"))
public class Chapter5Conf {
	...
}
```
위 코드는 `@Filter` 애노테이션의 `type` 속성값으로 `FilterType.ASPECTJ`를 주었다. 이는 정규표현식과는 다른데 이 내용은 7장(Chapter 7)에서 살펴본다. 일단 위 코드 중 일부인 `pattern = "spring.*Dao"`설정은 spring 패키지에서 Dao로 끝나는 타입을 컴포넌트 스캔 대상에서 제외한다는 것만 알고 넘어가자.

<br>

### 특정 애노테이션 스캔 제외
```java
@Retention(RUNTIME)
@Target(TYPE)
public @interface Noproduct {
}

@Retention(RUNTIME)
@Target(TYPE)
public @interface ManualBean {
}
```
만약 위 코드와 같은 `@Noproduct` `@ManualBean` 이라는 사용자 정의 애노테이션을 컴포넌트 스캔 대상에서 제외하고 싶다면 아래 코드처럼 `excludeFilters` 속성을 설정하면 된다.

```java
@Configuration
@ComponentScan(basePackages = {"com.example.demo.chapter5"}, 
	excludeFilters = @Filter(type=FilterType.ANNOTATION, 
						classes = {Noproduct.class, ManualBean.class} ))
public class Chapter5Conf {
	...
}
```

<br>

### 기본 스캔 대상
`@Component` 애노테이션을 붙인 클래스만 컴포넌트 스캔 대상에 포함되는 것은 아니다.
아래와 같은 목록은 전부 컴포넌트 스캔대상에 포함된다.

* `@Component`
* `@Controller`
* `@Service`
* `@Repository`
* `@Configuration`
* `@Aspect`

`@Aspect` 애노테이션을 제외한 나머지 애노테이션은 실제로는 **`@Component` 애노테이션에 대한 특수한 경우**이다. 예를들어 `@Controller` 애노테이션은 다음과 같다 

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

	@AliasFor(annotation = Component.class)
	String value() default "";

}
```
각 애노테이션의 용도는 관련된 각각의 장에서 살펴보자.

<br>
<br>

## 컴포넌트 스캔에 따른 충돌 처리

1. 빈 이름 충돌

spring 패키지와 spring2 패키지를 스캔하는데 두 패키지 모두 `MemberRegisterService` 클래스가 존재하고 두 클래스 모두 `@Component` 애노테이션을 붙였다면 익셉션이 발생한다.

이런 문제는 컴포넌트 스캔 과정에서 쉽게 발생할 수 있다. 컴포넌트 스캔 과정에서 서로 다른 타입인데 같은 빈 이름을 사용하는 경우가 있다면 둘 중 하나에 명시적으로 빈 이름을 지정해서 이름 충돌을 피해야 한다.

2. 수동 등록한 빈 충돌
```java
//spring 패키지에 Pet 컴포넌트
@Component
public class MyPet {
	...
}
```
```java
//설정 파일
@Configuration
@ComponentScan(basePackages = {"spring"})
public class Chapter5Conf {
	@Bean
	public MyPet myPet() {
		return new MyPet(); 
	}
}
```
위 코드의 상황은 자동 등록된 빈의 이름은 첫 글자를 소문자로 바꾼 "myPet"이다. 그러나 설정파일에서 직접 `MyPet` 클래스를 "myPet"라는 이름의 빈으로 등록했다.

다음과 같은 상황에서는 설정파일에서 **수동 등록한 빈을 우선 등록**한다. 즉 익셉션이 발생하지 않고 설정파일에서 생성된 빈만이 존재하게 된다.
