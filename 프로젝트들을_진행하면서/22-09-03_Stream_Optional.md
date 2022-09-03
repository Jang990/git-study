# 22년 09월 03일

`Optional`관련 사용방법을 찾던 도중 `Stream`과 같은 `Optional<T>.map()` 메소드를 제공한다 했다. 그런데 `Stream`이 이해가 안되고 이전에 백준을 풀면서 아래와 같은 코드들도 이해가 안되서 이참에 `Stream`을 공부해봤다.

```java
Arrays.stream(numberStr.split(" ")).mapToLong(Long::parseLong).toArray()
```
<br>


## `Stream` 사용법

스트림은 다음과 같이 다양한 방식으로 만들 수 있다.
```java
int[] numArr = {0, 1, 2, 3, 4, 5};
List<String> strList = Arrays.asList("a", "b", "c");
Stream<String> stm1 = strList.stream();
Stream<Integer> stm2 = Stream.iterate(0, n->n+2);
IntStream istm = Arrays.stream(numArr);
```

<br>

스트림은 **stream 생성 -> 중간연산 -> 최종연산** 으로 진행된다.

중간연산은 연산결과가 스트림인 연산이고 여러번 적용할 수 있다.<br> 
최종연산은 연산결과가 스트림이 아니고 스트림의 모든 요소를 소모하여 단 한번만 적용가능한 연산이다. 

진행과정은 다음과 같다.
```java
stream //이미 생성되어있는 스트림 객체
    .distinct().limit(5).sorted() // 중간연산 (중복제거->5개짜르기->정렬)
    .forEach(System.out::println); // 최종연산(출력)
```

<br>

### Stream의 특징

스트림은 읽기만(ReadOnly) 할 뿐 원본을 변경하지 않는다.
```java
List<Integer> list = Arrays.asList(3, 2, 5, 4, 2);
List<Integer> sortedList = list.stream()
		.sorted()
		.collect(Collectors.toList());
//원본인 list는 변경되지 않는다.
```

<br>

스트림은 일회용이다. 다음과 같이 재사용할 수 없다.(필요하면 다시 스트림을 생성)
```java
strStream.forEach(System.out::println); // 최종연산 수행
int numOfStr = strStream.count(); // 에러. 스트림이 이미 닫힘
```

<br>

최종 연산 전까지 중간연산이 수행되지 않는다. - 지연된 연산
```java
//로또번호 출력 예시
IntStream intStream = new Random().ints(1,46) // 1~45범위의 무한 스트림 - 지금은 그렇구나 하고 넘어가라
intStream.distinct().limit(6).sorted() 
    .forEach(i->System.out.println(i+","))
```

<br>

스트림은 작업을 내부 반복으로 처리 - 코드 간결
```java
//for(String str : strList)
//    System.out.println(str);
//위와 같은 연산을 아래처럼 수행
stream.forEach(System.out::println);
```

... 생략


### Stream 생성

```java
// 객체 배열 스트림 생성
Stream<String> strSteam1 = Stream.of("a", "b", "c");
Stream<String> strSteam2 = Stream.of(new String[]{"a", "b", "c"});
Stream<String> strSteam3 = Arrays.stream(new String[]{"a", "b", "c"});
Stream<String> strSteam4 = Arrays.stream(new String[]{"a", "b", "c"}, 0, 3); //0~2 즉 a,b,c 가 들어감 

//기본형 배열 스트림 생성
int[] numArr = {0, 1, 2, 3, 4, 5};
IntStream istm1 = Arrays.stream(numArr);
IntStream istm2 = IntStream.of(numArr);
```

<br>

**무한 스트림 부분은 건너뛰겠다.**


<br>
<br>

### Stream 연산

중간 연산
```java
stream.filter(); // 조건에 안맞는 요소 제외 (중간연산)
stream.distinct(); // 중복제거 (중간연산)
stream.sort(); // 정렬 (중간연산)
stream.limit(5); // 스트림 자르기 (중간연산)
stream.skip(3); // 요소 건너뛰기
stream.map(); // 요소 변환 (중간연산) - 좀 어려움 아래서 살펴봄


//스트림이 1, 2, 3, 4, 5, 6, 7, 8의 요소를 가진다고 가정
// 스트림에서 3개의 요소를 건너뛰고 5개를 자르기 때문에 
// 4,5,6,7,8 스트림이 나온다.
stream.skip(3).limit(5);
//2의 배수만을 가진 스트림이 나온다. 2,4,6,8
stream.filter(i->i%2==0)
```

<br>

최종연산
```java
stream.count(); // 요소 수 세기(최종연산)

//Optional 관련
stream.max(); 
stream.min();
```

이외에도 다른 중간연산 최종연산이 있다.

<br>
<br>

###  `Stream.map()`, `Stream.flatMap()`

일단 백준 알고리즘과, `Optional`쪽에서 사용하는 `map()` 부분의 원리만 보고 나중에 필요할 때 다시 공부한다.

<br>

