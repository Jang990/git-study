# 22년 11월 23일

## OAuth 세션 처리

프로젝트가 급해서 정리는 못하고 참고한 글들만 일단 적는다.

### 참고 글
https://spring.io/guides/tutorials/spring-boot-oauth2/

https://github.com/callicoder/spring-boot-react-oauth2-social-login-demo/tree/master/spring-social

https://velog.io/@vencott/05-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0%EC%99%80-OAuth-2.0%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B01

https://velog.io/@vencott/05-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0%EC%99%80-OAuth-2.0%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B02


## properties 나누기

위에 참고글중 https://velog.io/@vencott/05-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0%EC%99%80-OAuth-2.0%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B01 에 나와있는 내용이다.

`application-xxx.properties`로 만들면 `xxx`라는 이름의 profile이 생성되어 이를 통해 설정 파일을 나누어 관리할 수 있다. 

1. 외부에 노출되지 않아야 하는 `application-oauth.properties` 파일을 만든다.
```properties
#########################
# OAuth 설정
#########################
 # Google
spring.security.oauth2.client.registration.google.client-id= ...
spring.security.oauth2.client.registration.google.client-secret= ...
spring.security.oauth2.client.registration.google.scope=profile,email

...
```
<br>

2. github에 올라가서 외부에 노출되지 않도록 `gitignore`에 등록 해준다.
```
...

# 올라가지 말아야 할 파일
application-oauth.properties
```

<br>

3. 기존 `application.properties` 설정 파일이 `application-oauth.properties` 설정파일을 포함하도록 설정한다.
```properties
# 기존 설정파일
...

# application-oauth.properties 설정을 포함하도록 설정
spring.profiles.include=oauth
```

<br>


