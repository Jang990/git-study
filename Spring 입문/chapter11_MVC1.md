# Spring 5 입문 Chapter11 MVC1: 요청 매핑, 커맨드 객체, 리다이렉트, 폼 태그, 모델


## 요청 매핑 애노테이션을 이용한 경로 매핑

컨트롤러 클래스는 요청 매핑 애노테이션을 사용해서 매서드가 처리할 요청 경로를 지정한다. 요청 매핑 애노테이션에는 `@RequestMapping`, `@GetMapping`, `@PostMapping` 등이 있다.

<br>

예를 들어 회원 가입 과정을 생각해보자. 일반적인 회원 가입 과정은 '약관 동의' -> '회원 정보 입력' -> '가입 완료' 인데 각 과정을 위한 URL을 다음과 같이 지정할 수 있을 것이다.

* 약관 동의 화면: http://localhost:8080/register/step1
* 회원 정보 입력 화면: http://localhost:8080/register/step2
* 가입 처리 결과 화면: http://localhost:8080/register/step3

이렇게 여러 단계를 거쳐 하나의 기능이 완성되는 경우 관련 아래 코드와 같이 요청 경로를 한 개의 컨트롤러 클래스에서 처리하면 코드 관리에 도움이 된다.

```java
@Controller
public class Chapter11RegisterController {
	
	@RequestMapping("/register/step1")
	public String handleStep1() {
		//관련 서비스들...
		return "register/step1";
	}
	
	@RequestMapping("/register/step2")
	public String handleStep2() {
		//...
		return "register/step3";
	}
	
	@RequestMapping("/register/step3")
	public String handleStep3() {
		//...
		return "register/step3";
	}
}
```

이 코드를 보면 각 요청 매핑 애노테이션 경로가 `/register`로 시작한다. 이 경우 다음 코드처럼 공통되는 부분의 경로를 담은 `@RequestMapping` 애노테이션을 클래스에 적용하고 각 메서드는 나머지 경로를 값으로 찾는 요청 애노테이션을 적용할 수 있다.

```java
@Controller
@RequestMapping("/register")
public class Chapter11RegisterController {
	
//	@RequestMapping("/register/step1")
	@RequestMapping("/step1")
	public String handleStep1() {
		//관련 서비스들...
		return "register/step1";
	}
	
	@RequestMapping("/step2")
	...
}
```

<br>
<br>

## GET과 POST 구분: @GetMapping, @PostMapping

* GET방식 POST방식 구분없이 처리하고 싶다 -> `@RequestMapping`
* POST방식 요청만 처리하고 싶다 -> `@PostMapping`
* GET방식 요청만 처리하고 싶다. -> `@GetMapping`

```java
@Controller
@RequestMapping("/register")
public class Chapter11RegisterController {

	@RequestMapping("/step1")
	public String handleStep1() {
		...
	}
	
	@PostMapping("/step2")
	public String handleStep2() {
		...
	}
	
	@GetMapping("/step3")
	public String handleStep3() {
		...
	}

}
```

<br>
<br>

## 요청 파라미터 접근

요청 파라미터의 접근하는 방법은 2가지 방법이 있다.

1. 첫 번째 방법: `HttpServletRequest`를 직접 이용하는 것
```java
// http://localhost:8080/register/step1?name=jang 이런 URL로 요청을 받았다고 가정
// 결과: 콘솔창에 jang이 출력되고 step1 화면을 보여줌
@Controller
@RequestMapping("/register")
public class Chapter11RegisterController {
	
	@RequestMapping("/step1")
	public String handleStep1(HttpServletRequest request) {
		String name = request.getParameter("name");
		System.out.println(name);
		return "register/step1";
	}

    ...
}
```

<br>

2. 두 번째 방법: `@RequestParam` 애노테이션을 사용하는 것
```java
// http://localhost:8080/register/step3 이런 URL로 요청을 받았다고 가정
// 결과: 콘솔창에 jang이 출력되고 step3 화면을 보여줌
@Controller
@RequestMapping("/register")
public class Chapter11RegisterController {
    ...
	@GetMapping("/step3")
	public String handleStep3(@RequestParam(value="name", defaultValue = "jang")String userName) {
		System.out.println(userName);
		return "register/step3";
	}
}
```
위 코드의 `handleStep3` 메서드의 파라미터인 `userName`은 `name` 요청 파라미터의 값을 읽어오고 요청 파라미터의 값이 없으면 `"jang"` 문자열을 값으로 사용한다

