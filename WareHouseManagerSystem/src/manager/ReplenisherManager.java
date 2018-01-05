package manager;

import java.util.ArrayList;

import worker.Replenisher;

public class ReplenisherManager {

  private ArrayList<Replenisher> replenisherList;

  public ReplenisherManager(ArrayList<Replenisher> replenisherList) {
    this.replenisherList = replenisherList;
  }

  public ArrayList<Replenisher> getReplenisherList() {
    return replenisherList;
  }

  /**
   * Construct a ReplenisherManager given a replenisherList.
   * 
   * @param replenisherList the list of replenisher this manager is going to manage.
   * @return Replenisher if there is free replenisher.
   */
  public Replenisher getFreeReplenisher(ArrayList<Replenisher> replenisherList) {
    for (Replenisher replenisher : replenisherList) {
      if (replenisher.isFree()) {
        return replenisher;
      }
    }
    return null;
  }


  /**
   * Return a replenisher if there is replenisher with such name, otherwise return null.
   * 
   * @param replenisherList the list of replenisher this manager is going to manage.
   * @param name the name of replenisher you want to find.
   * @return a replenisher if there is a replenisher with such name, otherwise return null.
   */
  public Replenisher findReplenisherThroughName(ArrayList<Replenisher> replenisherList,
      String name) {
    for (Replenisher replenisher : replenisherList) {
      if (replenisher.getName().equals(name)) {
        return replenisher;
      }
    }
    return null;
  }

  public Replenisher getReplenisherForManager() {
    return getFreeReplenisher(replenisherList);
  }

  public void setReplenisherList(ArrayList<Replenisher> replenisherList) {
    this.replenisherList = replenisherList;
  }
}
