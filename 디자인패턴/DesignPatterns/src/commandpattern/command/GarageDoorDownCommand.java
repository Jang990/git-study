package commandpattern.command;

import commandpattern.receiver.GarageDoor;

public class GarageDoorDownCommand implements Command {
	GarageDoor garageDoor;
	
	public GarageDoorDownCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}
	
	@Override
	public void execute() {
		//이렇게 execute에 여러 동작을 집어 넣을 수도 있다.
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
