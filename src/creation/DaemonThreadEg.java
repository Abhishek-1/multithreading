package creation;

/******* Daemon thread does not prevent main thread from exiting *******/
public class DaemonThreadEg {

  public static void main(String[] args){
    Thread t = new Thread(() -> {
      System.out.println("Thread created");
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    t.setDaemon(true);
    t.start();
    System.out.println("Main exiting");
  }

}
