package m8.reentrant;

import m8.reentrant.MyLock;

public class ReentrantLockUse {

  public static class InventoryCounter{
    private int items = 0;
    /****** MyLock Class extends Reentrant Lock *****/
    MyLock lock = new MyLock();

    public void increment() {
      System.out.println("Increment trying to get lock");
        lock.lock();

        try{
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          items++;
        } finally {
          System.out.println("\nIn increment Number of threads waiting for lock " + lock.getQueueLength());
          System.out.println("Current Thread "+ Thread.currentThread().getName());
          System.out.println("Owner of thread " + lock.owner());
          lock.unlock();
        }


    }

    public void decrement() {
      System.out.println("Decrement trying to get lock");
      lock.lock();
      try {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        items--;
      }finally {
        System.out.println("\nIn decrement Number of threads waiting for lock " + lock.getQueueLength());
        System.out.println("Current Thread "+ Thread.currentThread().getName());
        System.out.println("Owner of thread " + lock.owner());
        lock.unlock();
      }

    }

    public int getItems() {
      lock.lock();
      try {
        return items;
      } finally {
        lock.unlock();
      }
    }
  }

  public static class IncrementOperator extends Thread {
    private InventoryCounter inventoryCounter;

    public IncrementOperator(InventoryCounter inventoryCounter) {
      this.inventoryCounter = inventoryCounter;
    }

    @Override
    public void run() {
      for(int i=0; i<10000; i++) {
        inventoryCounter.increment();
      }
    }
  }

  public static class DecrementOperator extends Thread {
    private InventoryCounter inventoryCounter;

    public DecrementOperator(InventoryCounter inventoryCounter) {
      this.inventoryCounter = inventoryCounter;
    }

    @Override
    public void run() {
      for(int i=0; i<10000; i++) {
        inventoryCounter.decrement();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException{
    InventoryCounter inventoryCounter = new InventoryCounter();
    IncrementOperator incrementOperator = new IncrementOperator(inventoryCounter);
    DecrementOperator decrementOperator = new DecrementOperator(inventoryCounter);
    incrementOperator.start();
    decrementOperator.start();
    Thread.sleep(1000);
    System.out.println(inventoryCounter.getItems());
  }

}
