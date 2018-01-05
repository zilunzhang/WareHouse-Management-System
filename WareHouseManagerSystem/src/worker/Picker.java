package worker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

// import exception.FasciaOutOfStockException;
import exception.IncorrectOrderException;
import exception.NotFinishPickingAllEightFascias;
import exception.SkuNotExistException;
import manager.WareHouseManager;
import picking.PickingRequest;
import picking.WarehousePicking;
import sequencing.MarshallingArea;
import util.Fascia;
import util.Location;
import util.WareHouse;

// extend Observer is for phase II.
public class Picker extends Worker implements Observer {

  private LinkedList<Fascia> forklift;
  private LinkedList<PickingRequest> requestList;
  private LinkedList<Location> pickListAfterOptimaztion;



  public Picker(String name, WareHouse wareHouse) {
    super(name, wareHouse);
    forklift = new LinkedList<Fascia>();
    requestList = new LinkedList<PickingRequest>();
    pickListAfterOptimaztion = new LinkedList<Location>();
  }


  public void dealWithPickingRequest(ArrayList<String> listOfSku) {
    System.out.println(super.getName() + " 's input picking request is: " + listOfSku.toString());
    ArrayList<Location> returnList =
        WarehousePicking.optimize(listOfSku, this.getWareHouseBelonging());
    for (Location location : returnList) {
      pickListAfterOptimaztion.add(location);
    }
    ArrayList<String> tempList = new ArrayList<String>();
    for (Location location : pickListAfterOptimaztion) {
      tempList.add(location.getFasciaManager().getFasciaSku());
    }
    System.out.println(super.getName() + " 's picking request is: " + tempList.toString());
  }



  public void picking(String sku) {

    System.out.println(super.getName() + " is picking " + sku);
    Fascia pickingItem = super.getWareHouseBelonging().getWareHouseManager()
        .getFasciaFromLocation(pickListAfterOptimaztion.poll());
    checkIsOptimizedOrder(pickingItem, sku);
    Location location = null;
    try {
      location = super.getWareHouseBelonging().getWareHouseManager().getLocationFromSku(sku);
    } catch (SkuNotExistException e) {
      e.printStackTrace();
    }
    Fascia fascia = location.getFasciaManager().getFascia();
    (this.forklift).add(fascia);
    if (this.scanBarCode(fascia.getSku(), sku)) {
      WareHouseManager.getLogger().log(Level.INFO, "OUTPUT: Picker " + super.getName()
          + " successfully picked Fascia #" + sku + " conforming by barcode.");
      super.setState(true);
    } else {
      WareHouseManager.getLogger().log(Level.SEVERE,
          "OUTPUT: Picker " + super.getName() + " needs to repick the fascia " + sku);
    }
    if (location.getFasciaManager().getAmount() < 6) {
      WareHouseManager.getLogger().log(Level.SEVERE,
          "NOTE: " + location.toString() + " is out of Stock!!!!!!!");
    }
  }

  public void toMarshalling(MarshallingArea ma) throws NotFinishPickingAllEightFascias {

    ArrayList<Fascia> tempArrayList = new ArrayList<Fascia>();

    if (forklift.size() == 8) {
      while (forklift.size() != 0) {
        tempArrayList.add(forklift.poll());
      }

      System.out.println("The size of tempArrayList is: " + tempArrayList.size() + " .");
      ma.addReceivedProduct(tempArrayList);

      System.out.println("Added all Fascias in the forklift to Marshalling Area, "
          + "now the size of forklift is: " + forklift.size() + " .");

      System.out.println("Before add correspoding picking request to Marshalling Area, "
          + "now the size of picking request list is: " + requestList.size() + " .");
      ma.addReceivedRequest(requestList.poll());
      System.out.println("Added correspoding picking request to Marshalling Area, "
          + "now the size of picking request list is: " + requestList.size() + " .");

    } else {
      throw new NotFinishPickingAllEightFascias(
          this.getName() + "has not finished picking 8 yet, please check input order file.");
    }
  }



  public void checkIsOptimizedOrder(Fascia pickingItem, String sku) {
    if (!(pickingItem.getSku().equals(sku))) {
      WareHouseManager.getLogger().log(Level.INFO,
          "NOTE: " + "the sku given by optimazation software is: " + pickingItem.getSku()
              + " and the required sku is: " + sku + ".");
      WareHouseManager.getLogger().log(Level.SEVERE,
          "NOTE: " + "The picking order is not correct!");

      try {
        throw new IncorrectOrderException("The picking order is not correct!");
      } catch (IncorrectOrderException e) {
        e.printStackTrace();
      }
    }
  }

  public int getNumberOfProduct() {
    return forklift.size();
  }

  public LinkedList<Fascia> getForklift() {
    return forklift;
  }


  public PickingRequest getRequest() {
    return requestList.get(0);
  }

  public void addRequestToList(PickingRequest request) {
    requestList.add(request);
  }

  public LinkedList<PickingRequest> getRequestList() {
    return requestList;
  }

  @Override
  public void update(Observable o, Object arg) {

  }
}
