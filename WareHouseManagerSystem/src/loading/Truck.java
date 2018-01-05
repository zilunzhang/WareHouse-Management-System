package loading;

import java.util.Stack;

import sequencing.Pallet;

public class Truck {

  private Stack<Pallet> leftLoadedPallet;

  private Stack<Pallet> rightLoadedPallet;
  private static final int MAX_CAPACITY = 10;
  private boolean isFull;

  /**
   * A truck.
   */
  public Truck() {
    this.leftLoadedPallet = new Stack<Pallet>();
    this.rightLoadedPallet = new Stack<Pallet>();
    this.setFull(false);
  }

  /**
   * Add the pallet to the truck.
   * 
   * @param pallet that is to be loaded.
   */
  public void addPallet(Pallet pallet) {
    if ((this.rightLoadedPallet).size() < MAX_CAPACITY) {
      if ((this.leftLoadedPallet).size() == (this.rightLoadedPallet).size()) {
        (this.leftLoadedPallet).add(pallet);
      } else {
        (this.rightLoadedPallet).add(pallet);
      }
    } else {
      this.setFull(true);
    }
  }

  /**
   * Check if the truck is full.
   * 
   * @return if the truck is full.
   */
  public boolean isFull() {
    return isFull;
  }

  /**
   * Set whether the truck is full.
   * 
   * @param isFull true if the truck is full.
   */
  public void setFull(boolean isFull) {
    this.isFull = isFull;
  }

  public Stack<Pallet> getLeftLoadedPallet() {
    return leftLoadedPallet;
  }

  public Stack<Pallet> getRightLoadedPallet() {
    return rightLoadedPallet;
  }
}
