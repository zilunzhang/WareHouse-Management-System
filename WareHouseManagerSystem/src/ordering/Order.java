package ordering;

import exception.SkuNotExistException;
import util.WareHouse;

public class Order {

  /** The front fascia for the minivan order. */
  private String frontSku;

  /** The back fascia for the minivan order. */
  private String backSku;

  private WareHouse wareHouseBelonging;

  private String colourInfo;
  private String modelInfo;


  /**
   * Given a colour, model and what wareHouse this order belongs to, construct a order.
   * 
   */
  public Order(String colour, String model, WareHouse wareHouse) {

    this.colourInfo = colour;
    this.modelInfo = model;
    this.wareHouseBelonging = wareHouse;

    try {
      this.frontSku = wareHouse.getWareHouseManager().getSkuFromInfo(colourInfo, modelInfo, true);
    } catch (SkuNotExistException e1) {
      e1.printStackTrace();
    }
    try {
      this.backSku = wareHouse.getWareHouseManager().getSkuFromInfo(colourInfo, modelInfo, false);
    } catch (SkuNotExistException exc) {
      exc.printStackTrace();
    }
  }

  /**
   * @param isFront boolean true if you want the front sku.
   * 
   * @return the sku
   */
  public String getSku(boolean isFront) {
    if (isFront) {
      return this.frontSku;
    } else {
      return this.backSku;
    }
  }

  public String getFrontSku() {
    return frontSku;
  }

  public String getBackSku() {
    return backSku;
  }

  public WareHouse getWareHouseBelonging() {
    return wareHouseBelonging;
  }

  public String getColourInfo() {
    return colourInfo;
  }

  public String getModelInfo() {
    return modelInfo;
  }

  public String toString() {
    return "This Order's colour is: " + this.getColourInfo() + ", " + "this order's model is: "
        + this.getModelInfo();
  }
}
