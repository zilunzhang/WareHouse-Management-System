package processor;

import java.util.ArrayList;

import ordering.Order;
import picking.PickingRequest;

public class FinishRequestProcessor implements Processor<PickingRequest> {

  private ArrayList<PickingRequest> finishedRequestProcessor;

  private ArrayList<ArrayList<String>> contents;

  public FinishRequestProcessor() {
    finishedRequestProcessor = new ArrayList<PickingRequest>();
    contents = new ArrayList<ArrayList<String>>();
  }

  @Override
  public void add(PickingRequest pickingRequest) {

    (this.finishedRequestProcessor).add(pickingRequest);
    this.addContents(pickingRequest);
  }

  @Override
  public PickingRequest remove() {
    return null;
  }

  @Override
  public Object outPut() {

    ArrayList<PickingRequest> tempPickingRequest = new ArrayList<PickingRequest>();

    for (PickingRequest pickingRequest : finishedRequestProcessor) {
      tempPickingRequest.add(pickingRequest);
    }
    return tempPickingRequest;
  }

  /** Add contents from pickingRequest into this finish request processor. */
  public void addContents(PickingRequest pickingRequest) {
    for (Order order : pickingRequest.getOrderGroup()) {
      ArrayList<String> orderInfo = new ArrayList<String>();
      String model = order.getModelInfo();
      String colour = order.getColourInfo();
      String frontSku = order.getFrontSku().toString();
      String backSku = order.getBackSku().toString();
      orderInfo.add(model);
      orderInfo.add(colour);
      orderInfo.add(frontSku);
      orderInfo.add(backSku);

      contents.add(orderInfo);
    }
  }

  public ArrayList<ArrayList<String>> getContents() {
    return contents;
  }

  public ArrayList<PickingRequest> getFinishedRequestProcessor() {
    return finishedRequestProcessor;
  }

  public int getSizeCounter() {
    return finishedRequestProcessor.size();
  }
}
