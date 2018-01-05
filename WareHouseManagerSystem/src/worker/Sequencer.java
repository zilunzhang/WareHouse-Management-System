package worker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.logging.Level;

import manager.WareHouseManager;
import picking.PickingRequest;
import processor.PickingRequestProcessor;
import sequencing.MarshallingArea;
import sequencing.Pallet;
import util.Fascia;
import util.WareHouse;

public class Sequencer extends Worker {

  private LinkedList<ArrayList<Fascia>> unorderedFasciasSet;
  private Pallet pallet;
  public LinkedList<PickingRequest> requestList;
  private boolean visuallyCorrect = true;

  /**
   * A sequencer.
   * 
   * @param name the name of the sequencer.
   * @param wareHouse which warehouse the sequencer belonged.
   */
  public Sequencer(String name, WareHouse wareHouse) {
    super(name, wareHouse);
    unorderedFasciasSet = new LinkedList<ArrayList<Fascia>>();
    pallet = new Pallet();
    requestList = new LinkedList<PickingRequest>();
  }

  /**
   * Check if the picker's work.
   * 
   */
  public boolean checkPickerWork() {
    boolean flag = true;
    if (unorderedFasciasSet.get(0).size() == requestList.get(0).getAllSku().size()) {
      for (Fascia fascia : unorderedFasciasSet.get(0)) {
        if (requestList.get(0).getAllSku().contains(fascia) == false) {
          flag = false;
        }
      }
    }
    return flag;
  }

  /**
   * Check if the pallet has the correct contents.
   * 
   * @param pallet
   * @return if the pallet has the correct contents.
   */
  public boolean check(Pallet pallet) {

    boolean flag = true;

    if (pallet.getFrontFascia().size() != pallet.getBackFascia().size()) {
      // if during sequencing, the size of front or back is not equal. From 0 to small one's size
      // -1.
      for (int i = 0; i < Math.min(pallet.getFrontFascia().size(), pallet.getBackFascia().size())
          - 1; i++) {
        // if info not equal
        if (!pallet.getFrontFascia().get(i).getColour()
            .equals(pallet.getBackFascia().get(i).getColour())
            | !pallet.getFrontFascia().get(i).getModel()
                .equals(pallet.getBackFascia().get(i).getModel())) {
          // not possible 2 front or 2 back, since the possibility is eliminated from sequencing.
          flag = false;
        }
      }
    } else {
      for (int i = 0; i < pallet.getBackFascia().size() - 1; i++) {
        // if info not equal
        if (!pallet.getFrontFascia().get(i).getColour()
            .equals(pallet.getBackFascia().get(i).getColour())
            | !pallet.getFrontFascia().get(i).getModel()
                .equals(pallet.getBackFascia().get(i).getModel())) {
          // not possible 2 front or 2 back, since the possibility is eliminated from sequencing.
          flag = false;
        }
      }
    }
    return flag;
  }


  /**
   * Sequence the order in the unorderedProduct and put them into the Pallet.
   */
  public void sequencing() {
    ArrayList<String> allFrontSku = this.getRequest().getAllFrontSku();
    ArrayList<String> allBackSku = this.getRequest().getAllBackSku();

    for (String sku : allFrontSku) {
      Fascia fascia = this.getFirstEncounter(sku);
      this.pallet.addProduct(fascia);
    }

    for (String sku : allBackSku) {
      Fascia fascia = this.getFirstEncounter(sku);
      this.pallet.addProduct(fascia);
    }

    WareHouseManager.getLogger().log(Level.INFO, "OUTPUT: Sequencer " + super.getName()
        + " successfully sequenced Fascias, " + " conforming by barcode.");


    System.out.println(pallet.getFrontFascia() + " " + pallet.getBackFascia());
  }

  /**
   * Sequencing the fascia given the sku.
   * 
   * @param sku sku
   */
  public void sequencing(String sku) {
    Fascia fascia = this.getFirstEncounter(sku);
    this.pallet.addProduct(fascia);
    if (this.barcodeReader(fascia, sku)) {
      WareHouseManager.getLogger().log(Level.INFO, "OUTPUT: Sequencer " + super.getName()
          + " successfully sequenced Fascias with SKU" + fascia.getSku());
    } else {
      WareHouseManager.getLogger().log(Level.INFO, "OUTPUT: Sequencer " + super.getName()
          + " failed to sequence Fascias with SKU" + fascia.getSku());
    }
  }

