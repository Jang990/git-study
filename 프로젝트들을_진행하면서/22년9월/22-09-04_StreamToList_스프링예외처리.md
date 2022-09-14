# 22년 09월 04일


## Stream을 이용하여 int[]형식을 List로 변환

배열을 List로 변환할 때, Arrays.asList() 메소드를 사용하면 됩니다.

하지만, 배열의 원소가 int와 같은 primitive type인 경우 Arrays.asList()는 좀 다른 결과를 리턴합니다.


`List<int>` 형식의 리스트를 얻기를 원하지만 `Array.asList()`를 사용하면 다음과 같은 `List<int[]>` 형식을 반환한다.
```java
// int 배열        
int[] arr = { 1, 2, 3 };

// Arrays.asList()         
List<int[]> intList = Arrays.asList(arr);

// 결과 출력
System.out.println(intList.size()); // 1
System.out.println(intList.get(0)); // I@71bb301
System.out.println(Arrays.toString(intList.get(0)));  // [1, 2, 3]
```

하지만 다음과 같이 stream을 사용하여 변환할 수 있다.
```java
int[] arr = { 1, 2, 3 };
// int -> List
List<Integer> intList = Arrays.stream(arr)
                        .boxed() // int를 Integer로 변환                
                        .collect(Collectors.toList()); //toSet() 등등이 있음

// List 출력        
System.out.println(intList.size()); // 3
System.out.println(intList); // [1, 2, 3]
```

출처: https://hianna.tistory.com/552 [어제 오늘 내일:티스토리]

<br>
<br>
<br>

## 스프링 예외 처리

예외처리 부분은 더 깊은 공부가 필요하다. 일단 단순한 예외 처리를 위한 공부이다.

<br>

사용자에게 에러내 용을 상세하게 보여줄 필요가 없다.
스프링에서 예외 처리방식은 3가지가 있다. 가장 보편적으로 사용되는 방식은 3번째 방식이다.

```
1. try-catch
2. ExceptionHandler - 컨트롤러단에서 처리
3. Global Level ExceptionHandler - 컨트롤러에서 클라이언트에 전달 전에 처리
```

<br>

3번째 방식으로 예외처리를 하려면 다음과 같은 코드를 작성하면된다. 

```java
@RestController
@RestControllerAdvice // Controller 클래스에 공통된 기능을 적용하기 위해 AOP를 사용하는 어노테이션
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=IllegalArgumentException.class) //IllegalArgumentException만을 처리하는 ExceptionHandler
	public String handleArgumentException(IllegalArgumentException e) {
		return "<h1>"+e.getMessage()+"<h1>";
	}
}
```

<br>


### @ControllerAdvice vs @RestControllerAdvice
@ControllerAdvice는 @Componenet 어노테이션을 가지고 있어 컴포넌트 스캔을 통해 스프링 빈으로 등록된다.
@RestControllerAdvice는 @Controlleradvice와 @ResponseBody 어노테이션으로 이루어져있고 HTML 뷰 보다는 Response body로 값을 리턴할 수 있다.
```
@ControllerAdvice is meta-annotated with @Component and therefore can be registered as a Spring bean through component scanning. @RestControllerAdvice is meta-annotated with @ControllerAdvice and @ResponseBody, and that means @ExceptionHandler methods will have their return value rendered via response body message conversion, rather than via HTML views.
```
출처 - [스프링 공식 문서](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-controller-advice)

<br>

예외 관련 참고할 내용<br>
https://jeong-pro.tistory.com/195 <br>
https://github.com/gngsn/Gngsn-Spring-Lab/blob/master/spring-toby/note/Exception.md


스프링 예외 처리 관련 참고할 내용<br>
https://velog.io/@kiiiyeon/%EC%8A%A4%ED%94%84%EB%A7%81-ExceptionHandler%EB%A5%BC-%ED%86%B5%ED%95%9C-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC
