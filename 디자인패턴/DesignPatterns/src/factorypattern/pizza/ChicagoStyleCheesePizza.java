package factorypattern.pizza;

public class ChicagoStyleCheesePizza extends Pizza {
	public ChicagoStyleCheesePizza() {
		name = "시카고 스타일 딥 디시 치즈 피자";
		dough = "아주 두꺼운 도우";
		sauce = "플럼 토마토 소스";
		
		topping.add("모짜렐라 치즈");
	}
	
	public void cut() {
		//시카고 피자는 네모나게 잘라야한다. 그렇게 때문에 cut 부분을 오버라이딩해준다.
		System.out.println(getName() +  "에 자를 대고 네모나게 자르는중...");
	}
}
