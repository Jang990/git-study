package decoratorpattern;

public abstract class Beverage {
	public String descroption = "제목 없음";
	
	public String getDescroption() {
		return descroption;
	}

	public abstract double cost();

	
	
	//137p 연필을 깎으며 문제를 위한 코드
	//사이즈에 따라 첨가물 가격을 다르게 만들 것이다. Mocha에만 적용해놓음
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
