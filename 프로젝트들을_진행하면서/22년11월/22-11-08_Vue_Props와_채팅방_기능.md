# 22년 11월 1일

## Vue Props
Vue Props를 전달했을 때 undefined가 뜨는 문제

```vue
<!-- 정적으로 "abc"라는 문자열을 전달할 때 -->
<test-chat test="abc"></test-chat>

<!--  동적으로 aaa라는 변수에 Hello 문자열을 전달할 때-->
<test-chat :test="aaa"></test-chat>

...
<script>
...
data() {
    return {
        aaa: "Hello"
    }
}
</script>
```

<br>
<br>

## 채팅방 기능

```java
private final SimpMessageSendingOperations sendingOperations;
/*
 	/receive로 메시지가 들어오면
	"/topic/chat/room/" + message.getRoomId()을 구독한 사용자들에게 
	ChatMsgForTestDTO 객체를 JSON형태로 뿌려준다
 */
@MessageMapping("/receive")
//	@SendTo("/topic/chat")
public void test(ChatMessageDTO message) { 
	sendingOperations.convertAndSend(
			"/topic/chat/room/" + message.getRoomId(), 
			new ChatMsgForTestDTO(message.getSender(), message.getMessage())
		);
}
```