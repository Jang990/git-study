package strategypattern;

import strategypattern.character.King;
import strategypattern.character.Knight;
import strategypattern.character.Troll;

public class Main {
	
	/*
	 * 챕터 1
	 * 
	 *  스트래티지 패턴(Strategy Pattern)
	 * 알고리즘군을 정의하고 각각을 캡슐화하여 교환해서 사용할 수 있도록 만든다.
	 * 스트래티지 패턴을 활용하면 알고리즘을 사용하는 클라이언트와는 독립적으로 알고리즘을 변경할 수 있다.
	 * 
	 *  디자인의 원칙(= 객체지향 원칙)
	 * 1. 애플리케이션에서 달라지는 부분을 찾아내고, 달라지지 않는 부분으로 부터 분리시킨다.
	 * (자주 달라지거나 바뀌는 부분을 인터페이스를 이용해서 구현. - 책에서 display() 부분은 한번 세팅하면 또 바뀌지 않는다.)
	 * 2. 구현이 아닌 인터페이스에 맞춰서 프로그래밍 한다.
	 * (실제 실행시에 쓰이는 객체가 코드에 의해 고정되지 않도록 어떤 상위 형식에 맞춰 프로그래밍함으로써 다형성 활용.)
	 * 3. 상속(extends)보다는 구성(implements)을 활용
	 * (유연성을 크게 향상시킬 수 있음)
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
