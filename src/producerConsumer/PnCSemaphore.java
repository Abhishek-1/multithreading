package producerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class PnCSemaphore {

  public static Semaphore full = new Semaphore(0);
  public static Semaphore empty = new Semaphore(1);
  public static int num = 0;

  private static class Producer extends Thread{
    public Queue<Integer> myQueue;

    public Producer(Queue inp){
      this.myQueue = inp;
    }

    public void run(){
      while(true){
        try {
          empty.acquire();
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("Producer adding");
        num += 1;
        myQueue.offer(num);
        System.out.println(num+ " added");
        full.release();
      }
    }
  }

  private static class Consumer extends Thread{
    public Queue<Integer> myQueue;

    public Consumer(Queue inp){
      this.myQueue = inp;
    }

    public void run(){
      while(true){
        try {
          full.acquire();
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("Consumer consuming");
        System.out.println(myQueue.poll() + " consumed");
        empty.release();
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
