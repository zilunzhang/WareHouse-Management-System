package picking;

import exception.SkuNotExistException;
import java.util.ArrayList;

import util.Location;
import util.WareHouse;

public class WarehousePicking {

  /**
   * Based on the Integer SKUs in List 'skus', return a List of locations, where each location is a
   * an Location object containing 5 pieces of information: the zone character (in the range
   * ['A'..'B']), the aisle number (an integer in the range [0..1]), the rack number (an integer in
   * the range ([0..2]), and the level on the rack (an integer in the range [0..3]), and the SKU
   * number.
   * 
   * @param skus the list of SKUs to retrieve.
   * @return the List of locations.
   */
  public static ArrayList<Location> optimize(ArrayList<String> skus, WareHouse wareHouse) {

    ArrayList<Location> tempLocations = new ArrayList<Location>();
    for (String sku : skus) {
      try {
        tempLocations.add(wareHouse.getWareHouseManager().getLocationFromSku(sku));
      } catch (SkuNotExistException e1) {
        e1.printStackTrace();
      }
    }

    ArrayList<Location> returnLocations = new ArrayList<Location>();

    for (Location location : tempLocations) {
      returnLocations.add(location);
    }
    return returnLocations;
  }
}
