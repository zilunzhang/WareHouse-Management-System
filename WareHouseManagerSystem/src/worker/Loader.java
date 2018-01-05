package worker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

import loading.Truck;
import picking.PickingRequest;
import processor.FinishRequestProcessor;
import processor.PickingRequestProcessor;
import sequencing.MarshallingArea;
import sequencing.Pallet;
import util.Fascia;
import util.WareHouse;

public class Loader extends Worker {

  private LinkedList<Pallet> palletOnHold;
  private LinkedList<PickingRequest> requestList;
  private boolean visuallyCorrect = true;

  /**
   * A loader.
   * 
   * @param the name of the loader.
   * @param the warehouse that the loader belonged.
   */
  public Loader(String name, WareHouse wareHouse) {
    super(name, wareHouse);
    palletOnHold = new LinkedList<Pallet>();
    requestList = new LinkedList<PickingRequest>();
  }

  /**
   * Check if the pallet received is correct.
   * 
   * @return if the pallet is correct.
   */
  public boolean barcodeReader() {
    if ((this.palletOnHold.get(0).getFrontFascia()).size() < 0) {
      return false;
    } else if ((this.palletOnHold.get(0).getBackFascia()).size() < 0) {
      return false;
    }
    int index = 0;
    while (index < 4) {
      String trialFrontSku = (((this.palletOnHold.get(0)).getFrontFascia()).get(index)).getSku();
      String correctFrontSku = (this.getRequest().getAllFrontSku()).get(index);
      String trialBackSku = (((this.palletOnHold.get(0)).getBackFascia()).get(index)).getSku();
      String correctBackSku = (this.getRequest().getAllBackSku()).get(index);

      if (!(trialFrontSku == correctFrontSku) || !(trialBackSku == correctBackSku)) {
        return false;
      }
      index++;
    }
    return true;
  }

  /**
   * In the case where the pallet is in wrong order, then the whole set would be discarded and the
   * request would be put back to the PickingRequestProcessor.
   * 
   * @param the PickingRequestProcessor that the loader would put the picking request back.
   */
  public void discard(PickingRequestProcessor pickingRequestProcessor) {
    if (!(this.barcodeReader())) {
      pickingRequestProcessor.addFirst(this.requestList.poll());
      this.palletOnHold = new LinkedList<Pallet>();
    }
  }

  /**
   * Load the pallet.
   * 
   * @param truck that loader loads the pallet.
   * @param the destination where the loaded picking request would go.
   */
  public void loading(Truck truck, FinishRequestProcessor frp) {
    truck.addPallet(this.palletOnHold.poll());
    this.palletOnHold = new LinkedList<Pallet>();
    (this.getRequest()).setLoaded(true);
    frp.add(this.requestList.poll());
    this.requestList = new LinkedList<PickingRequest>();
  }

  /**
   * Loader gets the pallet from the marshalling area.
   * 
   * @param the marshalling area where loader gets his pallet.
   */
  public void setPalletOnHold(MarshallingArea ma) {
    System.out.println("Ready Pallet is: " + ma.getReadyPalletSizeCount());

    this.palletOnHold.add(ma.getReadyPallet());
  }

  /**
   * Loader gets the picking request from the marshalling area.
   * 
   * @param the marshalling area where loader gets his picking request.
   */
  public void addRequest(MarshallingArea ma) {
    this.requestList.add(ma.getReadyRequest());
  }

  /**
   * Get the picking request held by this loader.
   * 
   * @return the picking request held by the loader.
   */
  public PickingRequest getRequest() {
    return this.requestList.get(0);
  }

  /**
   * Get the picking request ID.
   * 
   * @return the ID on the picking request held by the loader.
   */
  public int getRequestID() {
    return (this.requestList.get(0)).getId();
  }

  @Override
  public void update(Observable o, Object arg) {}

  /**
   * @return the isVisuallyCorrect
   */
  public boolean isVisuallyCorrect() {
    return visuallyCorrect;
  }

  /**
   * @param isVisuallyCorrect the isVisuallyCorrect to set
   */
  public void setVisuallyCorrect(boolean visuallyCorrect) {
    this.visuallyCorrect = visuallyCorrect;
  }

  public boolean isDamage() {
    ArrayList<Fascia> tempFascias = new ArrayList<Fascia>();
    for (Fascia frontFascia : palletOnHold.get(0).getFrontFascia()) {
      tempFascias.add(frontFascia);
    }
    for (Fascia backFascia : palletOnHold.get(0).getBackFascia()) {
      tempFascias.add(backFascia);
    }


    for (Fascia fascia : tempFascias) {
      if (fascia.isDamage() == false) {
        return false;
      }
    }
    return true;
  }

  public ArrayList<String> getPalletOnhold() {
    return palletOnHold.get(0).toStringList();
  }

}
