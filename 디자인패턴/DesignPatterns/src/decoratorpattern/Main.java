package decoratorpattern;

import decoratorpattern.coffeetype.Espresso;
import decoratorpattern.coffeetype.HouseBlend;
import decoratorpattern.deco.Mocha;
import decoratorpattern.deco.Whip;

public class Main {

	/*
	 *  챕터 3
	 *  
	 *  데코레이터 패턴(Decorator Pattern)
	 * 객체에 추가적인 요건을 동적으로 첨가한다. 
	 * 데코레이터는 서브클래스를 만드는 것을 통해서 기능을 유연하게 확장할 수 있는 방법을 제공한다. 
	 * 
	 *  디자인 원칙
	 * 5. 클래스는 확장에 대해서는 열려 있어야 하지만 코드 변경에 대해서는 닫혀 있어야 한다.(=OCP(Open-Closed Principle))
	 * (기존 코드는 건드리지 않은 채로 확장을 통해서 새로운 행동을 간단하게 추가할 수 있도록 하는게 목표이다.)
	 * (무조건 OCP를 적용하는 것은 불필요하게 복잡하고 이해하기 힘든 코드를 만들게 되는 부작용이 있을 수 있으니 주의해야 한다.)
	 * 
	 * 만약 HouseBlend가 특별 할인을 한다고 가정해보자
	 * HouseBlend가 20% 할인을 하는데
	 * HouseBlend에 모카와 휘핑크림을 추가한다면 
	 * 모카와 휘핑크림도 포함된 가격이 할인되어야 할 것이다
	 * (houseblend.cost() + .10 + .20) * 0.2; //이런식으로
	 * 하지만 코드상에서 
	 * houseblend = new HouseBlend(); //이렇게 처음에 만들고
	 * houseblend = new Mochao(houseblend); // 다시 이런식으로 감싼다
	 * 이러면 어떤게 HouseBlend인지 판단할 수 있을까? 
	 * 이렇기 떄문에 구상 구성요소의 형식을 알아내서 코드에 데코레이터 패턴을 적용하면 코드가 제대로 작동하지 않을 수 있다.
	 * 구상 구성 요소를 바탕으로 돌아가는 코드를 만들어야 한다면 
	 * 애플리케이션 디자인 자체 및 데코레이터 패턴을 사용하는 것에 대해서 다시 한 번 생각해 볼 필요가 있다.
	 * 
	 * 데코레이터는 감싸고 있는 객체에 행동을 추가히기 위한 용도로 만들어진 것이다.
	 * 
	 * 데코레이터 패턴을 활용해서 디자인을 하다보면 잡다한 클래스들이 너무 많아진다. 코드를 보는 개발자 입장에서는 괴롭다.
	 * 하지만 일단 파악하고 나면 클래스를 데코레이터로 감싸서 원하는 행동을 구현할 수 있다.
	 * 
	 * 142p 데코레이터 패턴의 단점(?) 주의점
	 */
	
	public static void main(String[] args) {
		Beverage espresso = new Espresso(); //여기가 중요하다. Beverage로 변수타입을 지정하고 Espresso로 객체를 만든다
		espresso = new Whip(espresso);
		espresso = new Mocha(espresso);
		System.out.println("주문명 : " + espresso.getDescroption());
		System.out.println("가격 : " + espresso.cost() + "$");
		System.out.println();
		
		Beverage houseblend = new HouseBlend();
		houseblend = new Whip(houseblend);
		houseblend = new Whip(houseblend);
		System.out.println("주문명 : " + houseblend.getDescroption());
		System.out.println("가격 : " + houseblend.cost() + "$");
		System.out.println();
		
		//137 연습문제를 위한 코드
		Beverage houseblend2 = new HouseBlend();
		houseblend2.setSize(Beverage.GRANDE);
		houseblend2 = new Mocha(houseblend2);
		houseblend2 = new Mocha(houseblend2);
		System.out.println("주문명 : " + houseblend2.getDescroption());
		System.out.println("가격 : " + houseblend2.cost() + "$");
	}
}
