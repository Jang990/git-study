package singletonpattern;

public class Singleton {
	private static Singleton uniqueInstance; //�� �Ѱ����� ��ü���� ������ ����
	
	private Singleton() { } //private�� ����
	
	
	/*
	 * ������ ���⼭ �����带 ����Ѵٰ� �����غ���
	 * ��Ÿ�� �� �ִ� �־��� ��Ȳ�̴�.
	 * th1									th2
	 * th2 ����
	 * Singleton.getInstance();				th2 �������� ����!
	 * 										Singleton.getInstance();
	 * if(uniqueInstance == null)
	 *										if(uniqueInstance == null)
	 * 										uniqueInstance = new Singleton();
	 * uniqueInstance = new Singleton();
	 * 
	 * 
	 * �� ������ �ذ��ϱ� ���ؼ��� ����ȭ synchronized �� �־���Ѵ�.
	 */
	public static synchronized Singleton getInstance() {
		if(uniqueInstance == null) 
			uniqueInstance = new Singleton();
		
		return uniqueInstance;
	}
	
	/*
	 * ����! �츮�� ����ȭ ������ �ذ��ߴ�.
	 * ������ getInstance() �޼ҵ忡 ����ȭ�� ��Ű���� �������� �밡�� ġ����Ѵ�.(��������...)
	 *  �ٸ� ����� �����غ���
	 * 1. getInstance()�� �ӵ��� �׸� �߿����� �ʴٸ� �׳� ����.
	 * 
	 * 2. �ν��Ͻ��� �ʿ��� �� �������� ���� ó������ ����� ������.
	 * private static Singleton uniqueInstance = new Singleton(); �̷��� �����ڴ� ���̴�.
	 * getInstance�� return ���ϵ���
	 * 
	 * 3. DCL(Double-Checking Locking)�� �Ἥ getInstane()���� ����ȭ�Ǵ� �κ��� ���Դϴ�.
	 * //private static Singleton uniqueInstance; ��
	 * private volatile static Singleton uniqueInstance; //�� �ٲٰ� 
	 * //getInstance �޼ҵ� ������
	 * if(uniqueInstance == null) {
	 * 		synchronized (Singleton.class) { //�̷��� �ϸ� ó������ ����ȭ�� �ȴ�.
	 * 			if(uniqueInstance == null) {
	 * 				uniqueInstance = new Singleton();
	 * 			}
	 * 		}
	 * }
	 * return uniqueInstance;
	 * //�̷��� �ٲ۴�.
	 */
	
	//��Ÿ �޼ҵ�
}
