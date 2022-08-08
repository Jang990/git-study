# 프로젝트

배운내용 써먹어보기

Spring 입문에서 배운 내용도 같이 써본다.

<br>
<br>


## 오류 해결

### 기본 설정 prefix, suffix
```yml
spring:
  mvc:
    view:
      prefix: /templates/
      suffix: .html
```

처음 `application.yml`을 설정하면서 `/templates/`에 있는 `.html`파일을 찾도록 생각하고 설정했다. 근데 `templates/index.html` 뷰를 컨트롤러에서 주도록 설정했는데 404 에러가 발생했다.

<br>

스프링에서는 기본적으로 `/static/`에서 파일을 찾는 것 같다. `index.html`파일을 `/static`으로 옮기고 `prefix` 프로퍼티를 주석처리하니 `index.html`을 찾아주었다.
```yml
spring:
  mvc:
    view:
#       prefix: /templates/
      suffix: .html
```

<br>
<br>

### 패스워드 인코더

스프링 시큐리티 설정파일에 꼭 패스워드 암호화 객체를 등록하자. 등록하지 않으면 시큐리티가 처리하지 못하고 500 에러가 난다.

```java
@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return new BCryptPasswordEncoder();
}
```

<br>
<br>

### UserDetailsService를 Service로 등록

`UserDetailsService` 객체를 구현하고 꼭 `@Service` 어노테이션을 이용해서 서비스로 등록하자. 로그인이 이루어지지 않는 경우 어노테이션을 붙혔는지 확인해보자.

```java
@Service
public class SecurityUserDetailService implements UserDetailsService{
	...
}
```

<br>
<br>

### 로그인 에러 확인하기

로그인을 시도하면 `/loginForm?error` 이렇게 error가 추가되기만할 뿐 추가적인 정보가 없다.

지금 `/login`로 가면 시큐리티에서 자동으로 로그인을 진행하도록 설정했는데 error가 뜬다. 이유를 알 수가 없어서 따로 에러 메시지를 띄우도록 진행한다.

`SimpleUrlAuthenticationFailureHandler` 클래스를 상속받는 클래스(LoginFailHandler)를 만들고 `onAuthenticationFailure()` 메서드를 오버라이딩한다. 그리고 다음과 같이 오버라이딩한 부분에 소스코드에 error 메세지를 URL에 담아주도록 만들어준다.

```java
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String errorMsg;
		if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
			errorMsg = "아이디 또는 비밀번호 오류";
		} else if (exception instanceof UsernameNotFoundException) {
			errorMsg = "존재하지 않는 아이디";
		} else {
			errorMsg = "알 수 없는 오류";
		}
		
		errorMsg = URLEncoder.encode(errorMsg, "UTF-8"); //한글 인코딩 깨지는 문제 방지
		setDefaultFailureUrl("/loginForm?error=true?exception="+ errorMsg); //URL 파라미터 실어주기
		super.onAuthenticationFailure(request, response, exception);
	}
}
```

<br> 

#### 로그인 오류 예외

|               **Exception**               |                 **설명**                |
|:-----------------------------------------:|:---------------------------------------:|
| BadCredentialException                    | 비밀번호가 일치하지 않을 때 던지는 예외 |
| InternalAuthenticationServiceException    | 존재하지 않는 아이디일 때 던지는 예외   |
| AuthenticationCredentialNotFoundException | 인증 요구가 거부됐을 때 던지는 예외     |
| LockedException                           | 인증 거부 - 잠긴 계정                   |
| DisabledException                         | 인증 거부 - 계정 비활성화               |
| AccountExpiredException                   | 인증 거부 - 계정 유효기간 만료          |
| CredentialExpiredException                | 인증 거부 - 비밀번호 유효기간 만료      |


<br>

그리고 `SpringSecurityConfig` 파일에 위에서 생성한 객체를 만들어서 `failureHandler()` 메서드를 통해 등록해주면 정상 작동한다.
```java
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
  @Bean // 추가한 빈 객체
	public LoginFailHandler loginFailHandler() {
		return new LoginFailHandler();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		...
		
		http.formLogin()
			...
			.failureHandler(loginFailHandler()) // 추가한 코드코드
			...
		
	}

  ...
}
```

`http://localhost:8080/loginForm?error`이렇게 단순하게 출력되던 URL이 
`http://localhost:8080/loginForm?error=true?exception=아이디 혹은 비밀번호 오류` 이렇게 바뀌였다. 

<br>

`/loginForm`에 파라미터가 제대로 전달되는지 확인해보려고 위 코드에 다음 코드를 넣어서 `password`를 콘솔에 찍을 수 있다. 
```java
String password = (String) request.getParameter("password");
System.out.println("---------->? 비밀번호" +password);
```

<br>

~~구글 자동완성때문에 에러가 난 것이다.~~

<br>
<br>



<br>
<br>

