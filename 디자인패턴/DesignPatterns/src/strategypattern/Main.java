package strategypattern;

import strategypattern.character.King;
import strategypattern.character.Knight;
import strategypattern.character.Troll;

public class Main {
	
	/*
	 * é�� 1
	 * 
	 *  ��Ʈ��Ƽ�� ����(Strategy Pattern)
	 * �˰����� �����ϰ� ������ ĸ��ȭ�Ͽ� ��ȯ�ؼ� ����� �� �ֵ��� �����.
	 * ��Ʈ��Ƽ�� ������ Ȱ���ϸ� �˰����� ����ϴ� Ŭ���̾�Ʈ�ʹ� ���������� �˰����� ������ �� �ִ�.
	 * 
	 *  �������� ��Ģ(= ��ü���� ��Ģ)
	 * 1. ���ø����̼ǿ��� �޶����� �κ��� ã�Ƴ���, �޶����� �ʴ� �κ����� ���� �и���Ų��.
	 * (���� �޶����ų� �ٲ�� �κ��� �������̽��� �̿��ؼ� ����. - å���� display() �κ��� �ѹ� �����ϸ� �� �ٲ��� �ʴ´�.)
	 * 2. ������ �ƴ� �������̽��� ���缭 ���α׷��� �Ѵ�.
	 * (���� ����ÿ� ���̴� ��ü�� �ڵ忡 ���� �������� �ʵ��� � ���� ���Ŀ� ���� ���α׷��������ν� ������ Ȱ��.)
	 * 3. ���(extends)���ٴ� ����(implements)�� Ȱ��
	 * (�������� ũ�� ����ų �� ����)
	 */
	
	public static void main(String[] args) {
		Troll troll = new Troll();
		troll.fight();
		
		King king = new King();
		king.fight();
		
		Knight knight = new Knight();
		knight.fight();
	}

}
