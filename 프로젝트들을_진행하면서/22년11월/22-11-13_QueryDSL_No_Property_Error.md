# 22년 11월 13일

## QueryDSL No property ... found for type ... 에러

```java
// 레포지토리
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, ChatMessageRepositoryCustom {
	public List<ChatMessage> findByUser(UserInfo user);
}


//querydsl 인터페이스
public interface ChatMessageRepositoryCustom {
	List<ChatMessage> findChatMessage(String subjectId);
}


// querydsl 구현부
// @Repository - 해결방법
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {
	private final JPAQueryFactory query;
	
	@Override
	public List<ChatMessage> findChatMessage(String subjectId) {
		...
	}
}
```

위와 같이 구현을 하던 중에 다음과 같은 에러가 생겼다. <br>
내 생각에는 뭔가 QueryDSL이 아닌 JPA 레포지토리에서 findBy를 잘못써서 생기는 듯한 에러가 보였다.

```
Caused by: org.springframework.data.mapping.PropertyReferenceException: 
No property 'findChatMessage' found for type 'ChatMessage'!
```

그래서 QueryDSL 부분을 레포지토리에서 끊고 테스트를 해봤는데 정상 작동되는 것을 확인했다.

그래서 QueryDSL이 제대로 적용이 안된거 같아서 QueryDSL에 네이밍규칙(`Custom`, `Impl`)이 제대로 지켜지지 않았는지 확인해 봤는데 아니였다.

<br>

결과적으로 다음과 같이 `@Repository` 어노테이션을 붙혀줬더니 에러가 해결이 됐다.

```java
// querydsl 구현부
@Repository // 해결방법
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {
	...
}
```

이전에 구현한 `UserSubjectRepositoryImpl`은 어노테이션 없이 잘 돌아갔는데 무슨 차이로 에러가 난건지 아직은 모르겠다.

<br>
<br>

참고 <br>
https://okky.kr/articles/1049124

