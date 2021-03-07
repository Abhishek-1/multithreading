package interrupt;

import java.io.IOException;

public class WaitingForUserInputs {

	public static void main(String [] args) {
        Thread thread = new Thread(new WaitingForUserInput());
        thread.setName("InputWaitingThread");
        thread.start();
        thread.interrupt();
    }
 
    private static class WaitingForUserInput implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                	if(Thread.currentThread().isInterrupted()) {
                		System.out.println("Interrupted the thread");
                		System.exit(0);
                	}
                    char input = (char) System.in.read();
                    if(input == 'q') {
                        return;
                    }
                }
            } catch (IOException e) {
                System.out.println("An exception was caught " + e);
            };
        }
    }

}
