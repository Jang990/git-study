# Spring 5 입문 Chapter 4 의존 자동 주입

## 의존 자동 주입

스프링 3 버전이나 스프링 4 버전 초기에는 의존 자동 주입에 호불호가 있었으나, 스프링 부트가 나오면서 의존 자동 주입을 사용하는 추세로 바뀌었다.

스프링에서 의존 자동 주입을 설정하려면 `@Autowired` 애노테이션이나 `@Resource` 애노테이션을 사용하면 된다. 이 책에서는 `@Autowired` 애노테이션의 사용 방법을 살펴본다

## @Autowired 애노테이션을 이용한 의존 자동 주입

자동 주입 기능을 사용하면 스프링이 알아서 의존 객체를 찾아서 주입한다.

### 필드에 @Autowired 애노테이션 붙이기

아래 코드처럼 설정에 의존 객체를 명시하지 않아도(코드에서는 세터 메서드를 주석처리) 스프링이 필요한 의존 빈 객체를 찾아서 주입해준다.

```java
//@Autowired 애노테이션 사용 전
public class Person {
	private Pet pet;
    ...
}

@Configuration
public class Chapter3Conf {
	@Bean
	public Pet pet() {
		return new Pet();
	}
	
	@Bean
	public Person person() {
		Person p = new Person();
		p.setAge(24);
		p.setName("Jang");
		p.setPet(pet());
		return p;
    }
}
```

```java
//@Autowired 애노테이션 사용 후
public class Person {
	@Autowired
	private Pet pet;
    ...
}

@Configuration
public class Chapter3Conf {
	@Bean
	public Pet pet() {
		return new Pet();
	}
	
	@Bean
	public Person person() {
		Person p = new Person();
		p.setAge(24);
		p.setName("Jang");
	    // @Autowried 애노테이션으로 대체
	    // p.setPet(pet());
		return p;
	}
}
```

자동 주입 기능을 사용하는 것은 매우 간단하다. 의존을 주입할 대상에 `@Autowired` 애노테이션을 붙이기만 하면 된다.

만약 `@Autowired` 애노테이션을 설정한 필드에 알맞은 빈 객체가 주입되지 않았다면 `Person`의 `pet` 필드는 null 일 것이다. 그러면 `pet`필드에 접근할 때 NullPointerExceptiopn이 발생한다.


### 메서드에 @Autowired 애노테이션 붙이기

`@Autowired` 애노테이션은 메서드에도 붙일 수 있다.

```java
//기존 Person 소스코드
public class Person {
	@Autowired
	private Pet pet;
    ...
}
```

```java
//세터 메서드에 @Autowired를 붙인 소스코드
public class Person {
//	@Autowired
	private Pet pet;

	@Autowired
	public void setPet(Pet pet) {
		this.pet = pet;
	}
    ...
}
```

이렇게 세터 메서드에 `@Autowired`를 붙여도 정상동작하는 것을 확인할 수 있다.

<br>

빈 객체 메서드에 `@Autowired` 애노테이션을 붙이면 스프링은 해당 메서드를 호출한다. 이때 메서드 파라미터 타입에 해당하는 빈 객체를 찾아 인자로 주입한다.

<br>

### 일치하는 빈이 없는 경우

`@Autowired` 애노테이션을 적용한 대상에 일치하는 빈이 없으면 어떻게 될까?

```java
// 빈을 생성하지 않도록 코드 수정
//	@Bean
public Pet pet() {
	return new Pet();
}

@Bean
public Person person() {
	Person p = new Person();
	...
	return p;
}
```

`@Autowired` 애노테이션을 적용한 대상에 일치하는 빈이 없으면 아래와 같은 에러가 발생한다. 스프링 버전에 따라 에러 메시지는 조금씩 달라질 수 있지만 에러 내용은 유사하다.

```
Exception in thread "main" org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'person': Unsatisfied dependency expressed through method 'setPet' parameter 0; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.demo.Pet' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
```

에러 메시지를 요약하자면 `@Autowired` 애노테이션을 붙인 `setPet` 메서드에 주입할 `Pet` 빈이 존재하지 않아 에러가 발생했다는 것이다.

<br>
<br>

반대로 `@Autowired` 애노테이션을 붙인 주입 대상에 일치하는 빈이 두 개 이상이라면 어떻게 될까?

```java
// 2개의 같은 타입 빈을 생성하도록 코드 수정
@Bean
public Pet pet1() {
	return new Pet();
}
@Bean
public Pet pet2() {
	return new Pet();
}

@Bean
public Person person() {
	Person p = new Person();
	...
	return p;
}
```

