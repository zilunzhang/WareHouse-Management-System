package sequencing;

import java.util.ArrayList;
import java.util.LinkedList;
import picking.PickingRequest;
import util.Fascia;
import util.WareHouse;

public class MarshallingArea {

  private LinkedList<ArrayList<Fascia>> receivedFascias;
  private int receivedProductSizeCount;

  private LinkedList<PickingRequest> receivedRequest;
  private int receivedRequestSizeCount;

  private LinkedList<Pallet> readyPallet;
  private int readyPalletSizeCount;

  private LinkedList<PickingRequest> readyRequest;
  private int readyRequestSizeCount;

  private WareHouse wareHouseBelonging;

  /**
   * The Marshalling Area.
   * 
   * @param wareHouse the warehouse where the marshalling area belongs.
   */
  public MarshallingArea(WareHouse wareHouse) {
    this.setWareHouseBelonging(wareHouse);
    receivedFascias = new LinkedList<ArrayList<Fascia>>();
    receivedRequest = new LinkedList<PickingRequest>();
    readyPallet = new LinkedList<Pallet>();
    readyRequest = new LinkedList<PickingRequest>();
  }

  /**
   * The product group would be delivered from a picker into the queue ReceivedProduct.
   * 
   * @param products the product group that is put into the ReceivedProduct by a picker.
   */
  public void addReceivedProduct(ArrayList<Fascia> products) {
    ArrayList<Fascia> receivedFascia = new ArrayList<Fascia>();
    for (Fascia fascia : products) {
      receivedFascia.add(fascia);
    }
    this.receivedFascias.add(receivedFascia);
    setReceivedProductSizeCount(getReceivedFasciaSetSizeCount() + 1);
  }

  /**
   * Get the product group comes in the first (queue is a first-in- first-out structure).
   * 
   * @return the first product group in the queue.
   */
  public ArrayList<Fascia> getFirstReceivedProduct() {
    setReceivedProductSizeCount(getReceivedFasciaSetSizeCount() - 1);
    return receivedFascias.poll();
  }

  /**
   * The product group would be delivered from a picker into the queue ReceivedProduct.
   * 
   * @param request the request that is put into the ReceivedRequest by a picker.
   */
  public void addReceivedRequest(PickingRequest request) {
    this.receivedRequest.add(request);
    this.receivedRequestSizeCount++;
  }

  /**
   * Get the request comes in the first (queue is a first-in- first-out structure).
   * 
   * @return the first request in the queue.
   */
  public PickingRequest getFirstReceivedRequest() {
    // System.out.println("The receivedRequest number is: " + this.getReceivedRequestSizeCount());
    this.receivedRequestSizeCount -= 1;
    // System.out.println("The received Request number is: " + this.getReceivedRequestSizeCount());
    return receivedRequest.poll();
  }

  /**
   * The correct pallet would be added to the ReadyPallet.
   * 
   * @param pallet the input pallet.
   */
  public void addReadyPallet(Pallet pallet) {
    (this.readyPallet).add(pallet);
    readyPalletSizeCount += 1;
  }

  /**
   * Get the pallet from the ReadyPallet.
   * 
   * @return the first pallet from the ReadyPallet.
   */
  public Pallet getReadyPallet() {
    readyPalletSizeCount -= 1;
    return (this.readyPallet).poll();
  }

  /**
   * Add the request corresponds to the checked pallet to the ReadyRequest.
   * 
   * @param request the request corresponds to the checked pallet.
   */
  public void addReadyRequest(PickingRequest request) {
    (this.readyRequest).add(request);
    readyRequestSizeCount += 1;
  }

  /**
   * Get the request from the ReadyRequest.
   * 
   * @return the first request from the ReadyRequest.
   */
  public PickingRequest getReadyRequest() {
    readyRequestSizeCount -= 1;
    return (this.readyRequest).remove();
  }

  public int getReadyPalletSizeCount() {
    return this.readyPalletSizeCount;
  }

  public int getReadyRequestSizeCount() {
    return readyRequestSizeCount;
  }

  public int getReceivedFasciaSetSizeCount() {
    return receivedProductSizeCount;
  }

  public void setReceivedProductSizeCount(int receivedProductSizeCount) {
    this.receivedProductSizeCount = receivedProductSizeCount;
  }

  public int getReceivedRequestSizeCount() {
    return receivedRequestSizeCount;
  }

  public WareHouse getWareHouseBelonging() {
    return wareHouseBelonging;
  }

  public void setWareHouseBelonging(WareHouse wareHouseBelonging) {
    this.wareHouseBelonging = wareHouseBelonging;
  }

  public Pallet getReadyPalletNotRemove() {
    return readyPallet.get(0);
  }

  public PickingRequest getReadyRequestNotRemove() {
    return readyRequest.get(0);
  }
}
