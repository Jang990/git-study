package singletonpattern;

public class Singleton {
	private static Singleton uniqueInstance; //단 한가지의 객체만을 저장할 변수
	
	private Singleton() { } //private로 설정
	
	
	/*
	 * 하지만 여기서 스레드를 사용한다고 생각해보자
	 * 나타날 수 있는 최악의 상황이다.
	 * th1									th2
	 * th2 생성
	 * Singleton.getInstance();				th2 성공적인 생성!
	 * 										Singleton.getInstance();
	 * if(uniqueInstance == null)
	 *										if(uniqueInstance == null)
	 * 										uniqueInstance = new Singleton();
	 * uniqueInstance = new Singleton();
	 * 
	 * 
	 * 이 문제를 해결하기 위해서는 동기화 synchronized 가 있어야한다.
	 */
	public static synchronized Singleton getInstance() {
		if(uniqueInstance == null) 
			uniqueInstance = new Singleton();
		
		return uniqueInstance;
	}
	
	/*
	 * 좋다! 우리는 동기화 문제를 해결했다.
	 * 하지만 getInstance() 메소드에 동기화를 시키려면 적지않은 대가를 치뤄야한다.(느려진다...)
	 *  다른 방법을 생각해보자
	 * 1. getInstance()의 속도가 그리 중요하지 않다면 그냥 두자.
	 * 
	 * 2. 인스턴스를 필요할 때 생성하지 말고 처음부터 만들어 버리자.
	 * private static Singleton uniqueInstance = new Singleton(); 이렇게 만들자는 것이다.
	 * getInstance는 return 만하도록
	 * 
	 * 3. DCL(Double-Checking Locking)을 써서 getInstane()에서 동기화되는 부분을 줄입니다.
	 * //private static Singleton uniqueInstance; 를
	 * private volatile static Singleton uniqueInstance; //로 바꾸고 
	 * //getInstance 메소드 내용을
	 * if(uniqueInstance == null) {
	 * 		synchronized (Singleton.class) { //이렇게 하면 처음에만 동기화가 된다.
	 * 			if(uniqueInstance == null) {
	 * 				uniqueInstance = new Singleton();
	 * 			}
	 * 		}
	 * }
	 * return uniqueInstance;
	 * //이렇게 바꾼다.
	 */
	
	//기타 메소드
}
