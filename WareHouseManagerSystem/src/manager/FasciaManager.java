package manager;

import java.util.Observable;
import util.Fascia;
import util.Location;
import worker.Replenisher;

public class FasciaManager extends Observable {
  private Fascia fascia;
  private int amount = 30;
  private Location location;
  private ReplenisherManager replenisherManager;

  /**
   * Construct the fascia manager given the information below.
   * 
   * @param replenisherManager replenisherManager of this fasciaManager.
   * @param fascia the fascia you are going manager.
   * @param location the location of this fascia.
   * 
   */
  public FasciaManager(ReplenisherManager replenisherManager, Fascia fascia, Location location) {
    this.fascia = fascia;
    this.location = location;
    this.replenisherManager = replenisherManager;
  }

  @Override
  public void notifyObservers() {

    Replenisher freeReplenisher = replenisherManager.getReplenisherForManager();

    if (freeReplenisher != null) {
      freeReplenisher.update(location);
    }
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  /** Return the fascia managed by this FasciaManager. */
  public Fascia getFascia() {
    amount = amount - 1;
    if (amount < 6) {
      setChanged();
      notifyObservers();
    }
    return fascia;
  }

  public void setFascia(Fascia fascia) {
    this.fascia = fascia;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Location getLocation() {
    return location;
  }

  public Fascia getFasciaWithoutUpdate() {
    return fascia;
  }

  public String getFasciaSku() {
    return fascia.getSku();
  }

  public void setFasciaSku(String sku) {
    fascia.setSku(sku);
  }

  public String getFasciaColour() {
    return fascia.getColour();
  }

  public String getFasciaModel() {
    return fascia.getModel();
  }

  public boolean getFrontInfo() {
    return fascia.isFront();
  }

  public void replenishing() {
    amount += 25;
  }

  /** Return the String representation of this fascia. */
  public String toString() {
    return "I am fascia manager, " + "my fascia's colour is: " + fascia.getColour()
        + ", and my fascia's model is: " + fascia.getModel()
        + ", and the amount of this fascia is: " + amount + ".";
  }

  public void setRepenisherManager(ReplenisherManager replenisherManager) {
    this.replenisherManager = replenisherManager;
  }
}
