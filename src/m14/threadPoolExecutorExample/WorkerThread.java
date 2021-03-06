package m14.threadPoolExecutorExample;

public class WorkerThread extends Thread{

  private String command;

  public WorkerThread(String command) {
    this.command = command;
  }

  @Override
  public void run(){
    System.out.println(Thread.currentThread().getName() + " Start . command ="+ command);
    processCommand();
    System.out.println(Thread.currentThread().getName() + " End");
  }

  private void processCommand(){
    try{
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
