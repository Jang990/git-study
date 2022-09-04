# 22년 09월 03일

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