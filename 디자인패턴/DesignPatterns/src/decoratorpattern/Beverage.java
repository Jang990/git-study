package decoratorpattern;

public abstract class Beverage {
	public String descroption = "���� ����";
	
	public String getDescroption() {
		return descroption;
	}

	public abstract double cost();

	
	
	//137p ������ ������ ������ ���� �ڵ�
	//����� ���� ÷���� ������ �ٸ��� ���� ���̴�. Mocha���� �����س���
	public int size;
	
	public static final int TALL = 1;
	public static final int GRANDE = 2;
	public static final int VENTY = 3;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if(size == GRANDE) { this.size = 2; }
		else if(size == VENTY) { this.size = 3; }
		else if(size == TALL) { this.size = 1; }
	}
	
	
	
}
