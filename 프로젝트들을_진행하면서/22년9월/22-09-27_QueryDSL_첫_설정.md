# 22년 09월 27일

## QueryDSL 첫 사용

캡스톤을 진행하면서 큰 문제는 기본적으로 `JpaRepository`를 구현하는 인터페이스에서 사용하는 메소드의 길이가 여러 컬럼들을 사용하면 너무 길어진다는 것이다. 

특히 조인을 하려면 기본적으로 메소드 길이가 더욱 길어지고, 그에 따라 가독성도 줄어든다. 추가적으로 `EntityGraph`와 같은 방식을 사용하면서 더욱 가독성이 떨어진다.

그래서 `QueryDSL`을 사용한다.

<br>

### Maven 설정

다음 항목들을 추가한다.

```xml
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-apt</artifactId>
</dependency>
<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-jpa</artifactId>
</dependency>
```

```xml
<plugin>
    <groupId>com.mysema.maven</groupId>
    <artifactId>apt-maven-plugin</artifactId>
    <version>1.1.3</version>
    <executions>
        <execution>
            <goals>
                <goal>process</goal>
            </goals>
            <configuration>
                <outputDirectory>src/main/generated</outputDirectory>
                <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
            </configuration>
        </execution>
    </executions>
</plugin>
```

<br>

### Config 파일 생성
```java
@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
``` 


<br>

### .gitignore 추가
QueryDSL 의존성을 추가하고 프로젝트를 업데이트하면 자동으로 `src/main/generated`위치에 Q파일들이 생긴다. 그러므로 `.gitignore`에 다음 항목을 추가한다.
```
generated
```

<br>

출처: https://smpark1020.tistory.com/185

<br>
<br>

## QueryDSL에서의 연관관계 조인

다음과 같이 연관관계가 있는 Entity를 `fetchJoin()`을 통해서 주입받을 수 있다. 일반 `leftJoin()`, `join()`등을 사용하면 `UserSubject`가 아닌 QueryDSL에서 제공하는 `Tuple`을 사용해야한다. 따로 레퍼런스를 뒤져보지 않고 테스트를 하면서 경험한 내용이기 때문에 확실하지는 않다. 정확한 내용은 나중에 정리한다.

```java
//fetchJoin()
List<UserSubject> usList = query.selectFrom(us)
		  .join(us.subjectInfo, si).fetchJoin()
		  .fetch();

//일반 조인
List<Tuple> usList = (List<Tuple>) query
			.select(us, si)
			.from(us)
			.join(us.subjectInfo, si)
			.fetch();
```


<br>
<br>

나중에 참고할 내용<br>
[QueryDsl 기본 문법 - 블로그](https://escapefromcoding.tistory.com/617)<br>
[QueryDSL 여러 테이블 조인 방법 - stackoverflow](https://stackoverflow.com/questions/47701172/how-to-join-multiple-querydsl-tables)<br>
[Spring Boot Data Jpa 프로젝트에 Querydsl 적용하기 - 블로그](https://jojoldu.tistory.com/372)<br>
[QueryDSL 튜플이나 DTO로 결과 반환하기-블로그](https://doing7.tistory.com/129)<br>


