package m8.reentrant;

public class ReentrantTry {

  public static void main(String[] args){

    MyLock lock = new MyLock();

    Thread t1 = new Thread(() -> {
      lock.lock();
      try{
        int i = 1;
        while(i<10){
          System.out.println("Current i"+ i);
          Thread.sleep(1000);
          i++;
        }
      } catch (InterruptedException e) {
        System.out.println("Catch");
        e.printStackTrace();
      } finally {
        System.out.println("Releasing lock");
        lock.unlock();
      }

    });
    Thread t2 = new Thread(() -> {
      while(true){
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if(lock.tryLock()){
          System.out.println("lock taken by thread 2");
        } else {
          System.out.println("Unable to acquire lock, lock is held by "+ lock.getThread().getName());
        }
      }

    });

    t1.start();
    t2.start();

  }

}
