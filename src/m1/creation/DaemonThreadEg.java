package m1.creation;

/******* Example Thread with daemon property *******/
public class DaemonThreadEg {

  public static void main(String[] args){
    Thread t = new Thread(() -> {
      System.out.println("Thread created");
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Child thread exiting");
    });

    /** Daemon property will insure that thread will run in background **/
    t.setDaemon(true);
    t.start();
    System.out.println("Main exiting");
  }

}
