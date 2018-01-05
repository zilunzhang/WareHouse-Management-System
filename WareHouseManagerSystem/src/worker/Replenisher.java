package worker;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import manager.WareHouseManager;
import util.Location;
import util.WareHouse;

public class Replenisher extends Worker implements Observer {

  public Replenisher(String name, WareHouse wareHouse) {
    super(name, wareHouse);

  }

  /*
   * do replenishing
   */
  public void update(Location location) {


    super.setState(false);
    System.out.println(" here did replenishing." + location);
    System.out.println(
        "Before replenishing, stock has " + location.getFasciaManager().getAmount() + " Fascias.");
    location.getFasciaManager().replenishing();
    WareHouseManager.getLogger().log(Level.INFO, "OUTPUT: " + this.getName() + " is replenishing.");
    // System.out.println(super.getName() + " did replenishing.");
    System.out.println("Now, stock has " + location.getFasciaManager().getAmount() + " Fascias.");

  }



}
