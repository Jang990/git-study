package factorypattern.pizza;

public class ChicagoStyleCheesePizza extends Pizza {
	public ChicagoStyleCheesePizza() {
		name = "��ī�� ��Ÿ�� �� ��� ġ�� ����";
		dough = "���� �β��� ����";
		sauce = "�÷� �丶�� �ҽ�";
		
		topping.add("��¥���� ġ��");
	}
	
	public void cut() {
		//��ī�� ���ڴ� �׸𳪰� �߶���Ѵ�. �׷��� ������ cut �κ��� �������̵����ش�.
		System.out.println(getName() +  "�� �ڸ� ��� �׸𳪰� �ڸ�����...");
	}
}