  /**
   * Check if there is any mistake in the pallet.
   * 
   * @return if the pallet is ordered correctly.
   */
  @Override
  public boolean barcodeReader() {
    if ((this.pallet.getFrontFascia()).size() < 4) {
      return false;
    } else if ((this.pallet.getBackFascia()).size() < 4) {
      return false;
    }
    int index = 0;
    while (index < 4) {
      String trialFrontSku = ((this.pallet.getFrontFascia()).get(index)).getSku();
      String correctFrontSku = (this.getRequest().getAllFrontSku()).get(index);
      String trialBackSku = ((this.pallet.getBackFascia()).get(index)).getSku();
      String correctBackSku = (this.getRequest().getAllBackSku()).get(index);
      if (!(trialFrontSku == correctFrontSku) || !(trialBackSku == correctBackSku)) {
        return false;
      }
      index++;
    }
    return true;
  }

  /**
   * Check if the fascia has the given sku.
   * 
   * @param fascia
   * @param sku
   * @return if the fascia has the given sku.
   */
  public boolean barcodeReader(Fascia fascia, String sku) {
    if (!(fascia.getSku().equals(sku))) {
      return false;
    }
    return true;
  }

  /**
   * In case the pallet is in wrong order, the whole set would be discard. The pickingRequest would
   * be thrown back to the first in the PickingRequestProcessor queue to be process later.
   * 
   * @param the pickingRequestProcessor that we want to add the request back.
   */
  public void discard(PickingRequestProcessor pickingRequestProcessor) {
    pickingRequestProcessor.addFirst((this.requestList).poll());
    this.unorderedFasciasSet = new LinkedList<ArrayList<Fascia>>();
    this.pallet = new Pallet();
    this.setState(true);
  }

  /**
   * To transfer the well-ordered pallet into the MarshalligArea, where has a queue that processes
   * the well-ordered pallet.
   * 
   * @param marshalling area that we want the pallet to be handled.
   */
  public void toMarshallingArea(MarshallingArea ma) {
    ma.addReadyPallet(this.pallet);
    ma.addReadyRequest(this.requestList.poll());
    this.pallet = new Pallet();
    // this.requestList = new LinkedList<PickingRequest>();
  }

  /**
   * A helper method, in case of repeated orders, we want to get the first product that has the
   * characteristics decribed in the pickingRequest.
   * 
   * @param model of the target product.
   * @param colour of the target product.
   * @return the first product that has the above characteristics.
   */
  public Fascia getFirstEncounter(String sku) {
    int index = 0;
    for (Fascia fascia : this.unorderedFasciasSet.get(0)) {
      if ((fascia.getSku()).equals(sku)) {
        index = (this.unorderedFasciasSet.get(0)).indexOf(fascia);
        break;
      }
    }
    return (this.unorderedFasciasSet.get(0)).remove(index);
  }

  /**
   * Return the group of products that have not been sequenced yet.
   * 
   * @return the group of the unordered product.
   */
  public ArrayList<Fascia> getUnorderedProduct() {
    return unorderedFasciasSet.get(0);
  }

  /**
   * Get the unorderedProduct from the MarshallingArea.
   * 
   * @param marshalling are where the sequencer can get the unordered product.
   */
  public void setUnorderedProduct(MarshallingArea ma) {
    this.unorderedFasciasSet.add(ma.getFirstReceivedProduct());
  }

  /**
   * Get the pallet produced after sequenced.
   * 
   * @return the pallet that held by the sequencer.
   */
  public Pallet getPallet() {
    return this.pallet;
  }

  /**
   * Get the request held by this sequencer.
   * 
   * @return the request that held by the sequencer.
   */
  public PickingRequest getRequest() {
    return (this.requestList).get(0);
  }

  /**
   * Received the picking request from the marshalling area.
   * 
   * @param the marshalling area that the sequencer gets the request from.
   */
  public void addRequest(MarshallingArea ma) {
    requestList.add(ma.getFirstReceivedRequest());
  }

  public void addUnorderedFascias(MarshallingArea ma) {
    unorderedFasciasSet.add(ma.getFirstReceivedProduct());
  }

  @Override
  public void update(Observable arg0, Object arg1) {}

  /**
   * return true if is visuallyCorrect.
   */
  public boolean isVisuallyCorrect() {
    return visuallyCorrect;
  }

  /**
   * 
   * @return true if is damaged.
   */
  public boolean isDamage() {
    for (Fascia fascia : unorderedFasciasSet.get(0)) {
      if (fascia.isDamage() == true) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param visuallyCorrect the visuallyCorrect to set.
   */
  public void setVisuallyCorrect(boolean visuallyCorrect) {
    this.visuallyCorrect = visuallyCorrect;
  }
}
