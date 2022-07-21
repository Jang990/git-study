package commandpattern;

import java.util.ResourceBundle.Control;

import commandpattern.command.Command;
import commandpattern.command.GarageDoorDownCommand;
import commandpattern.command.GarageDoorOpenCommand;
import commandpattern.command.LightOffCommand;
import commandpattern.command.LightOnCommand;
import commandpattern.controller.RemoteControl;
import commandpattern.controller.SimpleRemoteControl;
import commandpattern.receiver.GarageDoor;
import commandpattern.receiver.Light;

public class Main {
	/*
	 * 챕터 6
	 * 
	 *  커맨드 패턴(Command Pattern)
	 * 커맨트 패턴을 이용하려면 요구 사항을 객체로 캡슐화 할 수 있으며 매개변수를 써서 여러 가지 다른 요구 사항을
	 * 집어 넣을 수도 있습니다. 또한 요청 내역을 큐에 저장하거나 로그로 기록할 수도 있으며, 작업 취소 기능도 지원 가능합니다.
	 * 
	 */
	public static void main(String[] args) {
		//간단한 원격 조정 예제
		Light light1 = new Light();
		Command commandLight = new LightOnCommand(light1);
		SimpleRemoteControl simpleContoller = new SimpleRemoteControl();
		
		simpleContoller.setCommand(commandLight); 
//		simpleContoller.buttonPressed();
		
		/*
		 * 여러가지의 슬롯이 있고 각 슬롯마다 on버튼 off버튼 이 있는 리모컨을 만든다고 생각하고 구현해보자.
		 * 추가로 undo버튼을 누르면 했던 동작을 취소한다.
		 */
		
		//여러 슬롯의 원격 조정 예제
		Light light = new Light();
		GarageDoor garageDoor = new GarageDoor();
		LightOnCommand onLight = new LightOnCommand(light);
		LightOffCommand offLight = new LightOffCommand(light);
		GarageDoorOpenCommand openDoor = new GarageDoorOpenCommand(garageDoor);
		GarageDoorDownCommand downDoor = new GarageDoorDownCommand(garageDoor);
		
		RemoteControl controller = new RemoteControl();
		controller.setCommand(0, onLight, offLight);
		controller.setCommand(1, openDoor, downDoor);
		
		controller.onButtonWasPushed(0);
		controller.offButtonWasPushed(0);
		
		controller.onButtonWasPushed(1);
		controller.offButtonWasPushed(1);
		/*
		 * 여기까지 만들었는데 undo 버튼을 까먹고 있었다...
		 * 어떡하지?
		 * 그냥 Command 인터페이스에 undo를 추가하고
		 * 각 Command 클래스에서 execute에서 하는 행동에 반대 행동을 undo로 구현하면 된다.
		 * 이게 끝일까?
		 * 아니다. RemoteControl 클래래스에 사용자가 마지막으로 누른 버튼을 기록하고 
		 * UNDO 버튼이 눌렸을 때 필요한 작업을 처리하기 위한 코드를 추가해야 한다.
		 */
		controller.onButtonWasPushed(0);
		System.out.println("undo버튼 눌림");
		controller.undoButtonWasPusher();
		
		/*
		 * undo 버튼을 구현했다.
		 * 이제 난이도를 높혀보자. 선풍기를 구현해보자. 
		 * 선풍기는 미풍, 약풍, 강풍 이 있다.
		 * 강풍으로 바꾸는 커맨드를 구현해보자.
		 * 선풍기가 미풍으로 설정되어 있을 때 강풍으로 바꾸는 커맨드를 실행하고. 
		 * undo를 실행하기 위해서는
		 * prevSpeed라는 변수로 강풍으로 설정되기전 값을 미리 저장해 놓고
		 * undo버튼이 눌려지면 prevSpeed의 속도로 다시 설정하면 된다.
		 */
		
		//CeilingFan.class 와 CeilingFanHighCommand.class 참고
		
		/*
		 *  UNDO 버튼을 여러 번 누를 수 있도록 하려면 어떻게 해야 되나요?
		 * 좋은 질문이다. 어려운 일은 아니다. 래퍼런스만 저장하는 게 아니라 커맨드를 스택에 집어넣으면 된다. 
		 */
	}

}