`@Autowired` 애노테이션을 붙인 주입 대상에 일치하는 빈이 두 개 이상이라면 아래와 같은 에러가 발생한다.

```
Exception in thread "main" org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'person': Unsatisfied dependency expressed through method 'setPet' parameter 0; nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.example.demo.Pet' available: expected single matching bean but found 2: pet1,pet2
```

에러 메시지는 `Pet` 타입의 빈을 한정할 수 없는데 해당 타입 빈이 한 개가 아니라 이름이 `pet1`, `pet2`인 두 개의 빈을 발견했다는 사실을 알려준다.

자동 주입을 하려면 해당 타입을 가진 빈이 어떤 빈인지 정확하게 한정할 수 있어야 하는데 `Pet`타입의 빈이 두 개여서 어떤 빈을 자동 주입 대상으로 선택해야 할지 한정할 수 없다. 이 경우 스프링은 자동 주입에 실패하고 익셉션을 발생시킨다.

<br><br>

## @Qualifier 애노테이션을 이용한 의존 객체 선택

자동 주입 가능한 빈이 두 개 이상이면 자동 주입할 빈을 지정할 수 있는 방법이 필요하다. 

이때 `@Qualifier` 애노테이션을 사용한다. `@Qualifier` 애노테이션을 사용하면 자동 주입 대상 빈을 한정할 수 있다.

<br>

```java
public class Chapter3Conf {
	@Bean
    @Qualifier("dog")
	public Pet pet1() {
		return new Pet("dog");
	}

    @Bean
    public Pet pet2() {
		return new Pet("cat");
	}
	...
}
```
위 코드에서는 `@Bean` 애노테이션을 붙인 빈 설정 메서드에 `@Qualifier` 애노테이션을 사용했다. 

`pet1` 메서드에 "dog" 값을 갖는 `@Qualifier` 애노테이션을 붙였다. 이 설정은 해당 빈의 한정 값으로 "dog"를 지정한다. 

이렇게 지정한 값은 `@Autowired` 애노테이션에서 자동 주입할 빈을 한정할때 사용한다.

```java
public class Person {
	private Pet pet;

	@Autowired
    @Qualifier("dog")
	public void setPet(Pet pet) {
		this.pet = pet;
	}
    ...
}
```
`setPet` 메서드에 `@Autowired` 애노테이션을 붙였으므로 `Pet` 타입의 빈을 자동 주입한다. 이때 `@Qualifier` 애노테이션 값이 "dog" 이므로 한정 값이 "dog"인 빈을 의존 주입 후보로 사용한다.

즉 `Chapter3Conf` 설정 클래스에서 `@Qualifier` 애노테이션 값이 "dog"에 `Pet` 타입의  `pet1`을 자동 주입 대상으로 사용한다.

<br>

### 빈 이름과 기본 한정자

빈 설정에 `@Qualifier` 애노테이션이 없으면 빈의 이름을 한정자로 지정한다.

```java
@Bean
@Qualifier("dog")
public Pet pet1() {
	return new Pet("dog");
}

@Bean
public Pet pet2() {
	return new Pet("cat");
}
```

| **빈 이름** | **`@qualifies`** | **한정자** |
|-------------|------------------|------------|
| pet1|  dog    |  dog |
| pet2|      |  pet2 |

<br>


## 상위/하위 타입 관계와 자동 주입

```java
//상속관계의 두 클래스
public class Pet {
	...
}

public class MyDog extends Pet{
	@Override
	public String getName() {
		return "Dog";
	}
}
```

```java
@Configuration
public class Chapter3Conf {
	@Bean
	public Pet pet1() {
		return new Pet();
	}
	
	@Bean 
	MyDog myDog() {
		return new MyDog();
	}
    ...
}
```

```java
@Autowired
private Pet pet;
```

이런 상황에서 위 코드와 같이 `@Autowired`를 한다면 같은 타입의 빈이 2개 있을 때와 동일한 익셉션이 발생한다.

이 익셉션은 `@Qualifier`를 설정하거나, 부모클래스가 아닌 자식클래스인 `MyDog` 타입으로 `@Autowired` 하도록 만들면 해결할 수 있다.


## @Autowired 애노테이션의 필수 여부

**`@Autowired` 애노테이션은 기본적으로 `@Autowired` 애노테이션을 붙인 타입에 해당하는 빈이 존재하지 않으면 익셉션을 발생시킨다.**

<br>

`@Autowired` 애노테이션의 필수 여부를 지정하는 방법 3가지
1. `required` 속성을 false로 지정
2. `Optional` 사용
3. `@Nullable` 애노테이션 사용

<br>

