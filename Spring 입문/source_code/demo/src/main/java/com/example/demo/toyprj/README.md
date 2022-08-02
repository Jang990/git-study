# 토이 프로젝트

커맨드 객체 써보기.

인터셉터 써보기. 등등

책에 나온 내용들을 써보기 위한 패키지.



## 진행중 발생한 오류

<br>

### Optional과 UserInfo 커맨드 객체

커맨드 객체를 받을 때 널값이 들어오는 것을 생각해서 익셉션이 발생하는 것을 막기 위해 멤버변수들을 `Optional<T>` 를 이용했다.

```java
public class UserInfo {
	private Optional<String> id;
	private Optional<String> password;
	private Optional<Boolean> idRemember;
	
	public Optional<String> getId() {
		return id;
	}
	public void setId(String id) {
		System.out.println(id);
		this.id = Optional.ofNullable(id);
	}
	...
}
```

<br>

다음과 같이 게터, 세터만을 메소드로 만들어 두었는데 컨트롤러에서 다음과 같이 사용했을 때 오류가 발생했다.
```java
if(!user.getIdRemember().isPresent()) {
	System.out.println("비어있다.");
	user.setIdRemember(false);
}
```
```
Cannot invoke "java.util.Optional.isPresent()" because the return value of "com.example.demo.toyprj.UserInfo.getIdRemember()" is null
```

<br>


`Optional`을 사용하기 전에 커맨드 객체인 `UserInfo`에서 다음 코드와 같이 생성자에서 멤버 변수들을 초기화 시켜줬어야 한다.

```java
public class UserInfo {
	private Optional<String> id;
	private Optional<String> password;
	private Optional<Boolean> idRemember;
	
	public UserInfo() {
		this.id = Optional.empty();
		this.password = Optional.empty();
		this.idRemember = Optional.empty();
	}
	
	public Optional<String> getId() {
		return id;
	}
	public void setId(String id) {
		System.out.println(id);
		this.id = Optional.ofNullable(id);
	}
	...
}
```
