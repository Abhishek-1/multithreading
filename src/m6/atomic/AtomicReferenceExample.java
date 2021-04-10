package m6.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {

  public static void main(String[] args){
    String oldName = "old Name";
    String newName = "new Name";
    AtomicReference<String> atomicReference = new AtomicReference<>(oldName);
    atomicReference.set("Some Value Changed");
    if(atomicReference.compareAndSet(oldName, newName)){
      System.out.println("New Value is "+ newName);
    } else {
      System.out.println("Nothing Changed!!");
    }
  }

}
