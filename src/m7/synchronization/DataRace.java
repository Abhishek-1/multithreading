package m7.synchronization;

public class DataRace {
	
	public static void main(String[] args) {
		SharedClass sharedClass = new SharedClass();
		
		Thread thread1 = new Thread(() -> {
			for(int i=0; i<Integer.MAX_VALUE; i++) {
				sharedClass.increment();
			}
		});
		
		Thread thread2 = new Thread(() -> {
			for(int i=0; i<Integer.MAX_VALUE; i++) {
				sharedClass.checkDataRace();
			}
		});
		thread1.start();
		thread2.start();
		
	}
	
	private static class SharedClass {
		private volatile int x=0;
		private volatile int y=0;
		
		public void increment(){
			x++;
			y++;
		}
		
		public void checkDataRace() {
			if(y>x) {
				System.out.println("Data Race happened "+ y);
			}
		}
		
	}

}