스트림의 요소 변환하기 - `map()`
```java
Stream<File> fileStream = Stream.of(new File("Ex1.java"), new File("Ex1"), new File("Ex1.bak"), new File("Ex2.java"), new File("Ex1.txt"));
//Stream<File>에서 File에 이름을 Stream<String>으로 만듦
//Stream<File> -> Stream<String>
Stream<String> fileNameStream = fileStream.map(File::getName);
fileNameStream.forEach(System.out::println);
```

만약 다음 예시에서 파일 확장자(대문자)를 중복없이 뽑아내고 싶다면 다음과 같은 코드를 작성하면 된다.
```java
fileStream.map(File::getName) // Stream<File> -> Stream<String>
    .filter(s->s.indexOf('.' != -1)) // 확장자 없는것 필터링
    .map(s->s.substring(s.indexOf'.'+1)) // Stream<String> -> Stream<String>
    .map(String::toUpperCase) // 대문자 Stream<String> -> Stream<String>
    .distinct() // 중복제거
    .forEach(System.out::print);
```

<br>

`Stream.flatMap()`는 
```java
Stream strArr = Stream.of(new String[]{"abc", "def"},
                        new String[]{"ABC", "DEF"});
Stream<Stream<String>> strStrStrm1 = strArr.map(Arrays::stream); //우리가 원하는 형식이 아니다
// abc, def, ABC, DEF 를 원한다면 아래와 같이 사용하면 된다.
Stream<String> strStrStrm2 = strArr.flatMap(Arrays::stream);
```


<br>
<br>


내용출처: https://www.youtube.com/watch?v=7Kyf4mMjbTQ

<br>
<br>

## `Optional`
`Optional` 객체 생성
```java
/*
str(100번지) -> "abc"(100번지 값)
optVal(200번지) -> 100번지 주소(200번지 값) -> "abc"(100번지 값)
이렇게 참조하고 있다.
*/
String str = "abc"
Optional<String> optVal = Optional.of(str);

Optional<String> optVal2 = Optional.of(null); // 널포인터 익셉션 오류 발생 Null을 다루기 위해 Optional을 쓰기때문에 이건 잘 안씀
Optional<String> optVal3 = Optional.ofNullable(null); // 오류 안남
```

```java
Optional<String> optVal = null; // 바람직하지 않음
Optional<String> optVal = Optional.empty(); // 빈 객체로 초기화
```

<br>

`Optional`객체 값 가져오기
```java
Optional<String> optVal = Optional.of("abc");
String str1 = optVal.get(); // optVal에 저장된 값 반환. null이면 예외 발생

//이 아래것들을 자주 씀
String str2 = optVal.orElse(""); // optVal에 저장된 값이 null이면 ""를 반환
String str3 = optVal.orElseGet(String::new); // 람다식 사용가능, optVal에 저장된 값이 null이면 new String()를 반환
String str4 = optVal.orElseThrow(NullPointerException::new); //널이면 예외 발생
//orElse와 orElseGet은 같은 것 같지만 다르다. 차이는 아래서 설명한다.
```

`Optional`에서 사용하는 `map()`은 `Stream`과 똑같이 사용하면 된다.

<br>

내용출처: https://www.youtube.com/watch?v=W_kPjiTF9RI

<br>
<br>

### `Optional.orElse()` VS `Optional.orElseGet()`

`orElse()`는 null이던말던 항상 불립니다. `orElseGet()`은 null일 때만 불립니다. 다음 예시를 통해 위험한 상황을 확인하자.

```java
public User findByUsername(String name) {
	return userRepository.findByName(name).orElse(createUserWithName(name));
}

private User createUserWithName(String name) {
	User newUser = new User();
	newUser.setName(name)
	return userRepository.save(user);
}
```

위의 상황은 `orElse()`로 인해 `createUserWithName()` 메소드가 `User`가 null이든 말든 실행된다. 그러므로 항상 `User`가 생성되는 상황이다. 만약 name이 unique였다면 장애가 발생하는 상황이다.

즉 위 코드는 아래코드와 같다.
```java
public User findByUsername(String name) {
	User newUser = createUserWithName(name); // 무조건 User를 생성해서 DB에 저장
	return userRepository.findByName(name).orElse(newUser);
}

private User createUserWithName(String name) {
	User newUser = new User();
	newUser.setName(name)
	return userRepository.save(user);
}
```

<br>

차이를 이해하고 있자

<br>

내용 출처: https://cfdf.tistory.com/34


<br>
<br>

## 메소드 참조 ex)Long::parseLong

**메소드 참조**는 람다 표현식이 단 하나의 메소드만을 호출하는 경우에 해당 람다 표현식에서 불필요한 매개변수를 제거하고 사용할 수 있독 해준다. 즉 아래와 같은 두 코드는 같은 코드이다.
```java
//람다 표현식
(base, exponent) -> Math.pow(base, exponent);
//메소드 참조
Math::pow;
```

내용출처: http://www.tcpschool.com/java/java_lambda_reference