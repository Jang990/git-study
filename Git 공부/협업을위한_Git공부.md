# Git 협업 공부

## Source Tree

`revert`: revert할 커밋 우클릭 - '커밋 되돌리기...' 클릭 <br>
해당 커밋에 변경내역이 undo된 상태의 커밋이 생성됨

`reset`: 커밋 우클릭 - '현재 브랜치로 초기화' 

<br>
<br>

## branch 나누고 합치기

여러 브랜치로 나누고 합칠 때는 2가지 방법을 생각할 수 있다.
첫 번째는, `merge`이고 두 번째는 `rebase`이다.

<br>

`merge`의 경우 브랜치의 흔적을 남긴다. 즉 브랜치의 커밋기록들이 잔가지로 남는다. <br>
`rebase`는 깔끔하게 하나의 줄기로 커밋 내역이 유지가 된다. 곁가지들을 싹 다 잘라서 main 줄기로 가져오는 방식이기 때문에 깔끔하다.

`merge`의 경우 많은 브랜치가 사용되는 프로젝트에서는 프로젝트의 진행 내역들을 보고 파악하기가 어렵다.

<br>

**프로젝트의 진행 성격에 따라 브랜치의 사용 내역들을 남겨둘 필요가 있다면 `merge`를, 히스토리를 깔끔하게 남겨둘 필요가 있다면 `rebase`가 적절한 선택이다.**

**추가적으로 이미 팀원들과 공유된 커밋에 대해서는 `rebase`를 사용하는 것은 좋지 않다.**

<br>
<br>

### `merge`

```git
1. 메인 브랜치로 이동
git switch main

2. merge하기 (병합할 대상 브랜치 - merge_branch)
git merge merge_branch

3. 이전 브랜치 삭제 
git branch -d merge_branch
```

<br>

### `rebase`
```git
중요) rebase할 때는 merge와 반대로 대상 브랜치로 이동한다.

1. 대상 브랜치로 이동
git switch rebase_branch

2. rebase로 합치기
git rebase main


3. 메인 브랜치로 이동
git switch main

4. 나중에 브랜치 심화에서 배운다. 이것도 해줘야 한다.
git merge rebase_branch
```
<br>
<br>


## 충돌 해결


```
git merge aaa
```
특정 브랜치를 다음 명령어로 병합했을 때 충돌나는 파일이 있다면 아래와 같이 알려준다.

<br>

```
다른 작업들~~~~~1
다른 작업들~~~~~2
<<<<<<< HEAD
메인 작업공간
=======
Jang 작업
>>>>>>> jang
다른 작업들~~~~~3
```

<br>

만약 충돌을 해결하기 어렵다면 다음 명령어를 사용하여 되돌릴 수도 있다.
```
git merge --abort
```

다 `merge`나 `rebase`를 해서 다 사용한 브랜치는 그때그때 지워주자. 만약 병합되지 않은 브랜치를 삭제하고 싶다면 강제삭제를 진행하면 된다.

<br>
<br>

### Source Tree에서 Merge시 충돌

Source Tree에서 Merge할 시에 충돌이 일어나면 커밋하지 않고 일단 변경내용을 기다린다. 그리고 개발자가 충돌을 해결하고 직접 커밋을 하면 된다.

<br>
<br>

## github 연동

```
1. 로컬 git 저장소에 원경 저장소 연결
git remote add origin (원격 저장소 주소)

2. 기본 브랜치명을 main으로 설정
git branch -M main

3. 로컬 저장소의 커밋 내역들을 원격으로 업로드
git push -u origin main
-u 또는 --set-upstream: 현재 브랜치와 명시된 원격 브랜치 기본 연결

```

<br>

### github 레포지토리 파일 받기

```
1. 받을 폴더로 이동
2. 우클릭 후 Git Bash Here로 Git Bash 열기
3. git clone (원격 저장소 주소)
```

<br>
<br>

## Git Hub 오류사항

팀원 A와 B가 있다고 가정한다.
```
1. A와 B가 함께 git pull을 했다. - 성공

2. A가 변경사항을 commit하고 push한다. - 성공

3. B가 변경사항을 commit하고 push하려 한다. - push에서 실패
```

즉 다음과 같은 사항이 발생하는 것이다.
```
 원격 저장소(GitHub)
공통의 커밋 ---- A의 커밋 (팀원 A가 마지막으로 커밋함)

 A의 로컬 저장소
공통의 커밋 ---- A의 커밋 (원격저장소와 동기화 되어 있음)

 B의 로컬 저장소
공통의 커밋 ---- B의 커밋 (push 실패)
```
B의 경우 원격 저장소에 적용된 새 버전이 있으므로 `push`가 불가능하다. 즉 `push`를 하기 전에 `pull`을 먼저 해야한다.

<br>

pull의 옵션은 두 가지가 있다.
1. `git pull --no-rebase` - `merge`방식
2. `git pull --rebase` - `rebase`방식

앞서 협업시에 rebase를 하지 말라고 했지만 `pull`시에 rebase는 괜찮다.

```
 merge 방식
공통의 커밋 ---- A의 커밋 ---- 병합된 커밋
          \---- B의 커밋 ----/

 rebase 방식
공통의 커밋 ---- A의 커밋 ---- B의 커밋
```

두 방식 모두 충돌을 해결해야 한다는 것을 알아두자.

<br>
<br>

## 원격 브랜치

### 원격 브랜치 생성
로컬에 브랜치를 만들어서 원격 브랜치를 생성하기
```
1. 로컬 브랜치 생성
git branch test

2. 원격으로 보내보기 - 실패함
git push   - 실패
에러 로그로 난 지금 test 브랜치를 어디에 push해야할지 모른다.
origin이라는 원격에 test라는 브랜치를 만들고 싶은거 같은데 
그럴려면 아래와 같은 명령어를 사용해라 라고 나온다.

3. git push -u origin test

4. 원격 저장소(GitHub)에 추가된 브랜치 확인
```

<br>

### 원격 브랜치 제거
```
git push (원격 이름) --delete (원격의 브랜치 명)
git push origin --delete test
```

<br>
<br>
<br>

## Merge 방식 설명 2-way, 3-way

### 2-way merge
|브랜치AAA|브랜치BBB|2-Way|
|---|---|---|
|A|A|A|
|B|C|충돌!|
|C|D|충돌!|
|E|F|충돌!|

현재 2개의 브랜치AAA와 브랜치BBB를 만들어서 병합한다고 할때 2-way merge 방식은 위 표와 같다.

A의 경우 브랜치AAA와 BBB 모두 같기 때문에 병합시 충돌이 일어나지 않지만 아래 세가지 데이터는 충돌이 일어난다. 이렇게 충돌을 감지해주는 것이다.

<br>

### 3-way merge
2-way merge도 좋지만 구식이다. 3-Way Merge는 더 좋다.

|브랜치AAA|공통의조상커밋|브랜치BBB|3-Way|
|---|---|---|---|
|A|A|A|A|
|B|B|K|K|
|H|D|D|H|
|E|D|F|충돌!|

첫 번째 데이터인 경우는 변화가 없기 때문에 Merge시 충돌이 일어나지 않는다.

두 번째 데이터의 경우 '공통의 조상 커밋'과 '브랜치AAA'의 값은 같다. <br>
하지만 '브랜치BBB'에서 K로 변화가 생겼다. 그래서 병합시에 K가 되는 것이다.

세 번째 데이터도 마찬가지이다.

네 번째 데이터의 경우에는 '공통의 조상 커밋', '브랜치AAA', '브랜치BBB' 모두 다른 값을 가지고 있기 떄문에 충돌이 발생하는 것이다.





