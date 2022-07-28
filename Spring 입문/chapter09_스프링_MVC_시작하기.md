# Spring 5 입문 Chapter9 MVC 시작하기

스프링을 사용하는 이유는 여러 이유가 있지만 한 가지 이유를 꼽자면 스프링이 지원하는 웹 MVC 프레임워크 때문이다.

<br><br>

### Maven, Gradle 프로젝트에 WEB-INF
서블릿 스펙에 따르면 WEB-INF 폴더의 하위 폴더로 lib 폴더와 classes 폴더를 생성하고 각각의 폴더에 대한 jar 파일과 컴파일 된 클래스들이 위치해야한다.

하지만 메이븐이나 그레이들 프로젝트의 경우 필요한 jar 파일은 pom.xml/build.gradle 파일의 의존을 통해 지정하고ㅓ 컴파일된 결과는 target폴더나 build 폴더에 위치한다. 때문에 WEB-INF 폴더 밑에 lib 폴더나 classes 폴더를 생성할 필요가 없다.

<br>

기본 설정 코드 작성이나 톰캣설정, JSP설정 등은 스프링부트와 다를 것 같아서 책으로만 보고 따로 정리하는 것은 스킵한다.

책에서는 web.xml을 통해 DispatcherServlet을 설정하고 있는데 스프링 부트에는 web.xml이 사라졌다. (자동으로 설정해주는 것 같다.) 스프링 부트에서 따로 DispatcherServlet을 설정하고 싶다면 https://oingdaddy.tistory.com/359 이 글을 참고하자.

<br>

## @Controller

스프링 MVC 프레임워크에서 컨트롤러란 간단히 설명하면 웹 요청을 처리하고 그 결과를 뷰에 전달하는 스프링 빈 객체이다. 스프링 컨트롤러로 사용될 클래스는 `@Controller` 애노테이션을 붙여야 하고, `@GetMapping` 애노테이션이나 `@PostMapping` 애노테이션과 같은 요청 매핑 애노테이션을 이용해서 처리할 경로를 지정해 주어야 한다.

다음 코드는 컨트롤러 예제를 나타낸 것이다.

```java
@Controller
public class Chap9Controller {

	@RequestMapping("/hello")
	public String hello(Model model,
			@RequestParam(value="name", required = false)String name) {
		model.addAttribute("Jang", name);
		return "index";
	}
}
```

`@RequestParam` 애노테이션은 HTTP 요청 파라미터를 메서드의 파라미터로 전달받을 수 있게 해 준다. `@RequestParam` 애노테이션의 `value` 속성은 HTTP 요청 파라미터의 이름을 지정하고 `required` 속성은 필수 여부를 지정한다.

즉 `http://localhost:18080/hello?name=jang` 이런 요청이 오면 name파라미터의 값인 'jang'을 `String name`에서 사용하지만 필수로 받지는 않는다는 의미이다.

위 코드에 `Model model`은 뷰에 전달할 데이터를 지정하기 위해 사용된다. MVC에 그 모델이다. 뷰에서는 컨트롤러에서 `model.addAttribute("Jang", name);` 코드로 속성을 추가해서 전달한 모델을 'Jang' 이라는 속성 이름을 사용해서 컨트롤러가 전달한 데이터에 접근한다.

`@GetMapping` 이 붙은 메서드는 컨트롤러의 실행 결과를 보여줄 뷰 이름을 리턴한다.

<br><br><br>


### 아래는 책 이외의 내용
<hr>

## @SpringBootApplication (스프링 부트 내용)

Spring Boot 개발 시 항상 메인 클래스에 `@Configuration`, `@EnableAutoConfiguration` , `@ComponetScan`을 애노테이트한다. 이 애노테이션들은 자주 함께 사용되는데 스프링 부트에서 편의를 위해 `@SpringBootApplication` 애노테이션을 제공한다.

```java
//기본 Main 클래스
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
```

<br>

* `@EnableAutoConfiguration`

스프링 부트에서는  WebMvc / JDBC / JPA/ LOGGING 등 Spring기반 웹어플리케이션 구동에 필요한 대부분 설정들은 `@EnableAutoConfiguration`에 의해 자동설정되고 외부로는 드러나지 않는다. 필요속성값만 오버라이딩하여 사용하면 된다.<br>
기본 Java 또는 Spring 기반의 개발환경을 드라마틱한 수준으로 간소화 시킨다는 점에서 스프링 부트의 핵심은 @EnableAutoConfiguration 이라고 할 수 있다. 

책에서는 설정클래스에 `@EnableWebMvc` 애노테이션을 추가하고 `implements WebMvcConfigurer` 했는데 스프링 부트는 자동으로 해주는것 같다.

<br>
<br>

## @RestController

Spring Web에서는 사용자에게 보여지는 View가 없는 컨트롤러를 RestController라고 한다. 즉 `@RestController` 애노테이션을 붙이면 View, Page 형태가 아닌, JSon, XML 형태로 반환을 하게된다.

