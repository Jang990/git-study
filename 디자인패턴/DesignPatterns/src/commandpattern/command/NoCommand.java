package commandpattern.command;

public class NoCommand implements Command {
	/*
	 * NoCommand ��ü�� ������ �� ��ü(Null Object)�̴�.
	 * ���� ������ ��ü�� ������ Ŭ���̾�Ʈ �ʿ��� null ó���� ���� �ʾƵ� �ǵ��� �ϰ� ���� �� Ȱ���ϸ� ����.
	 * �����ݾ�...
	 * if(onCommand[slot] != null) {
	 * 	onCommands[slot].execute();
	 * }
	 * �� ��ü�� ���� ������ ���Ͽ��� �����ϰ� ���ȴ�. �� ��ü�� ������ ������ �������� �з��ϱ⵵ �Ѵ�.
	 */
	
	@Override
	public void execute() {
	}
	
	@Override
	public void undo() {
	}
}
