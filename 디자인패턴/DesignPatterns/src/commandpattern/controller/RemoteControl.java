package commandpattern.controller;

import commandpattern.command.Command;
import commandpattern.command.NoCommand;

public class RemoteControl {
	private static final int MAX_SLOT = 2;
	private Command[] onCommand;
	private Command[] offCommand;
	
	private Command undoCommand;//undo�� �����ϱ����� ���������� ���� Ŀ�ǵ带 ������ ����
	
	public RemoteControl() {
		onCommand = new Command[MAX_SLOT];
		offCommand = new Command[MAX_SLOT];
		
		//Null ��ü ���� - Ŭ�����ȿ� ���� ���� ����
		Command noCommand = new NoCommand();
		for (int i = 0; i < offCommand.length; i++) {
			onCommand[i] = noCommand;
			offCommand[i] = noCommand;
		}
		
		undoCommand = noCommand;
	}
	
	public void setCommand(int slot, Command onCommand, Command offCommand) {
		this.onCommand[slot] = onCommand;
		this.offCommand[slot] = offCommand;
	}
	
	public void onButtonWasPushed(int slot) {
		onCommand[slot].execute();
		undoCommand = onCommand[slot];
	}
	
	public void offButtonWasPushed(int slot) {
		offCommand[slot].execute();
		undoCommand = offCommand[slot];
	}
	
	public void undoButtonWasPusher() {
		undoCommand.undo();
	}
	
}	
