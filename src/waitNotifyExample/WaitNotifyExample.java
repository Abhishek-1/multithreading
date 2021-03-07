package waitNotifyExample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringJoiner;

public class WaitNotifyExample {

  private static final int N=10;
  private static final String INPUT_FILE = "./src/waitNotifyExample/out/matrices";
  private static final String OUTPUT_FILE = "./src/waitNotifyExample/out/matricesOut";

  public static void main(String[] args) throws IOException {

    ThreadSafeQueue queue = new ThreadSafeQueue();
    File inputFile = new File(INPUT_FILE);
    File outputFile = new File(OUTPUT_FILE);

    MatricesReaderProducer matricesReaderProducer = new MatricesReaderProducer(new FileReader(inputFile), queue);
    MatricesMultiplyConsumer matricesMultiplyConsumer = new MatricesMultiplyConsumer(new FileWriter(outputFile), queue);

    matricesReaderProducer.start();
    matricesMultiplyConsumer.start();

  }

  private static class MatricesMultiplyConsumer extends Thread {
    private ThreadSafeQueue queue;
    private FileWriter fileWriter;

    public MatricesMultiplyConsumer(FileWriter fileWriter, ThreadSafeQueue queue){
      this.queue = queue;
      this.fileWriter = fileWriter;
    }

    @Override
    public void run(){
      while(true){
        MatricesPair matricesPair = queue.remove();
        if(matricesPair == null){
          System.out.println("No more matrices to read from queue, Consumer is exiting");
          break;
        }
        float[][] result = multiplyMatrices(matricesPair.matrix1, matricesPair.matrix2);
        try {
          saveMatrixToFile(fileWriter, result);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      try{
        fileWriter.flush();
        fileWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    private float[][] multiplyMatrices(float[][] m1, float[][] m2){
      float[][] result = new float[N][N];
      for(int r=0; r<N; r++){
        for (int c = 0; c<N; c++){
          for(int k=0; k<N; k++){
            result[r][c] = m1[r][k] * m2[k][c];
          }
        }
      }
      return result;
    }

    private void saveMatrixToFile(FileWriter fileWriter, float[][] result) throws IOException {
      for (int r=0; r<N; r++){
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (int c=0; c<N; c++){
          stringJoiner.add(String.format("%.2f", result[r][c]));
        }
        fileWriter.write(stringJoiner.toString());
        fileWriter.write('\n');
      }
      fileWriter.write('\n');
    }

  }

  private static class MatricesReaderProducer extends Thread{
    private Scanner scanner;
    private ThreadSafeQueue queue;

    public MatricesReaderProducer(FileReader reader, ThreadSafeQueue queue){
        this.scanner = new Scanner(reader);
        this.queue = queue;
    }

    @Override
    public void run(){
      while(true){
        float[][] matrix1 = readMatrices();
        float[][] matrix2 = readMatrices();
        if(matrix1 == null || matrix2 == null){
          queue.terminate();
          System.out.println("No more Matrices to read");
          return;
        }
        MatricesPair matricesPair = new MatricesPair();
        matricesPair.matrix1 = matrix1;
        matricesPair.matrix2 = matrix2;
        queue.add(matricesPair);
      }
    }
    private float[][] readMatrices(){
      float[][] matrix = new float[N][N];
      for(int r=0; r<N; r++){
        if(!scanner.hasNext()){
          return null;
        }
        String[] line = scanner.nextLine().split(",");
        for(int c=0; c<N; c++){
          matrix[r][c] = Float.valueOf(line[c]);
        }
      }
      scanner.nextLine();
      return matrix;
    }
  }

  private static class ThreadSafeQueue {

    private Queue<MatricesPair> queue = new LinkedList<>();
    private boolean isEmpty = false;
    private boolean isTerminated = false;

    public synchronized void add(MatricesPair matricesPair){
      queue.add(matricesPair);
      isEmpty = false;
      notify();
    }

    public synchronized MatricesPair remove(){
        while(isEmpty && !isTerminated) {
          try {
            wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        if(isEmpty && isTerminated) return null;

        if(queue.size() == 1) isEmpty = true;

        System.out.println("Queue size "+ queue.size());

       return queue.remove();

    }

    public synchronized void terminate(){
      isTerminated = true;
      notifyAll();
    }

  }

  private static class MatricesPair {

    public float[][] matrix1;
    public float[][] matrix2;

  }

}
