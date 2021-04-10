package m14.threadPoolExecutorExample;

import java.util.concurrent.ThreadPoolExecutor;

public class MonitorThread extends Thread{

  private ThreadPoolExecutor executor;
  private int seconds;
  private Boolean run=true;

  public MonitorThread(ThreadPoolExecutor executor, int delay){
    this.executor = executor;
    this.seconds = delay;
  }

  @Override
  public void run() {
    while(run){
      System.out.println(
          String.format("[monitor] [%d/%d] Active: %d, Completed %d, Task %d, isShutdown: %s, isTerminated: %s",
              executor.getPoolSize(),
              executor.getCorePoolSize(),
              executor.getActiveCount(),
              executor.getCompletedTaskCount(),
              executor.getTaskCount(),
              executor.isShutdown(),
              executor.isTerminated())
      );
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void shutdown(){
    this.run=false;
  }
}
