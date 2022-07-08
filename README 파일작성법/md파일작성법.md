# 마크다운(markdown)
일반 텍스트 기반의 마크업언어

<br>

# 1. Header 사용법

```
# 헤더 크기 (h1)
## 헤더 크기 (h2)  
### 헤더 크기 (h3)  
#### 헤더 크기 (h4)
##### 헤더 크기 (h5)
###### 해더 크기 (h6)
####### 해더 크기 H7(지원하지 않음)
```

# 헤더 크기 (h1) 
## 헤더 크기 (h2) 
### 헤더 크기 (h3) 
#### 헤더 크기 (h4) 
##### 헤더 크기 (h5) 
###### 해더 크기 (h6)
####### 해더 크기 H7(지원하지 않음)

<br><br>

# 2. 문단 구별

문단구별을 위한 3가지 방법
1. 한 개 이상의 빈 줄을 문단 사이에 삽입
2. 줄의 마지막에 [Space Bar] 를 두 번 이상 눌러 띄어쓰기
3. `<br>`태그 사용

```
문장을 작성하면 됩니다.(공백을 안 두면..) 
빈 줄이 없으면 자동으로 앞의 문장 뒤에 붙습니다.(Space Bar를 두 번 이상 눌러 띄어쓰기를 하면..)  
위 문장에서 두 칸의 공백을 두어 강제 개행할 수 있습니다.

빈 줄을 문단 사이에 삽입하여 강제 개행할 수도 있습니다.
<br>
`<br>`태그를 사용하여 개행도 가능하다.
```

문장을 작성하면 됩니다.(공백을 안 두면..) 
빈 줄이 없으면 자동으로 앞의 문장 뒤에 붙는다.(Space Bar를 두 번 이상 눌러 띄어쓰기를 하면..)
위 문장에서 두 칸의 공백을 두어 강제 개행 가능.

빈 줄을 문단 사이에 삽입하여 강제 개행 가능.
<br>
`<br>`태그를 사용하여 강제 개행 가능.

<br><br>

# 3.목록(List) 사용법
## 3-1. 순서 있는 목록 
```
1. Item 1
   1. Item 1-1
   2. Item 1-2
2. Item 2
3. Item 3

```
1. Item 1
   1. Item 1-1
   2. Item 1-2
2. Item 2
3. Item 3
## 3-2. 순서 없는 목록 (글머리 기호: *, +, - 지원)
```
* Item 1
   * Item 1-1
   * Item 1-2
* Item 2  

+ 빨강
  + 녹색
    + 파랑

- 빨강
  - 녹색
    - 파랑
```
* Item 1
   * Item 1-1
   * Item 1-2
* Item 2 

+ 빨강
  + 녹색
    + 파랑

- 빨강
  - 녹색
    - 파랑

혼합해서 사용 가능
```
* 1단계
  - 2단계
    + 3단계
      + 4단계
```

* 1단계
  - 2단계
    + 3단계
      + 4단계


<br><br>

# 4. 코드 블록

문단구별을 위한 3가지 방법
1. 부분블럭코드("`") 이용방식
2. `<pre><code>{code}</code></pre>` 이용방식
3. 코드블럭코드("```") 이용방식

<br>

## 4-1 부분블럭코드("`") 이용방식
```
`<br>`<br>
`<hr>`<br>
`<pre><code></code></pre>`<br>
```
`<br>`<br>
`<hr>`<br>
`<pre><code></code></pre>`<br>

<br>

## 4-2. `<pre><code>{code}</code></pre>` 이용방식
```
<pre>
<code>
public class BootSpringBootApplication {
  public static void main(String[] args) {
    System.out.println("Hello, Honeymon");
  }
}
</code>
</pre>
```

<pre>
<code>
public class BootSpringBootApplication {
  public static void main(String[] args) {
    System.out.println("Hello, Honeymon");
  }
}
</code>
</pre>

<br>

## 4-3. 코드블럭코드("```") 이용방식

```
    ```
    public class BootSpringBootApplication {
        public static void main(String[] args) {
            System.out.println("Hello, Honeymon");
        }
    }
    ```
```


```
public class BootSpringBootApplication {
  public static void main(String[] args) {
    System.out.println("Hello, Honeymon");
  }
}
```
깃헙에서는 코드블럭코드("```") 시작점에 사용하는 언어를 선언하여 문법강조 가능
```
    ``` java
    public class BootSpringBootApplication {
        public static void main(String[] args) {
            System.out.println("Hello, Honeymon");
        }
    }
    ```
```
```java
    public class BootSpringBootApplication {
        public static void main(String[] args) {
            System.out.println("Hello, Honeymon");
        }
    }
```

<br><br>

# 5. 수평선 `<hr/>`
문서를 미리보기로 출력할 때 페이지 나누기 용도 등으로 사용
아래 줄은 모두 수평선을 만든다.
```
* * *

***

*****

- - -

---------------------------------------
```
* * *

***

*****

- - -

---------------------------------------

<br><br>

# 6. 링크
* 참조링크
```
1. [link keyword][id]

[id]: URL "Optional Title here"

2. Link: [Google][googlelink]

[googlelink]: https://google.com "Go google"
```
1. [link keyword][id]

[id]: URL "Optional Title here"

2. Link: [Google][googlelink]

[googlelink]: https://google.com "Go google"


<br>

* 자동연결
```
1. 외부링크: <http://example.com/>
2. 이메일링크: <address@example.com>
```
1. 외부링크: <http://example.com/>
2. 이메일링크: <address@example.com>

<br><br>

# 7. 강조
```
*single asterisks*
_single underscores_
**double asterisks**
__double underscores__
~~cancelline~~
```
*single asterisks*  
_single underscores_  
**double asterisks**  
__double underscores__  
~~cancelline~~  

<br>

# 8. 이미지
1. `![이미지이름](이미지주소)` 사용방법
2. `<img>` 태그 사용방법
3. Repository 내에 이미지 사용방법
4. img 중앙정렬 방법

<br>

## 8-1 `![이미지이름](이미지주소)` 사용방법
```
![구글로고](https://gstatic.com/images/branding/googlelogo/svg/googlelogo_clr_74x24px.svg)
```

![구글로고](https://gstatic.com/images/branding/googlelogo/svg/googlelogo_clr_74x24px.svg)

<br>

## 8-2 `<img>` 태그 사용방법
```
<img src="https://gstatic.com/images/branding/googlelogo/svg/googlelogo_clr_74x24px.svg" />
```
<img src="https://gstatic.com/images/branding/googlelogo/svg/googlelogo_clr_74x24px.svg" />

<br> 

* width와 height 조절가능
```
<img src="https://gstatic.com/images/branding/googlelogo/svg/googlelogo_clr_74x24px.svg" 
width="200"
height="100"/>
```
<img src="https://gstatic.com/images/branding/googlelogo/svg/googlelogo_clr_74x24px.svg" 
width="200px"
height="100px"/>


## 8-3. Repository 내에 이미지 사용방법

```
![프로젝트_img1](./img_Folder/img1.jpg)
```

![프로젝트_img1](./img_Folder/img1.jpg)

프로젝트의 경로로 지정하여 사용

<br>

## 8-4. img 중앙정렬 방법

`<p align="center">`태그를 사용하여 중앙정렬
```
<p align="center">
  <img src="https://gstatic.com/images/branding/googlelogo/svg/googlelogo_clr_74x24px.svg" 
    width="500"
    height="300"/>
</p>
```

<br><br>

<p align="center">
  <img src="https://gstatic.com/images/branding/googlelogo/svg/googlelogo_clr_74x24px.svg" 
    width="500"
    height="300"/>
</p>
