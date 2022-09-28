# 22년 09월 28일

## QueryDSL 커스텀 Repository
기존에 있던 `UserSubjectRepository`를 커스텀해보겠다.

간단한 커스텀 방법은 다음과 같다.

1. `UserSubjectRepositoryCustom` 인터페이스를 생성

```java
public interface UserSubjectRepositoryCustom {
	List<UserSubject> findLectureInfo(...);
}
```

<br>

2. `UserSubjectRepositoryCustom` 인터페이스를 상속받는 `UserSubjectRepositoryImpl` 구현
```java
@RequiredArgsConstructor
public class UserSubjectRepositoryImpl implements UserSubjectRepositoryCustom{
	private final JPAQueryFactory query; // 꼭 추가해주기
    
    //QueryDSL을 이용해 원하는 로직을 구현하면 된다.
    @Override
	public List<UserSubject> findLectureInfo(...) {
        ...
    }
}
```

<br>

3. 기존 `UserSubjectRepository`에 `UserSubjectRepositoryCustom` 인터페이스를 implements
```java
public interface UserSubjectRepository extends JpaRepository<UserSubject, String>, UserSubjectRepositoryCustom {
    ...
}
```

<br>

4. `UserSubjectRepository`에서 `UserSubjectRepositoryImpl`에 구현한 QueryDSL 로직을 사용
```java
UserSubjectRepository repository;
repository.findLectureInfo(...);
```

<br>

### 네이밍 규칙 - 매우 중요!

일종의 공식이라고 보시면 되는데요, **Custom이 붙은 인터페이스를 상속한 Impl 클래스의 코드는 Custom 인터페이스를 상속한 JpaRepository에서도 사용할 수 있습니다.**

<br>

내용 출처: https://jojoldu.tistory.com/372

<br>
<br>

## `fetchJoin()`의 문제점 중 하나 - `MutipleBagFetchException`

```
구현체에 따라 되기도 하는데 둘 이상의 컬렉션을 페치할 수 없다. 컬렉션 * 컬렉션의 카테시안 곱이 만들어지므로 주의해야 한다. 하이버네이트를 사용하면 MutipleBagFetchException이 발생한다.

김영한 JPA 책 내용 중 일부...
```

현재 내가 `SubjectInfo` -> `WeekInfo` -> `LectureInfo` 테이블을 조인하면서 발생하는 문제이다. `SubjectInfo` 테이블에 `List<WeekInfo>`가 있고 `WeekInfo`테이블에는 `List<LectureInfo>`가 있기 때문에 발생한다.

<br>

해결방안
1. 엔티티의 기존 `List`자료형을 `Set` 자료형으로 바꾸기
2. 이상적인 솔루션: 쿼리를 여러번 사용

출처: https://www.baeldung.com/java-hibernate-multiplebagfetchexception

지금와서 프로젝트에서 `List`를 `Set`형식으로 바꾸는 것은 너무 많은 시간이 소요된다. 2번째 '이상적인 솔루션'이 확실히 편한것 같다.

<br>
<br>

나중에 참고할 내용<br>
[QueryDSL 공식 문서](http://querydsl.com/static/querydsl/3.7.2/reference/ko-KR/html/index.html) <br>
[Tuple을 받아서 DTO안에 List형식 DTO를 매핑시키는 법](https://bbuljj.github.io/querydsl/2021/05/17/jpa-querydsl-projection-list.html)


