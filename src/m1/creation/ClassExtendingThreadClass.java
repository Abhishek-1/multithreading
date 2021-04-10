package m1.creation;

public class ClassExtendingThreadClass {
	
	public static void main(String[] args) {
		
		Thread t = new NewThread();
		t.setName("Worker Thread");
		t.start();
		
	}
	
	private static class NewThread extends Thread {
		
		@Override
		public void run() {
			System.out.println("The current running thread is "+ this.getName());
			
		}
	}

}
