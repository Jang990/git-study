# Inflearn JWT 강의

강의 출처: https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt/unit/65762?category=questionDetail&tab=community

강의 github: https://github.com/SilverNine/spring-boot-jwt-tutorial

<br>

강의에서 생성 순서

entity -> `TokenProvider` -> `JwtFilter` -> `JwtSecurityConfig` -> `JwtAuthenticationEntryPoint` -> `SecurityConfig` -> dto -> repository -> `CustomUserDetailsService` -> `AuthController`

<br>
<br>

## `Onceperrequestfilter` vs `GenericFilterBean`

```
프로젝트에 도달하는 즉시 한 번 인증하고 권한을 부여해야 합니다. 그런 다음 모든 것이 정상인 것 같으면 이 요청과 이 컨텍스트의 다른 모든 요청이 필터를 다시 거칠 필요 없이 API에 도달하도록 허용될 수 있습니다. OncePerRequestFilter는 이 인증 프로세스가 한 번만 발생하는지 확인합니다. 이것을 사용하지 않으면 내부적으로 프로젝트의 다른 API에 요청할 때마다 모든 API에 동일한 보안 필터가 있으므로 동일한 인증이 다시 발생합니다.
...
일부 보안 필터 작업이 요청에 대해 한 번만 수행되어야 한다는 것입니다. 따라서 GenericFilterBean보다 OncePerRequestFilter가 필요합니다.
```

출처: https://stackoverflow.com/questions/50410901/genericfilterbean-vs-onceperrequestfilter-when-to-use-each

<br>

`GenericFilterBean`
이 Filter를 조금 더 확장하여 스프링에서 제공하는 필터가 있는데 그것이 바로 GenericFilterBean이다.

`GenericFilterBean`은 기존 Filter에서 얻어올 수 없는 정보였던 Spring의 설정 정보를 가져올 수 있게 확장된 추상 클래스이다.

출처: https://minkukjo.github.io/framework/2020/12/18/Spring-142/

<br>
<br>

## ResponseEntity란

Spring Framework에서 제공하는 클래스 중 HttpEntity라는 클래스가 존재한다. 이것은 HTTP 요청(Request) 또는 응답(Response)에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스이다.

출처: [ResponseEntity란 무엇인가?](https://devlog-wjdrbs96.tistory.com/182)

<br>
<br>

## `AccessDeniedHandler` 와 `AuthenticationEntryPoint`

`AccessDeniedHandler`는 서버에 요청을 할 때 액세스가 가능한지 권한을 체크후 액세스 할 수 없는 요청을 했을시 동작된다.

`AuthenticationEntryPoint`는 인증이 되지않은 유저가 요청을 했을때 동작된다.

<br>
<br>

## `@PostConstruct` vs`InitializingBean`

1. `@PostConstruct`
2. `InitializingBean`의 `afterPropertiesSet()`
3. `@Bean(initmethod="..")`

출처에 글에서는 이 순서로 우선순위가 있다고 말한다. 

그리고 다음과 같은 글이 달려있다.
```
시스템 구성을 선호하는 방식에 따라 다르며 이는 개인 선택의 문제입니다.  ...
```

https://stackoverflow.com/questions/8519187/spring-postconstruct-vs-init-method-attribute

## 오류 - 메이븐에서 JJWT
JDK 10이하의 환경에서 RSASSA-PSS(PS256, PS384, PS512) 알고리즘을 사용하고자 하는 경우 주석부분을 해제하면 된다. JDK 11이상의 경우 자동으로 지원하기 때문에 명시할 필요는 없다.

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.2</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.2</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId> <!-- or jjwt-gson if Gson is preferred -->
    <version>0.11.2</version>
    <scope>runtime</scope>
</dependency>
<!-- Uncomment this next dependency if you are using JDK 10 or earlier and you also want to use 
     RSASSA-PSS (PS256, PS384, PS512) algorithms.  JDK 11 or later does not require it for those algorithms:
<dependency>
    <groupId>org.bouncycastle</groupId>
    <artifactId>bcprov-jdk15on</artifactId>
    <version>1.60</version>
    <scope>runtime</scope>
</dependency>
-->

```

출처: https://budnamu.tistory.com/113 [버드나무 IT:티스토리]

<br>
<br>

## 오류 - executing DDL "alter table user drop foreign key"

JPA에 `create_drop`설정을 `update`로 변경

https://galid1.tistory.com/610


