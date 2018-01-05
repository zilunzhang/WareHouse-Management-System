package processor;

/**
 * This class is a processor class to deal with Object such as Order.
 * 
 * @author Zilun Zhang
 *
 */
public interface Processor<E> {

  /**
   * Add element
   * 
   * @param e1 the element need to be added.
   */
  public void add(E e1);

  /**
   * Remove the first element in the Processor and return it. If size of the Processor is empty,
   * return null.
   * 
   * @return E the first element in the Processor (actually, it is a Queue)
   */
  public E remove();

  /**
   * Usually call this method when Processor is full. Return all elements in this Processor in the
   * form of ArrayList.
   * 
   */
  public Object outPut();
}
