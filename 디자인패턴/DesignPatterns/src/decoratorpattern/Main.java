package decoratorpattern;

import decoratorpattern.coffeetype.Espresso;
import decoratorpattern.coffeetype.HouseBlend;
import decoratorpattern.deco.Mocha;
import decoratorpattern.deco.Whip;

public class Main {

	/*
	 *  é�� 3
	 *  
	 *  ���ڷ����� ����(Decorator Pattern)
	 * ��ü�� �߰����� ����� �������� ÷���Ѵ�. 
	 * ���ڷ����ʹ� ����Ŭ������ ����� ���� ���ؼ� ����� �����ϰ� Ȯ���� �� �ִ� ����� �����Ѵ�. 
	 * 
	 *  ������ ��Ģ
	 * 5. Ŭ������ Ȯ�忡 ���ؼ��� ���� �־�� ������ �ڵ� ���濡 ���ؼ��� ���� �־�� �Ѵ�.(=OCP(Open-Closed Principle))
	 * (���� �ڵ�� �ǵ帮�� ���� ä�� Ȯ���� ���ؼ� ���ο� �ൿ�� �����ϰ� �߰��� �� �ֵ��� �ϴ°� ��ǥ�̴�.)
	 * (������ OCP�� �����ϴ� ���� ���ʿ��ϰ� �����ϰ� �����ϱ� ���� �ڵ带 ����� �Ǵ� ���ۿ��� ���� �� ������ �����ؾ� �Ѵ�.)
	 * 
	 * ���� HouseBlend�� Ư�� ������ �Ѵٰ� �����غ���
	 * HouseBlend�� 20% ������ �ϴµ�
	 * HouseBlend�� ��ī�� ����ũ���� �߰��Ѵٸ� 
	 * ��ī�� ����ũ���� ���Ե� ������ ���εǾ�� �� ���̴�
	 * (houseblend.cost() + .10 + .20) * 0.2; //�̷�������
	 * ������ �ڵ�󿡼� 
	 * houseblend = new HouseBlend(); //�̷��� ó���� �����
	 * houseblend = new Mochao(houseblend); // �ٽ� �̷������� ���Ѵ�
	 * �̷��� ��� HouseBlend���� �Ǵ��� �� ������? 
	 * �̷��� ������ ���� ��������� ������ �˾Ƴ��� �ڵ忡 ���ڷ����� ������ �����ϸ� �ڵ尡 ����� �۵����� ���� �� �ִ�.
	 * ���� ���� ��Ҹ� �������� ���ư��� �ڵ带 ������ �Ѵٸ� 
	 * ���ø����̼� ������ ��ü �� ���ڷ����� ������ ����ϴ� �Ϳ� ���ؼ� �ٽ� �� �� ������ �� �ʿ䰡 �ִ�.
	 * 
	 * ���ڷ����ʹ� ���ΰ� �ִ� ��ü�� �ൿ�� �߰����� ���� �뵵�� ������� ���̴�.
	 * 
	 * ���ڷ����� ������ Ȱ���ؼ� �������� �ϴٺ��� ����� Ŭ�������� �ʹ� ��������. �ڵ带 ���� ������ ���忡���� ���Ӵ�.
	 * ������ �ϴ� �ľ��ϰ� ���� Ŭ������ ���ڷ����ͷ� ���μ� ���ϴ� �ൿ�� ������ �� �ִ�.
	 * 
	 * 142p ���ڷ����� ������ ����(?) ������
	 */
	
	public static void main(String[] args) {
		Beverage espresso = new Espresso(); //���Ⱑ �߿��ϴ�. Beverage�� ����Ÿ���� �����ϰ� Espresso�� ��ü�� �����
		espresso = new Whip(espresso);
		espresso = new Mocha(espresso);
		System.out.println("�ֹ��� : " + espresso.getDescroption());
		System.out.println("���� : " + espresso.cost() + "$");
		System.out.println();
		
		Beverage houseblend = new HouseBlend();
		houseblend = new Whip(houseblend);
		houseblend = new Whip(houseblend);
		System.out.println("�ֹ��� : " + houseblend.getDescroption());
		System.out.println("���� : " + houseblend.cost() + "$");
		System.out.println();
		
		//137 ���������� ���� �ڵ�
		Beverage houseblend2 = new HouseBlend();
		houseblend2.setSize(Beverage.GRANDE);
		houseblend2 = new Mocha(houseblend2);
		houseblend2 = new Mocha(houseblend2);
		System.out.println("�ֹ��� : " + houseblend2.getDescroption());
		System.out.println("���� : " + houseblend2.cost() + "$");
	}
}
