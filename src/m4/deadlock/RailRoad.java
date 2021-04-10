package m4.deadlock;

import java.util.Random;

public class RailRoad {

	public static void main(String[] args) {
		Intersection intersection = new Intersection();
		Thread trainA = new Thread(new TrainA(intersection));
		Thread trainB = new Thread(new TrainB(intersection));
		trainA.start();
		trainB.start();

	}

	public static class TrainB implements Runnable {
		private Intersection intersection;
		private Random random = new Random();

		public TrainB(Intersection intersection) {
			this.intersection = intersection;
		}

		@Override
		public void run() {
			while (true) {
				long sleepingTime = random.nextInt(5);
				try {
					Thread.sleep(sleepingTime);
				} catch (InterruptedException ex) {
				}
				intersection.takeRoadB();
			}
		}
	}

	public static class TrainA implements Runnable {
		private Intersection intersection;
		private Random random = new Random();

		public TrainA(Intersection intersection) {
			this.intersection = intersection;
		}

		@Override
		public void run() {
			while (true) {
				long sleepingTime = random.nextInt(5);
				try {
					Thread.sleep(sleepingTime);
				} catch (InterruptedException ex) {
				}
				intersection.takeRoadA();
			}
		}
	}

	public static class Intersection {
		private Object roadA = new Object();
		private Object roadB = new Object();

		public void takeRoadA() {
			synchronized (roadA) {
				System.out.println("Road A locked by " + Thread.currentThread().getName());
				synchronized (roadB) {
					System.out.println("Train is passing through A");
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
			}
		}

		public void takeRoadB() {
			synchronized (roadB) {
				System.out.println("Road B locked by " + Thread.currentThread().getName());
				synchronized (roadA) {
					System.out.println("Train is passing through A");
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}

}
