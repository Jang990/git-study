# 22년 11월 13일

## Spring JPA - 1차 캐시와 findBy 커스텀 쿼리

### 현재 상황

아래는 내가 채팅 모듈을 개발하면서 작성한 테스트 코드다.

```java
@Test
@Transactional
void test() {
	// given
	SubjectInfo subject = createSubject();
	UserInfo user1 = createUser1();
	UserInfo user2 = createUser2();
    createAdmin();
	
	// when
	chatService.enterChatRoom(subjectId, user1.getStudentNumber());
	chatService.enterChatRoom(subjectId, user2.getStudentNumber());

	createMsg(user1, user2, subject);
	
	chatService.exitChatRoom(subjectId, user1.getStudentNumber());
	
	Pageable pageable = PageRequest.of(0, 30);
	List<ChatMsgForTestDTO> chats = chatMessageRepository.findChatMessage(subjectId, pageable);

    ...
}
```

`enterChatRoom(...)`에서 채팅방에 입장하고, `exitChatRoom(...)` 에서 그 입장한 채팅방에서 퇴장하면서 DB에서 삭제하기 위해 `delete...()` 메서드를 사용한다.

현재 테스트에서는 `exitChatRoom(...)` 메서드에 `delete...(...)`에서 오류가 난다.

```java
// 채팅방 입장 로직
if(userSubjectRepository.findBySubjectInfo_SubjectIdAndUserInfo_StudentNumber(roomId, studentNumber).isPresent()) {
    throw new IllegalArgumentException("이미 채팅방에 입장해있습니다. \n"
        + "SubjectId: " + roomId + "\t StudentId: " + studentNumber);
}
UserSubject us = new UserSubject(user, subject);
us = userSubjectRepository.save(us);

...

// 채팅방 퇴장 로직
/* 
 오류가 나던 부분 
userSubjectRepository.deleteByUserInfo_StudentNumberAndSubjectInfo_SubjectId(studentNumber, roomId);
*/
// 해결 부분
UserSubject exitRoom = userSubjectRepository
    .findBySubjectInfo_SubjectIdAndUserInfo_StudentNumber(roomId, studentNumber)
    .orElseThrow(() -> new IllegalArgumentException("채팅방에 입장해있지 않습니다. \n"+ 
    "SubjectId: " + roomId + "\t StudentId: " + studentNumber));
userSubjectRepository.delete(exitRoom);
```

위와 같이 채팅방 퇴장 로직 부분에 오류가 나던 부분을 해결 부분으로 바꾸면 해결이 된다.

<br>
<br>

### Spring JPA - 1차 캐시

먼저 나는 1차 캐시를 염두해두고 코드를 짜봤는데 거의 모든 부분에서 쿼리가 나간다. 

다음 Spring Data JPA의 1차 캐시 조건을 확인하자.

<hr>

**엔티티 조회 시 쿼리를 생략하고 1차 캐시를 반환하는 조건**
1. `EntityManager.find` (by PK)를 통해 조회될 경우에만 1차 캐시를 반환한다. Spring-data-jpa 사용 시 이는 `repository.findById()`이다.
2. 커스텀 쿼리는 1차 캐시를 사용할 수 없다. ex) `productRepository.findByProductName("상품1");`
3.  PK 를 통해 조회하더라도 그것이 커스텀 쿼리라면 1차 캐시를 사용할 수 없다. `findOneById(Long id)` 라는 커스텀 쿼리 메서드는 PK 로 조회하지만 1차 캐시가 존재해도 무조건 쿼리를 호출한다.
4.  위의 이유로, *JPQL* 은 무조건 쿼리를 호출한다. *JPQL* 은 `EntityManager.createQuery` 를 통해 쿼리를 직접 생성해 호출하며, `EntityManager.find` 와는 다르다.

<br>

레퍼런스<br>
https://parkcheolu.tistory.com/391
<hr>

<br>

결과적으로 `deleteByUserInfo_...(...)`를 앞에서 `findBy`로 불러와서 `delete()`에 넣는 형식으로 바꾸는 것인데 왜 오류가 나는 것인지는 모르겠다. 나중에 시간이 나면 다시 확인해 보겠다.

<br>
<br>
<br>
<br>

## 커스텀 쿼리

```java
// JPQL 커스텀 쿼리
UserSubject exitRoom = userSubjectRepository.findBySubjectInfo_SubjectIdAndUserInfo_StudentNumber(subjectId, studentNumber)
```
```sql
-- 작동 쿼리문
select
    generatedAlias0 
from
    UserSubject as generatedAlias0 
left join
    generatedAlias0.subjectInfo as generatedAlias1 
left join
    generatedAlias0.userInfo as generatedAlias2 
where
    (
        generatedAlias1.subjectId=:param0 
    ) 
    and (
        generatedAlias2.studentNumber=:param1 
    ) */ select
        usersubjec0_.us_id as us_id1_7_,
        usersubjec0_.subject_id as subject_2_7_,
        usersubjec0_.student_number as student_3_7_ 
    from
        user_subject usersubjec0_ 
    left outer join
        subject_info subjectinf1_ 
            on usersubjec0_.subject_id=subjectinf1_.subject_id 
    left outer join
        user_info userinfo2_ 
            on usersubjec0_.student_number=userinfo2_.student_number 
    where
        subjectinf1_.subject_id=? 
        and userinfo2_.student_number=?
```

