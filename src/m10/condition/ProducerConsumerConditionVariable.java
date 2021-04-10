package m10.condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerConditionVariable {

  public static ReentrantLock lock = new ReentrantLock();
  public static Condition condition = lock.newCondition();
  public static int num = 0;

  private static class Producer extends Thread{
    public Queue<Integer> myQueue;

    public Producer(Queue inp){
      this.myQueue = inp;
    }

    public void run(){
      while(true){
        lock.lock();
        try{
          while(myQueue.size() > 5 ){
            condition.await();
          }
          Thread.sleep(1000);
          num += 1;
          System.out.println("Producer produced "+ num);
          myQueue.offer(num);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
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
      lock.lock();
      try {
        while (!myQueue.isEmpty()) {
            Thread.sleep(1000);
            System.out.println("Consumer consumed " + myQueue.poll());
        }
        condition.signal();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
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
