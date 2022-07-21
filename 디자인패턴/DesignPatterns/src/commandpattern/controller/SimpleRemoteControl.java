package commandpattern.controller;

import commandpattern.command.Command;

public class SimpleRemoteControl {
	Command slot; 
	
	public SimpleRemoteControl() {
	}
	
	public void setCommand(Command command) {
		//Command로 받기 때문에 execute() 메소드만 사용할 수 있다.
		slot = command;
	}
	
	public void buttonPressed() {
		slot.execute();
	}
}
