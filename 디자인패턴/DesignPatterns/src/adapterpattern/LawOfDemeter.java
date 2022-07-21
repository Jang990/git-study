package adapterpattern;

public class LawOfDemeter {
	//é�� 7-2�� ���� ����� ��Ģ ������ ���� Ŭ����
	
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
		
		/* public boolean isSeoulRegion() { return "����".equals(region); } */

	}

	/*
	 * ���� ���� �� ���� Ŭ������ �ִٰ� ��������.
	 */
	
	
	class NotificationService { 
		public void sendMessageForSeoulUser(final User user) 
		{ 
			if("����".equals(user.getAddress().getRegion())) 
				System.out.println("���￡ ��� �ֽ��ϴ�."); 
		}
	}
	/*
	 * ���⼭ 
	 * "����".equals(user.getAddress().getRegion()) �� �ڵ�� �����׸� ��Ģ(����� ��Ģ)�� �ؼ����� ���� �ڵ��̴�.
	 * �޼ҵ带 ȣ���ؼ� ���� ���� ��ü�� �޼ҵ带 ȣ���ϴ� ���� �ٶ������� �ʴ�.
	 * �ּ����� ó���Ǿ� �ִ� �͵��� Ǫ�� ���� �ٶ����ϴ�.
	 * 
	 * �� �������� �ٸ� ��ü�� ��� ������� �ʵ��� �İ� �İ� ���� ����� ���ε�
	 */

	public static void main(String[] args) {
		
	}
	
	
}