아래 목록은 `@RequestParam` 애노테이션의 속성이다.
* value: HTTP 요청 파라미터 이름 지정
* required: 필수여부를 boolean 타입으로 지정
* defaultValue: 요청 파라미터가 값이 없을 때 사용할 문자열 값을 지정(기본 값은 없다)

<br>

```java
@RequestMapping(value="agree", defaultValue="false")Boolean agreeVal
```
스프링 MVC는 파라미터 타입에 맞게 String 값을 변환해 준다. 위 코드는 `agree` 요청 파라미터의 값을 읽어와 Boolean 타입으로 변환해서 `agreeVal` 파라미터에 전달한다. Boolean 타입 외에 int, long, Integer, Long 등 기본 데이터 타입과 래퍼 타입에 대한 변환을 지원한다.

<br>
<br>

## 리다이렉트 처리

이전 코드에 step1, step2, step3에서 step2는 Post 요청만 처리하고 있다. step2에 Get 요청을 보내서 잘못된 전송 방식으로 요청을 한다면 405 에러 페이지를 사용자에게 보여줄 것이다. 이런 상황에서는 에러페이지를 사용자에게 직접 보여주는 것보다 step1 페이지로 다시 가는 것이 좋을 것이다.

```java
@GetMapping("/step2")
public String handleStep2Get() {
	return "redirect:/register/step1";
}
```

위 코드와 같이 구현하여 step1으로 보낼 수 있다.

"redirect:" 뒤 문자열이 "/"로 시작하면 웹 어플리케이션을 기준으로 이동 경로를 생성한다.

<br>
<br>

## 커멘드 객체를 이용해서 요청 파라미터 사용

아래 코드는 요청 파라미터 개수가 증가할 때마다 코드 길이도 함께 길어지는 단점이 있다. 파라미터 개수가 20개가 넘는 복잡한 폼은 파라미터의 값을 읽어와 설정하는 코드만 40줄 이상 작성해야 한다.

```java
...
public String handleDog(HttpServletRequest request) {
    String dogName = request.getParameter("name");
    int dogAge = Integer.parseInt(request.getParameter("age"));
    ...

    MyDog dog = new MyDog();
    dog.setName(dogName);
    dog.setAge(dogAge);
    ...

}
```
<br>

스프링은 이런 불편함을 줄이기 위해 요청 파라미터의 값을 커맨드 객체에 담아주는 기능을 제공한다.
```java
//http://localhost:8080/register/dogPage?name=Dog&age=24 URL로 요청을 받았다 가정
// 결과: 콘솔창에 이름: Dog 나이: 24 를 출력하고 dog 화면을 보여줌
@RequestMapping("/dogPage")
public String handleDogPage(MyDog dog) {
		System.out.println("이름: " + dog.getName());
		System.out.println("나이: " + dog.getAge());
		return "register/dog";
}
```
```java
//커멘드 객체 코드
public class MyDog {
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
```
`MyDog` 클래스에는 게터와 세터 메스드가 있다. 스프링은 이들 메서드를 사용해서 요청 파라미터의 값을 커맨드 객체에 복사한 뒤 `dog` 파라미터로 전달한다.

즉 스프링 MVC가 `handleDogPage()` 메서드에 전달할 `MyDog` 파라미터 객체를 생성하고 그 객체의 세터 메스드를 이용해서 일치하는 요청 파라미터의 값을 전달한다.
<br>
<br>

**JSP 관련 내용 스킵...**

<br>
<br>

## 주요 에러 발생 상황

### 404 Not Found 에러 
404 에러가 발생한 경우 다음 사항을 확인해보자.
* 요청 경로가 올바른지
* 컨트롤러에 설정한 경로가 올바른지
* 컨트롤러 클래스에 `@Controller` 애노테이션을 적용했는지

<br>

### 405 Method Not Allowed 에러 
지원하지 않는 전송 방식을 사용한 경우 405 에러가 발생한다. 예를 들어 POST 방식만 처리하는 요청 경로를 GET 방식으로 연결하는 경우 발생한다.

<br>

