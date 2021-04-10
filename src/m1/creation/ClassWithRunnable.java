package m1.creation;

/******* Example showcasing setUncaughtExceptionHandler  *******/
public class ClassWithRunnable {

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread( () -> {
				System.out.println("We are now in thread "+ Thread.currentThread().getName());
				System.out.println("Current priority of thread is "+ Thread.currentThread().getPriority());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("Sorry!");
		});
		
		thread.setName("Misbehaving Thread");
		thread.setPriority(Thread.MAX_PRIORITY);

		
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread arg0, Throwable arg1) {
				System.out.println("A thread has give error" + arg0.getName() + " the error is "+ arg1.getMessage());
			}
		});
		
		System.out.println("Currently executing "+Thread.currentThread().getName()+" before starting thread");
		thread.start();
		System.out.println("Currently executing "+Thread.currentThread().getName()+" after starting thread");
		Thread.sleep(1000);
		System.out.println("main exit");
		
		
	}

}
