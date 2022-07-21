package commandpattern.command;

import commandpattern.receiver.CeilingFan;

public class CeilingFanHighCommand implements Command {
	CeilingFan ceilingFan;
	int prevSpeed; //���� ��ǳ���� �ӵ��� �����ϴ� ����.
	
	public CeilingFanHighCommand(CeilingFan ceilingFan) {
		this.ceilingFan = ceilingFan;
	}
	
	@Override
	public void execute() {
		prevSpeed = ceilingFan.getSpeed(); //��ǳ�� �ӵ��� �����ϱ� �� �ӵ��� �����մϴ�.
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
