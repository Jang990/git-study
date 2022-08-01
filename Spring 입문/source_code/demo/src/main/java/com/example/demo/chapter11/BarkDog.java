package com.example.demo.chapter11;

import java.util.List;

public class BarkDog {
	private List<String> barkSound;
	MyDog dog;
	
	public List<String> getBarkSound() {
		return barkSound;
	}
	public void setBarkSound(List<String> barkSound) {
		this.barkSound = barkSound;
	}
	public MyDog getDog() {
		return dog;
	}
	public void setDog(MyDog dog) {
		this.dog = dog;
	}

}
