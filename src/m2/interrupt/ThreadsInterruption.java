package m2.interrupt;

import java.math.BigInteger;

/** Example showing ways to handle thread.interrupt() **/
public class ThreadsInterruption {
	
    public static void main(String [] args) {
        Thread thread = new Thread(new SleepingThread());
        thread.start();
        Thread thread2 = new Thread(new SleepingThreadWithoutTryCatch());
        thread2.start();
        thread2.interrupt();
        thread.interrupt();
    }
 
    private static class SleepingThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000000);
                    System.out.println("Here");
                } catch (InterruptedException e) {
                	System.out.println("Interrupted Thread 1");
                	//Without this return it will not terminate
                	return;
                }
            }
        }
    }

    private static class SleepingThreadWithoutTryCatch implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted Thread 2");
                    return;
                }
            }
        }
    }

}
