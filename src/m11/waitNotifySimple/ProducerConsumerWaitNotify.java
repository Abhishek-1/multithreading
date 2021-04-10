package m11.waitNotifySimple;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWaitNotify {

  public static ReentrantLock lock = new ReentrantLock();
  public static Condition condition = lock.newCondition();
  public static int num = 0;

  private static class Producer extends Thread{
    public Queue<Integer> myQueue;

    public Producer(Queue inp){
      this.myQueue = inp;
    }

    public void run(){
      synchronized (lock) {
        while (true) {
          try {
            while (myQueue.size() > 5) {
              lock.wait();
            }
            Thread.sleep(1000);
            num += 1;
            System.out.println("Producer produced " + num);
            myQueue.offer(num);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private static class Consumer extends Thread{
    public Queue<Integer> myQueue;

    public Consumer(Queue inp){
      this.myQueue = inp;
    }

    public void run(){
      synchronized (lock) {
        try {
          while (!myQueue.isEmpty()) {
            Thread.sleep(1000);
            System.out.println("Consumer consumed " + myQueue.poll());
          }
          lock.notify();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String[] args){

    Queue<Integer> queue = new LinkedList<>();
    Producer producer = new Producer(queue);
    Consumer consumer = new Consumer(queue);
    producer.start();
    consumer.start();
  }

}
