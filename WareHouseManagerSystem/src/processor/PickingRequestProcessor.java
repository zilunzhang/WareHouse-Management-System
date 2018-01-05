package processor;

import java.util.LinkedList;

import picking.PickingRequest;

public class PickingRequestProcessor implements Processor<PickingRequest> {

  private LinkedList<PickingRequest> pickingRequestProcessor = new LinkedList<PickingRequest>();

  @Override
  /**
   * add picking request to processor.
   */
  public void add(PickingRequest pickingRequest) {
    pickingRequestProcessor.addLast(pickingRequest);
  }

  /**
   * Add picking request to processor.
   * 
   * @param pickingRequest picking request we are going to put into this processor.
   */
  public void addFirst(PickingRequest pickingRequest) {
    pickingRequestProcessor.addFirst(pickingRequest);
  }

  @Override
  public PickingRequest remove() {
    return pickingRequestProcessor.poll();

  }

  @Override
  public PickingRequest outPut() {
    return this.remove();
  }

  public int getSizeCounter() {
    return pickingRequestProcessor.size();
  }

  public LinkedList<PickingRequest> getContent() {
    return pickingRequestProcessor;
  }
}
