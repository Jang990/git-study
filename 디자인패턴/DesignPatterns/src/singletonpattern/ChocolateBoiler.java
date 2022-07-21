package singletonpattern;

public class ChocolateBoiler {
	//214p 연습문제 풀이
	private boolean empty;
	private boolean boiled;
	public static ChocolateBoiler uniqueChocolateBoiler;
	private ChocolateBoiler() {
		empty = true;
		boiled = false;
	}
	
	public ChocolateBoiler getInstance() {
		if(uniqueChocolateBoiler == null) 
			uniqueChocolateBoiler = new ChocolateBoiler();
		
		return uniqueChocolateBoiler;
	}
}
