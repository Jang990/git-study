package commandpattern.command;

public class NoCommand implements Command {
	/*
	 * NoCommand 객체는 일종의 널 객체(Null Object)이다.
	 * 딱히 리턴할 객체는 없지만 클라이언트 쪽에서 null 처리를 하지 않아도 되도록 하고 싶을 때 활용하면 좋다.
	 * 귀찮잖아...
	 * if(onCommand[slot] != null) {
	 * 	onCommands[slot].execute();
	 * }
	 * 널 객체는 여러 디자인 패턴에서 유용하게 사용된다. 널 객체를 일종의 디자인 패턴으로 분류하기도 한다.
	 */
	
	@Override
	public void execute() {
	}
	
	@Override
	public void undo() {
	}
}
