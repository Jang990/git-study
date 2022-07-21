package adapterpattern;

public class LawOfDemeter {
	//챕터 7-2에 나온 디미터 법칙 설명을 위한 클래스
	
	class User { 
		private String email; 
		private String name; 
		private Address address;
		public Address getAddress() {
			return address;
		} 
		
		/* public boolean isSeoulUser() { return address.isSeoulRegion(); } */		
	}
	
	class Address { 
		private String region; 
		private String details;
		public String getRegion() {
			return region;
		}
		
		/* public boolean isSeoulRegion() { return "서울".equals(region); } */

	}

	/*
	 * 위와 같은 두 개의 클래스가 있다고 가정하자.
	 */
	
	
	class NotificationService { 
		public void sendMessageForSeoulUser(final User user) 
		{ 
			if("서울".equals(user.getAddress().getRegion())) 
				System.out.println("서울에 살고 있습니다."); 
		}
	}
	/*
	 * 여기서 
	 * "서울".equals(user.getAddress().getRegion()) 이 코드는 데미테르 법칙(디미터 법칙)을 준수하지 않은 코드이다.
	 * 메소드를 호출해서 리턴 받은 객체의 메소드를 호출하는 것은 바람직하지 않다.
	 * 주석으로 처리되어 있는 것들을 푸는 것이 바람직하다.
	 * 
	 * 내 생각에는 다른 객체와 깊게 연결되지 않도록 파고 파고 들어가지 말라는 뜻인듯
	 */

	public static void main(String[] args) {
		
	}
	
	
}

