package synchronization;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantTry {

  /***** A thread tries to access the lock held by another thread using tryLock *****/

  public static class MyLock extends ReentrantLock{
    public String owner(){
      Thread t = this.getOwner();
      if(t != null){
        return t.getName();
      }
      return "none";
    }

    public Thread getThread(){
      return this.getOwner();
    }
  }

  public static void main(String[] args){

    MyLock lock = new MyLock();

    Thread t1 = new Thread(() -> {
      lock.lock();
      try{
        while(true){

        }
      } finally {
        lock.unlock();
      }

    });



    Thread t2 = new Thread(() -> {
     if(lock.tryLock()){
       System.out.println("lock taken by thread 2");
     } else {
       System.out.println("Unable to acquire lock, lock is held by "+ lock.getThread().getName());
     }
    });

    t1.start();
    t2.start();

  }

}
