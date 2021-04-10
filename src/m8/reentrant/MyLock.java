package m8.reentrant;
import java.util.concurrent.locks.ReentrantLock;

class MyLock extends ReentrantLock {

  String owner() {
    Thread t = this.getOwner();
    if (t != null) {
      return t.getName();
    } else {
      return "none";
    }
  }

  public Thread getThread(){
    return this.getOwner();
  }
}
