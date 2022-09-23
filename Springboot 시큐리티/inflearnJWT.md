# Inflearn JWT 강의

강의 출처: https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt/unit/65762?category=questionDetail&tab=community

강의 github: https://github.com/SilverNine/spring-boot-jwt-tutorial

<br>

강의에서 생성 순서

entity -> `TokenProvider` -> `JwtFilter` -> `JwtSecurityConfig` -> `JwtAuthenticationEntryPoint` -> `SecurityConfig` -> dto -> repository -> `CustomUserDetailsService` -> `AuthController`

`SecurityUtil` -> `UserService` -> 

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

<br>
<br>

## Postman에서 토큰 저장
`http://localhost:8080/api/authenticate`의 주소에서 토큰을 받는데 이걸 받아서 복붙으로 테스트를 하는 것은 매우 귀찮다. 다음과 같이 설정하면 편하게 토큰을 사용할 수 있다.

Tests 탭에 들어가서 다음 코드를 붙혀넣고 `{{}}`를 이용해서 값을 사용할 수 있다.
```js
var jsonData = JSON.parse(responseBody)
pm.globals.set("jwt_tutorial_token", jsonData.token);
```

`{{jwt_tutorial_token}}`이렇게 값으로 넣어서 사용하면 된다는데 작동을 하지 않는다. 추가적인 설정이 필요한 것 같다.

<br>
<br>

## `SecurityContextHolder`, `SecurityContext` ...

`SecurityContextHolder` - 누가 인증했는지에 대한 정보들을 저장하고 있습니다.

`SecurityContext` - 현재 인증한 user에 대한 정보를 가지고 있습니다.

`Authentication` - `SecurityContext`의 user, 인증 정보를 가지고 있으며, `AuthenticationManager`에 의해 제공됩니다.

`GrantedAuthority` - 인증 주체에게 부여된 권한 (roles, scopes, etc.)

`AuthenticationManager` - Spring Security의 필터에서 인증을 수행하는 방법을 정의하는 API입니다.

<br>

`SecurityContextHolder`는 시큐리티가 인증한 내용들을 가지고 있으며, **`SecurityContext`를 포함**하고 있고 `SecurityContext`를 현재 스레드와 연결해 주는 역할을 합니다.

```java
SecurityContextHolder.getContext(); // SecurityContext 반환
```

`SecurityContextHolder`은 `ThreadLocal`을 이용하여 인증 관련된 정보(principal, credentials, authenticated)를 저장합니다.

`ThreadLocal`에 정보를 저장하여 관리하기 때문에 동일 스레드에서는 항상 같은 인증 정보로 접근 가능합니다.

https://00hongjun.github.io/spring-security/securitycontextholder/

<br>
<br>


