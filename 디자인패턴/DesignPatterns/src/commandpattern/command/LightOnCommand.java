package commandpattern.command;

import commandpattern.receiver.Light;

public class LightOnCommand implements Command {
	Light light;
	
	public LightOnCommand(Light light) {
		this.light = light;
	}
	
	@Override
	public void execute() {
		this.light.on();
	}
	
	@Override
	public void undo() {
		this.light.off();
	}
}
