package iteratorpattern;

public class Main {
	
	/*
	 *  챕터 9 - 1
	 *  
	 * 메뉴 리스트를 배열을 사용하는 식당과
	 * 메뉴 리스트를 ArrayList를 사용하는 식당이 있다.
	 * 두 식당이 합병되어 두 메뉴 또한 통합할 예정이다. 그러나 두 식당에서 코드를 바꾸려는 생각이 없다
	 * 이러면 만약 모든 메뉴를 불러오려면
	 * for(i < 0; i < array.length; i++) {...array[i]...}이 for문과
	 * for(i < 0; i < arrayList.size(); i++) {...arrayList.get(i)...} 이 for문이 같이 사용되어야 할 것이다.
	 * 만약 또 다른 구현법을 사용하는 식당과 합병을 한다면
	 * 3개의 for문을 써야 할 것이다. 상당히 불편할 뿐만 아니라, 코드 관리 및 확장을 하기도 힘들다.
	 * 
	 * Iterator라는 인터페이스를 만들어보자.
	 * 각 코드에 설명 대충 있음
	 * 
	 * 만드는 것을 경험해보고 이제는 자바에서 제공하는 Iterator를 사용해보자
	 * 자바에서 제공하는 인터페이스는 
	 * 만든 인터페이스에 remove() 메소드만 추가 된 것이다.
	 */
	
	public static void main(String[] args) {
		
	}

}
