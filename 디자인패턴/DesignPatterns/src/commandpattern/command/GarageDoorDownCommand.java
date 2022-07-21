package commandpattern.command;

import commandpattern.receiver.GarageDoor;

public class GarageDoorDownCommand implements Command {
	GarageDoor garageDoor;
	
	public GarageDoorDownCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}
	
	@Override
	public void execute() {
		//�̷��� execute�� ���� ������ ���� ���� ���� �ִ�.
		garageDoor.lightOn();
		garageDoor.down();
		garageDoor.lightOff();
	}
	
	@Override
	public void undo() {
		garageDoor.lightOn();
		garageDoor.up();
		garageDoor.lightOff();
	}

}
