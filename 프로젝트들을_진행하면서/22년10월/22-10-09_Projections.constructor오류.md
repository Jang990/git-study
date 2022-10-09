|week_id                   |title |start_date |end_date   |lecture_id |lecture_video_id          |type |idx |title       |full_time |status |learning_time |
|--------------------------|------|-----------|-----------|-----------|--------------------------|-----|----|------------|----------|-------|--------------|
|LESN_2208231331442df60423 |1주차   |2022-08-29 |2022-12-16 |3          |LESN_2208231331442df60424 |온라인  |1   |의사소통능력 1차시  |37분       |0      |0 초           |
|LESN_2208231331442df60423 |1주차   |2022-08-29 |2022-12-16 |4          |LESN_2208231331442df60425 |온라인  |2   |의사소통능력 2차시  |33분       |0      |0 초           |
|LESN_2208231331442df60426 |2주차   |2022-08-29 |2022-12-16 |7          |LESN_2208231331442df60428 |온라인  |1   |의사소통능력 3차시  |36분       |0      |0 초           |
|LESN_2208231331442df60426 |2주차   |2022-08-29 |2022-12-16 |9          |LESN_2208231331442df60427 |온라인  |2   |의사소통능력 4차시  |27분       |0      |0 초           |
|LESN_2208231331442df60429 |3주차   |2022-08-29 |2022-12-16 |12         |LESN_2208231331442df6042a |온라인  |1   |의사소통능력 5차시  |32분       |0      |0 초           |
|LESN_2208231331442df60429 |3주차   |2022-08-29 |2022-12-16 |15         |LESN_2208231331442df6042b |온라인  |2   |의사소통능력 6차시  |30분       |0      |0 초           |
|LESN_2208231331442df6042c |4주차   |2022-08-29 |2022-12-16 |18         |LESN_2208231331442df6042e |온라인  |1   |의사소통능력 7차시  |31분       |0      |0 초           |
|LESN_2208231331442df6042c |4주차   |2022-08-29 |2022-12-16 |20         |LESN_2208231331442df6042d |온라인  |2   |의사소통능력 8차시  |33분       |0      |0 초           |
|LESN_2208231331442df6042f |5주차   |2022-08-29 |2022-12-16 |24         |LESN_2208231331442df60430 |온라인  |1   |의사소통능력 9차시  |36분       |0      |0 초           |
|LESN_2208231331442df6042f |5주차   |2022-08-29 |2022-12-16 |27         |LESN_2208231331442df60431 |온라인  |2   |의사소통능력 10차시 |35분       |0      |0 초           |
|LESN_2208231331442df60432 |6주차   |2022-08-29 |2022-12-16 |30         |LESN_2208231331442df60434 |온라인  |1   |의사소통능력 11차시 |35분       |0      |0 초           |
|LESN_2208231331442df60432 |6주차   |2022-08-29 |2022-12-16 |32         |LESN_2208231331442df60433 |온라인  |2   |의사소통능력 12차시 |36분       |0      |0 초           |
|LESN_2208231331442df60435 |7주차   |2022-08-29 |2022-12-16 |35         |LESN_2208231331442df60436 |온라인  |1   |의사소통능력 13차시 |39분       |0      |0 초           |
|LESN_2208231331442df60435 |7주차   |2022-08-29 |2022-12-16 |37         |LESN_2208231331442df60437 |온라인  |2   |의사소통능력 14차시 |35분       |0      |0 초           |
|LESN_2208231331442df60438 |8주차   |2022-08-29 |2022-12-16 |40         |LESN_2208231331442df6043a |온라인  |1   |의사소통능력 15차시 |28분       |0      |0 초           |


위 표는 아래 쿼리로 불러와지는 데이터들의 내용이다.

```java

List<LectureWeekData> lectures =
    query
    .from(wi)
    .innerJoin(wi.lectures, li)
    .innerJoin(ul).on(ul.subjectLecture.eq(li))
    .where(
    	ul.userSubject.eq(
    			JPAExpressions.select(us)
    				.from(us)
    				.where(us.userInfo.studentNumber.eq(studentId), 
    						us.subjectInfo.subjectId.eq(subjectId))
    	)
    )
    .transform(
    		GroupBy.groupBy(wi.weekId).list(
    			Projections.constructor(LectureWeekData.class, 
    				wi.weekId.as("lectureWeekId"),
    				wi.title,
    				wi.startDate,
    				wi.endDate,
    				// 여기 아래서 오류발생
    				GroupBy.list(
    						Projections.fields(LectureData.class,
    									li.lectureId.longValue(),
    									li.lectureVideoId,
    									li.type,
    									li.idx,
    									li.title,
    									li.fullTime,
    									ul.status,
    									ul.learningTime
    						)
    				).as("lectureDetailDTO")
    			)
    		)
    )


//사용되는 constructor
public LectureWeekData(String lectureWeekId, String title,  
Date startDate, Date endDate, 
List<LectureData> lectureDetailDTO) {
    관련로직...
}
```
여기서 오류가 걸렸다.

`Tuple`로 받아서 내가 따로 코드를 작성해서 생성자에 값을 집어넣으면 간단하긴 한데 이러면 나중에도 `Projections`를 제대로 사용할 수 없을 것 같아서 해결방법을 알아보려 했다.

<br>

`Exception "argument type mismatch"` 오류가 계속 뜬다. `GroupBy.list` 에서 `LectureData`리스트를 만드는 곳에서 계속 오류가 나타난다.

<br>

`.as("lectureDetailDTO")`도 추가해주고 `Projections.fields(LectureData.class,...)`도 잘 생성되는지 따로 테스트를 해봤는데 잘 생성되는 것이 확인 되었다.

또한 `Projections.constructor(LectureWeekData.class, ...)`에서 list를 넣는 `GroupBy.list(...)`를 빼고 진행했을 떄도 잘 생성되는 것을 확인했다.

<br>

그런데 이상하게 이 둘을 합쳐서 `constructor`로 넣으면 `argument type mismatch` 오류가 나타난다.

<br>

 참고<br>
[QueryDSL 공식 깃허브 예제](https://github.com/querydsl/querydsl/tree/master/querydsl-collections/src/test/java/com/querydsl/collections)<br>
[나와 비슷한 스택오버플로우 글](https://stackoverflow.com/questions/40256348/querydsl-exception-argument-type-mismatch-with-projection-bean-and-onetomany)

<br>
<br>

결과적으로 위 코드에서 `Projections.constructor` 부분을 `Projections.fields`로 바꾸니까 해결됐다.

내가 생성자를 고집했던 이유는 안에서 관련 cnt를 초기화해주기 위해서 고집했다.

하지만 굳이 생성자를 사용할 필요없이 관련 로직을 서비스에서 로직을 처리하면 됐다. 

*따지고 보면 비즈니스 로직이니까 지금처럼 서비스에서 처리하는게 더 알맞는게 맞지 않나?*

