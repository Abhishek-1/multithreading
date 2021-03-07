package join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactorialCalculation {

	public static void main(String[] args) throws InterruptedException {

		List<Long> inputNumbers = Arrays.asList(3543500000L, 2324L, 4656L, 23L, 5556L, 3435L, 0L);
		List<FactorialThread> threads = new ArrayList<>();
		for(Long inp: inputNumbers) {
			threads.add(new FactorialThread(inp));
		}
		
		for(Thread t: threads) {
			t.setDaemon(true);
			t.start();
		}
		
		for(Thread t: threads) {
			System.out.println("Calling thread -> "+ Thread.currentThread().getName());
			t.join(2000);
		}
		
		for(int i=0; i<inputNumbers.size(); i++) {
			FactorialThread factorialThread = threads.get(i);
			if(factorialThread.getIsFinished()) {
				System.out.println("Factorial of "+inputNumbers.get(i)+" is "+factorialThread.getResult());
			}
			else {
				System.out.println("Factorial of "+inputNumbers.get(i)+" is Processing");
			}
		}
	}

	public static class FactorialThread extends Thread {

		private long inputNumber;
		private BigInteger result = BigInteger.ZERO;
		private boolean isFinished = false;

		public FactorialThread(long input) {
			this.inputNumber = input;
		}

		@Override
		public void run() {
			System.out.println("Reference thread -> "+ Thread.currentThread().getName() + " Started");
			this.result = factorial(inputNumber);
			this.isFinished = true;
			System.out.println("Reference thread -> "+ Thread.currentThread().getName() + " Ended");
		}

		public BigInteger factorial(long inputNumber) {
			BigInteger temp = BigInteger.ONE;

			for (long i = inputNumber; i > 0; i--) {
				temp = temp.multiply(new BigInteger(Long.toString(i)));
			}
			
			return temp;
		}
		
		public BigInteger getResult() {
			return result;
		}
		
		public boolean getIsFinished() {
			return isFinished;
		}
	}

}
