package commandpattern.command;

import commandpattern.receiver.CeilingFan;

public class CeilingFanHighCommand implements Command {
	CeilingFan ceilingFan;
	int prevSpeed; //이전 선풍기의 속도를 저장하는 변수.
	
	public CeilingFanHighCommand(CeilingFan ceilingFan) {
		this.ceilingFan = ceilingFan;
	}
	
	@Override
	public void execute() {
		prevSpeed = ceilingFan.getSpeed(); //선풍기 속도를 조정하기 전 속도를 저장합니다.
		ceilingFan.high(); 
	}
	@Override
	public void undo() {
		switch(prevSpeed) {
		case CeilingFan.HIGH:
			ceilingFan.high();
			break;
		case CeilingFan.MEDIUM:
			ceilingFan.medium();
			break;
		case CeilingFan.LOW:
			ceilingFan.low();
			break;
		case CeilingFan.OFF:
			ceilingFan.off();
			break;
		}
	}
}
