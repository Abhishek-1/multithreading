package synchronization;

public class BlockSynchronization {

	private static class InventoryCounter {
		private int items = 0;
		Object lock = new Object();

		public void increment() {
			synchronized (this.lock) {
				items++;
			}
		}

		public void decrement() {
			synchronized (this.lock) {
				items--;
			}
		}

		public int getItems() {
			synchronized (this.lock) {
				return items;
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
			for (int i = 0; i < 10000; i++) {
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
			for (int i = 0; i < 10000; i++) {
				inventoryCounter.decrement();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		InventoryCounter inventoryCounter = new InventoryCounter();
		IncrementOperator incrementOperator = new IncrementOperator(inventoryCounter);
		DecrementOperator decrementOperator = new DecrementOperator(inventoryCounter);
		incrementOperator.start();
		decrementOperator.start();
		Thread.sleep(1000);
		System.out.println(inventoryCounter.getItems());
	}

}
