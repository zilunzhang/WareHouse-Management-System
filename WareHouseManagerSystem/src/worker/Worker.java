package worker;

import java.util.ArrayList;
import java.util.Observable;

import util.Fascia;
import util.WareHouse;


public abstract class Worker {

  private String name;
  private WareHouse wareHouseBelonging;

  private boolean isFree;

  public Worker(String name, WareHouse wareHouse) {
    this.name = name;
    this.wareHouseBelonging = wareHouse;
    this.isFree = true;
  }


  public boolean scanBarCode(String skuNeedToCheck, String originalSku) {
    return skuNeedToCheck.equals(originalSku);
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the state
   */
  public boolean isFree() {
    return isFree;
  }

  /**
   * @param state the state to set
   */
  public void setState(boolean state) {
    this.isFree = state;
  }

  // public boolean checkIsDamage(Fascia fascia) {
  // return (fascia.isDamage() == true);
  // }

  public boolean isDisorder(ArrayList<String> skus, ArrayList<Fascia> Fasicas) {
    for (int i = 0; i < skus.size(); i++) {
      if (skus.get(i) != Fasicas.get(i).getSku()) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return the wareHouse
   */
  public WareHouse getWareHouseBelonging() {
    return wareHouseBelonging;
  }

  /**
   * @param wareHouse the wareHouse to set
   */
  public void setWareHouseBelonging(WareHouse wareHouse) {
    this.wareHouseBelonging = wareHouse;
  }

  public void update(Observable o, Object arg) {

  }

  public boolean equals(Worker w) {
    if (this.name.equals(w.getName())) {
      return true;
    }
    return false;
  }


  public boolean barcodeReader() {
    return false;
  }

}