### 400 Bad Request
아래 코드와 같이 코드를 짰다고 가정하자.
```java
@PostMapping("/register/step2")
public String handleStep2(
    //필수로 존재해야 함, 기본값 없음
    @RequestParam("agree")Boolean agree,
    Model model
) {
    ...
}
```
컨트롤러에서 `agree`는 필수로 존재해야 하는데 `agree` 파라미터를 전송하지 않는다면 400 에러가 발생한다. 또한 `agree` 파라미터를 "true1" 이런식으로 전달하게 되어도 400 에러가 발생한다.

<br>
<br>

## 커맨드 객체: 중첩 콜렉션 프로퍼티

```java
public class BarkDog {
	private List<String> barkSound;
	private MyDog dog;
	
	public List<String> getBarkSound() {
		return barkSound;
	}
	public void setBarkSound(List<String> barkSound) {
		this.barkSound = barkSound;
	}
	public MyDog getDog() {
		return dog;
	}
	public void setDog(MyDog dog) {
		this.dog = dog;
	}
}
```
커맨드 객체가 위 코드와 같이 `List`와 `MyDog`라는 객체를 가지고 있다면 어떻게 해야할까?

스프링 MVC는 커맨드 객체가 리스트 타입의 프로퍼티를 가졌거나 중첩 프로퍼티를 가진 경우에도 요청 파라미터의 값을 알맞게 커맨드 객체에 설정해주는 기능을 제공해주고 있다. 규칙은 다음과 같다.

* HTTP 요청 파라미터 이름이 "프로퍼티이름[인덱스]" 형식이면 List 타입 프로퍼티의 값 목록으로 처리
* HTTP 요청 파라미터 이름이 "프로퍼티이름.프로퍼티이름"과 같은 형식이면 중첩 프로퍼티 값을 처리

```html
<!-- 전송 폼 -->
<form>
    <input type="text" value="멍멍" name="barkSound[0]">
    <input type="text" value="컹컹" name="barkSound[1]">
    <input type="text" value="jang" name="dog.name">
    <input type="text" value="24" name="dog.age">
    <input type="submit" value="입력">
</form>
```
```java
@RequestMapping("/dogPage")
public String hadleDogPage(BarkDog dog) {
	if(dog != null) {
		System.out.println(dog.getBarkSound().get(0));
		System.out.println(dog.getBarkSound().get(1));
		System.out.println("이름: " + dog.getDog().getName());
		System.out.println("나이: " + dog.getDog().getAge());
	}
	return "register/dog";
}
```
```
멍멍
컹컹
이름: jang
나이: 24
```
즉 위와 같은 폼을 전송하면 스프링 MVC가 알맞게 커맨드 객체의 프로퍼티에 매핑시켜 준다.

<br>
<br>

## Model을 통해 컨트롤러에서 뷰에 데이터 전달하기

컨트롤러는 뷰가 응답 화면을 구성하는데 필요한 데이터를 생성해서 전달해야 한다. 이때 사용하는 것이 Model이다. 

```java
@RequestMapping("/")
public String firstModel(Model model) {
	String name = "jang";
	model.addAttribute("userName", name);
	return "index";
}
```
위와 같은 코드에서는 뷰에서 `${userName}` 이런식으로 모델의 속성 이름을 호출하여 "jang"이라는 값을 사용할 수 있다.

<br>
<br>

## ModelAndView를 통한 뷰 선택과 전달

지금까지 구현한 컨트롤러는 두 가지 특징이 있다.
* `Model`을 이용해서 뷰에 전달할 데이터 설정
* 결과를 보여줄 뷰 이름을 리턴

`ModelAndView`를 사용하면 이 두 가지를 한 번에 처리할 수 있다. 요청 매핑 애노테이션을 적용한 메서드는 `Stirng` 타입 대신 `ModelAndView`를 리턴할 수 있다. `ModelAndView`는 모델과 뷰 이름을 함께 제공한다. 위에서 사용한 `firstModel` 메서드를 `ModelAndView`를 사용하여 바꿔보았다.

```java
@RequestMapping("/")
public ModelAndView firstModel() {
    ModelAndView mav = new ModelAndView();
	String name = "jang";
	mav.addObject("userName", name);
    mav.setViewName("index");
	return mav;
}
```

뷰에 전달할 모델 데이터는 `addObject()` 메서드로 추가하고 뷰 이름은 `setViewName()` 메서드를 이용해서 지정한다.

<br>
<br>

**JSP 태그 라이브러리 스킵...**
