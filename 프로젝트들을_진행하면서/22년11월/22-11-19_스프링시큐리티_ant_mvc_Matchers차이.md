# 22년 11월 19일

## `.antMatchers(...)` vs `.mvcmathcers(...)`

`antMatchers`와 `mvcmathcers`는 다음과 같은 차이가 있다.

```java
// 1번
.antMatchers("/secured").authenticated()
/*  정확한 /secured URL 만 일치  */

// 2
.mvcMatchers("/secured").authenticated()
/*  /secured 뿐만 아니라 /secured/, /secured.html,/secured.xyz 등등이 일치  */
```

1번의 경우에는 /secured 로 요청이 들어오는 경우 인증되지 않은 사용자는 403에러를 보게 된다. <br>
하지만 인증되지 않은 사용자가 /secured/ 로 요청하는 경우에는 정상처리되는 것을 확인할 수 있다. 
<br>
이렇게 특정 엔드포인트를 꼭 찝어서 처리를 하는 것을 알 수 있다.

2번의 경우에는 /secured 로 요청이 들어오는 경우 인증되지 않은 사용자는 또한 403 에러를 볼 수 있다.<br>
하지만 1번과 다르게 /secured/ /secured.html 등등의 요청 모두 403 에러를 볼 수 있다.<br>
즉 특정 엔트리포인트를 전부 싸잡아 처리한다.

<br>

물론 .antmathcers에 와일드카드를 사용하여 다음과 같이 사용할 수 있다.
```java
.antMatchers("/secured/**").authenticated()
```

<br>
<br>

### 참고
https://shirohoo.github.io/spring/spring-security/2022-03-31-mvcMatchers/

https://stackoverflow.com/questions/50536292/difference-between-antmatcher-and-mvcmatcher


