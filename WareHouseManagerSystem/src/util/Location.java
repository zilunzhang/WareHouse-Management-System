package util;

import exception.SkuNotExistException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import manager.FasciaManager;
import manager.ReplenisherManager;


public class Location extends Observable {

  private ArrayList<Observer> observers;
  private String owner = null;
  private String zone;
  private String aisle;
  private String rack;
  private String level;
  private FasciaManager fasciaManager;
  private ReplenisherManager replenisherManager;


  /** Given a fasica, construct a location that stores the fascia. */
  public Location(Fascia fascia) throws SkuNotExistException {
    fasciaManager = new FasciaManager(replenisherManager, fascia, this);
    observers = new ArrayList<Observer>();
  }

  public FasciaManager getFasciaManager() {
    return fasciaManager;
  }

  public void setFasciaManager(FasciaManager fasciaManager) {
    this.fasciaManager = fasciaManager;
  }

  /** Return true if two location has the same zone, aisle, rack, level. */
  public boolean equals(Location other) {
    if (other.getAisle().equals(aisle) && other.getZone().equals(zone)
        && other.getRack().equals(rack) && other.getLevel().equals(level)) {
      return true;
    }
    return false;
  }

  /** Given zone, aisle, rack, level, set the corresponding information of the location. */
  public void setInfo(String zone, String aisle, String rack, String level) {
    this.zone = zone;
    this.aisle = aisle;
    this.rack = rack;
    this.level = level;
  }

  public String getZone() {
    return zone;
  }

  public void setZone(String zone) {
    this.zone = zone;
  }

  public String getAisle() {
    return this.aisle;
  }

  public void setAisle(String aisle) {
    this.aisle = aisle;
  }

  public String getRack() {
    return rack;
  }

  public void setRack(String rack) {
    this.rack = rack;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public String toString() {
    return "The location is: " + zone + aisle + rack + level + ". " + fasciaManager.toString();
  }

  /** Set the replenisherManager of this locaion. */
  public void setReplenisherManager(ReplenisherManager replenisherManager) {
    this.replenisherManager = replenisherManager;
    fasciaManager.setRepenisherManager(replenisherManager);
  }

  public ReplenisherManager getReplenisherManager() {
    return replenisherManager;
  }
}

