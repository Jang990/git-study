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
	 * é�� 6
	 * 
	 *  Ŀ�ǵ� ����(Command Pattern)
	 * Ŀ��Ʈ ������ �̿��Ϸ��� �䱸 ������ ��ü�� ĸ��ȭ �� �� ������ �Ű������� �Ἥ ���� ���� �ٸ� �䱸 ������
	 * ���� ���� ���� �ֽ��ϴ�. ���� ��û ������ ť�� �����ϰų� �α׷� ����� ���� ������, �۾� ��� ��ɵ� ���� �����մϴ�.
	 * 
	 */
	public static void main(String[] args) {
		//������ ���� ���� ����
		Light light1 = new Light();
		Command commandLight = new LightOnCommand(light1);
		SimpleRemoteControl simpleContoller = new SimpleRemoteControl();
		
		simpleContoller.setCommand(commandLight); 
//		simpleContoller.buttonPressed();
		
		/*
		 * ���������� ������ �ְ� �� ���Ը��� on��ư off��ư �� �ִ� �������� ����ٰ� �����ϰ� �����غ���.
		 * �߰��� undo��ư�� ������ �ߴ� ������ ����Ѵ�.
		 */
		
		//���� ������ ���� ���� ����
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
		 * ������� ������µ� undo ��ư�� ��԰� �־���...
		 * �����?
		 * �׳� Command �������̽��� undo�� �߰��ϰ�
		 * �� Command Ŭ�������� execute���� �ϴ� �ൿ�� �ݴ� �ൿ�� undo�� �����ϸ� �ȴ�.
		 * �̰� ���ϱ�?
		 * �ƴϴ�. RemoteControl Ŭ�������� ����ڰ� ���������� ���� ��ư�� ����ϰ� 
		 * UNDO ��ư�� ������ �� �ʿ��� �۾��� ó���ϱ� ���� �ڵ带 �߰��ؾ� �Ѵ�.
		 */
		controller.onButtonWasPushed(0);
		System.out.println("undo��ư ����");
		controller.undoButtonWasPusher();
		
		/*
		 * undo ��ư�� �����ߴ�.
		 * ���� ���̵��� ��������. ��ǳ�⸦ �����غ���. 
		 * ��ǳ��� ��ǳ, ��ǳ, ��ǳ �� �ִ�.
		 * ��ǳ���� �ٲٴ� Ŀ�ǵ带 �����غ���.
		 * ��ǳ�Ⱑ ��ǳ���� �����Ǿ� ���� �� ��ǳ���� �ٲٴ� Ŀ�ǵ带 �����ϰ�. 
		 * undo�� �����ϱ� ���ؼ���
		 * prevSpeed��� ������ ��ǳ���� �����Ǳ��� ���� �̸� ������ ����
		 * undo��ư�� �������� prevSpeed�� �ӵ��� �ٽ� �����ϸ� �ȴ�.
		 */
		
		//CeilingFan.class �� CeilingFanHighCommand.class ����
		
		/*
		 *  UNDO ��ư�� ���� �� ���� �� �ֵ��� �Ϸ��� ��� �ؾ� �ǳ���?
		 * ���� �����̴�. ����� ���� �ƴϴ�. ���۷����� �����ϴ� �� �ƴ϶� Ŀ�ǵ带 ���ÿ� ��������� �ȴ�. 
		 */
	}

}
