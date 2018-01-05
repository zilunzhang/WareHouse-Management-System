package processor;

import java.util.ArrayList;
import java.util.LinkedList;
import ordering.Order;

public class OrderProcessor implements Processor<Order> {

  private LinkedList<Order> orderProcessor;

  /** The constructor of this class. */
  public OrderProcessor() {
    orderProcessor = new LinkedList<Order>();
  }

  /**
   * Add element
   * 
   * @param order the element need to be added.
   * 
   */
  public void add(Order order) {
    orderProcessor.add(order);
  }

  /**
   * Remove the first Order in the orderProcessor and return it. If size of the Processor is empty,
   * return null.
   * 
   * @return order the first order in the OrderProcessor (actually, it is a Queue which is
   *         implemented by linked list)
   */
  public Order remove() {
    return orderProcessor.poll();
  }

  /**
   * Usually call this method when Processor is full. 
   * Return all elements in this Processor in the form of ArrayList.
   * 
   */
  public ArrayList<Order> outPut() {
    ArrayList<Order> outputList = new ArrayList<Order>();
    int index = 4;
    while (index != 0) {
      outputList.add(this.remove());
      index -= 1;
    }
    return outputList;
  }

  public int getSizeCounter() {
    return orderProcessor.size();
  }


  public LinkedList<Order> getOrderProcessor() {
    return orderProcessor;
  }

  public boolean isGreaterThanThree() {
    return getSizeCounter() > 3;
  }
}