```java
public class Person {
	private Pet pet;

    @Autowired(required = false)
	public void setPet(Pet pet) {
		this.pet = pet;
	}

    public void bark() {
        if(pet == null) {
            System.out.println("Pet이 존재하지 않음");
        }
        else {
            System.out.println("멍멍");
        }
    }
}
```

이렇게 `pet` 이 null인 상황 또한 처리하고 싶다면 `@Autowired` 애노테이션의 required 속성을 위 코드와 같이 false로 지정하면 된다.

<br>

`@Autowired` 애노테이션의 required 속성을 false로 지정하면 매칭되는 빈이 없어도 익셉션이 발생하지 않고 `setPet` 메서드를 실행하지 않는다.

스프링 5 버전부터는 `@Autowired` 애노테이션의 required 속성을 false로 하는 대신에 다음과 같이 의존 주입 대상에 자바 8의 `Optional`을 사용해도 된다.
```java
public class Person {
	private Pet pet;

    @Autowired
	public void setPet(Optional<Pet> petOpt) {
        if(petOtp.isPresent()) this.pet = petOpt.get();
		else this.pet = null;
	}

    public void bark() {
        ... // 생략
    }
}
```

<br>

필수 여부를 지정하는 세 번째 방법은 `@Nullable` 애노테이션을 사용하는 것이다.

```java
public class Person {
	private Pet pet;

    @Autowired
	public void setPet(@Nullable Pet pet) {
		this.pet = pet;
	}

    public void bark() {
        ... // 생략
    }
}
```

`@Autowired` 애노테이션을 붙인 세터 메서드에 `@Nullable` 애노테이션을 의존 주입 대상 파라미터에 붙이면, 스프링 컨테이너는 세터 메서드를 호출할 때 **자동 주입할 빈이 존재하면 해당 빈을 인자로 전달하고, 존재하지 않으면 null을 전달한다**

`required` 속성을 false로 지정하는 방식과의 차이점은 **빈이 존재하지 않아도 메서드가 호출된다는 점이다.**

<br>

앞서 설명한 3가지 방법은 필드에도 그대로 적용된다.

```java
@Autowired(required = false)
private Pet pet;

@Autowired
private Optional<Pet> petOpt;

@Autowired
@Nullable
private Pet pet;
```

## 생성자 초기화와 필수 여부 지정 방식 동작 이해

```java
public class MyPet {
	private Pet pet;

    public MyPet() {
        this.pet = new Pet();
    }

    @Autowired(required = false)
	public void setPet(Pet pet) {
		this.pet = pet;
	}

    public void bark() {
        ... //생략
    }
}
```
위 코드에서 <br>
**스프링 컨테이너는 빈을 초기화하기 위해 기본 생성자를 이용해서 객체를 생성하고** <br>
**의존 자동 주입을 처리하기 위해 `setPet()` 메서드를 호출한다.**

하지만 `required` 속성이 false이면 일치하는 빈이 존재하지 않을 때 `setPet()`을 호출하지 않는다. 

<br>

만약 아래 코드와 같이 `@Nullable` 애노테이션을 사용할 경우에는 null 값을 전달하기때문에 생성자에서 `this.pet = new Pet()` 코드로 초기화를 해도 `this.pet`에는 null값이 들어가게 된다.
```java
public class Person {
	private Pet pet;

    public MyPet() {
        this.pet = new Pet();
    }

    @Autowired
	public void setPet(@Nullable Pet pet) {
		this.pet = pet;
	}

    public void bark() {
        ... // 생략
    }
}
```

`Optional` 타입도 `@Nullable`과 유사하다.
<br>

기본 생성자에서 자동 주입 대상이 되는 필드를 초기화할 때는 이 점에 유의해야 한다.

<br>

## 자동 주입과 명시적 의존 주입 간의 관계

```java
@Configuration
public class Chapter3Conf {
	@Bean
	@Qualifier("dog")
	public Pet pet1() {
		return new Pet();
	}
	
	@Bean
	public Pet pet2() {
		return new Pet("고양이");
	}
	
	@Bean
	public Person person() {
		Person p = new Person();
		p.setAge(24);
		p.setName("Jang");
		p.setPet(pet2());
		return p;
	}
}
```

```java
public class Person {
	private Pet pet;

	@Autowired
	@Qualifier("dog")
	public void setPet(Pet pet) {
		this.pet = pet;
	}
    ...
}
```

만약 위 코드처럼 설정 클래스에서 `p.setPet(pet2())` 로 `pet2` 의존을 주입했는데 자동 주입 대상이면 어떻게 될까?

결과는 자동 주입인 `pet1` 의존이 주입된다.

<br>
