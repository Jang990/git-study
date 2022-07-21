package commandpattern.controller;

import commandpattern.command.Command;

public class SimpleRemoteControl {
	Command slot; 
	
	public SimpleRemoteControl() {
	}
	
	public void setCommand(Command command) {
		//Command�� �ޱ� ������ execute() �޼ҵ常 ����� �� �ִ�.
		slot = command;
	}
	
	public void buttonPressed() {
		slot.execute();
	}
}
