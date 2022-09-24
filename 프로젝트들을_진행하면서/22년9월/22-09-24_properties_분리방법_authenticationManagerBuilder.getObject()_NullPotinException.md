# 22년 09월 24일


## 개발환경에 따라 `application.properties` 분리하여 사용하기

### properties 분리 방법

개발을 하며 로컬 서버에서 구동할 때와 개발서버에서 구동할 때, 실 서버에서 구동할 경우 설정 변수 값이 다르게 적용되는 경우가 많다.

이럴 때 개발환경에 따라 각각의 프로퍼티를 사용하면 다르게 사용되는 값들을 하나하나 다 바꿔주는 번거로운 일을 하지 않을 수 있다.

* 주의할 점으로는 새로 생성하는 `properties`는 **application-{이름}.properties** 라는 규칙을 따라야 한다.

<br>

### 환경에 맞는 properties를 불러와서 사용하는 방법

`application-prod.properties` 라는 properties파일을 생성했다면 다음과 같은 명령어로 properties를 사용할 수 있다.
```
java -jar -Dspring.profiles.active=prod (어플리케이션 파일명).jar
```

참고: https://wildeveloperetrain.tistory.com/8

<br>
<br>

## `authenticationManagerBuilder.getObject()` is null

다음 코드를 실행하는데 이상하게 `authenticationManagerBuilder.getObject()` 부분에서 `NullPotinException`이 발생했다.
```java
Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken); 
```

원인은 내가 개선된 JWT로 바꾸면서 이전 JWT에서 쓰던 `UserDetailsService`가 충돌이 되면서 발생한 것이다. 이전에 쓰던 `UserDetailsService`를 구현하던 클래스를 삭제하니 정상 작동을 한다.

<br>

참고: https://www.inflearn.com/questions/349502