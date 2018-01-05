package picking;

import exception.SkuNotExistException;
import java.util.ArrayList;
import ordering.Order;
import util.WareHouse;

public class PickingRequest {

  /** The unique id of each picking request. */
  private int id;

  /** The next picking request id. */
  private static int totalId = 0;

  /** the order group which contains four orders' information for minivan. */
  private ArrayList<Order> orderGroup;

  private ArrayList<ArrayList<String>> contentVisualInfo;
  private ArrayList<ArrayList<String>> contentSku;
  private boolean isLoaded;
  private WareHouse wareHouseBelonging;


  /**
   * A new picking request.
   * 
   * @param orderGroup the order group which contains four orders for minivan, ie. there are four
   *        elements in orderGroup and each element contains information on SKUs for the front and
   *        back fascia.
   */
  public PickingRequest(ArrayList<Order> orderGroup, WareHouse wareHouse) {
    this.id = totalId;
    this.orderGroup = orderGroup;
    totalId++;
    this.setWareHouseBelonging(wareHouse);
    contentVisualInfo = new ArrayList<ArrayList<String>>();
    this.setContentVisualInfo(this.orderGroup);

    contentSku = new ArrayList<ArrayList<String>>();
  }

  public int getId() {
    return this.id;
  }

  /** Given a list of order group, set the contentVisualInfo of this picking request. */
  public void setContentVisualInfo(ArrayList<Order> orderGroup) {
    for (Order order : orderGroup) {
      String model = "no such model";
      try {
        model = wareHouseBelonging.getWareHouseManager().getModelFromSku(order.getSku(true));
      } catch (SkuNotExistException e1) {
        System.out.println("Sku does not exist, move on!");
      }

      String colour = "no such colour";
      try {
        colour = wareHouseBelonging.getWareHouseManager().getColorFromSku(order.getSku(true));
      } catch (SkuNotExistException exc) {
        System.out.println("Sku does not exist, move on!");
      }

      if (!(model.equals("no such model"))) {
        ArrayList<String> orderVisualInfo = new ArrayList<>();
        orderVisualInfo.add(model);
        orderVisualInfo.add(colour);
        (this.contentVisualInfo).add(orderVisualInfo);
      }
    }
  }

  /** get ContentVisualInfo. */
  public ArrayList<ArrayList<String>> getContentVisualInfo() {
    return this.contentVisualInfo;
  }

  /** set content of sku. */
  public void setContentSku(ArrayList<Order> orderGroup) {
    for (Order order : orderGroup) {
      String frontSku = order.getSku(true);
      String backSku = order.getSku(false);

      ArrayList<String> orderSku = new ArrayList<>();
      orderSku.add(frontSku);
      orderSku.add(backSku);
      (this.contentSku).add(orderSku);
    }
  }

  /** return the ArrayList of front sku of this picking request. */
  public ArrayList<String> getAllFrontSku() {
    ArrayList<String> allFrontSku = new ArrayList<>();
    for (Order order : orderGroup) {
      allFrontSku.add(order.getFrontSku());
    }
    return allFrontSku;
  }

  /** return the ArrayList of back sku of this picking request. */
  public ArrayList<String> getAllBackSku() {
    ArrayList<String> allBackSku = new ArrayList<>();
    for (Order order : orderGroup) {
      allBackSku.add(order.getBackSku());
    }
    return allBackSku;
  }

  /** return the ArrayList of sku of this picking request front comes first back comes next. */
  public ArrayList<String> getAllSku() {
    ArrayList<String> allSkus = new ArrayList<>();

    for (Order order : orderGroup) {
      allSkus.add(order.getFrontSku());
      allSkus.add(order.getBackSku());
    }
    return allSkus;
  }

  public boolean isLoaded() {
    return isLoaded;
  }

  /**
   * @param isLoaded the isLoaded to set.
   */
  public void setLoaded(boolean isLoaded) {
    this.isLoaded = isLoaded;
  }

  public ArrayList<Order> getOrderGroup() {
    return this.orderGroup;
  }

  public String toString() {
    return "This picking request includes " + orderGroup.size() + " Orders.";
  }

  public WareHouse getWareHouseBelonging() {
    return wareHouseBelonging;
  }

  public void setWareHouseBelonging(WareHouse wareHouseBelonging) {
    this.wareHouseBelonging = wareHouseBelonging;
  }

  /** return the ArrayList of the string presentation of the orders in this picking request. */
  public ArrayList<String> toStringList() {
    ArrayList<String> temp = new ArrayList<String>();
    for (Order order : orderGroup) {
      temp.add(order.toString());
    }
    return temp;
  }
}

