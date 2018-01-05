package sequencing;

import java.util.ArrayList;

import util.Fascia;

public class Pallet {

  private ArrayList<Fascia> frontFascia;
  private ArrayList<Fascia> backFascia;

  /** Construct an empty pallet. */
  public Pallet() {
    this.frontFascia = new ArrayList<Fascia>();
    this.backFascia = new ArrayList<Fascia>();
  }

  /** Add the fascia into corresponding pallet. */
  public void addProduct(Fascia fascia) {
    if (fascia.isFront()) {
      (this.frontFascia).add(fascia);
    } else {
      (this.backFascia).add(fascia);
    }
  }

  public ArrayList<Fascia> getFrontFascia() {
    return this.frontFascia;
  }

  public ArrayList<Fascia> getBackFascia() {
    return this.backFascia;
  }

  /** Return an arrayList of string representation of all the fascia in the pallet. 
   * front goes first, back goes next.  */
  public ArrayList<String> toStringList() {
    ArrayList<String> temp = new ArrayList<String>();
    for (Fascia fascia : frontFascia) {
      temp.add(fascia.toString());
    }
    for (Fascia fascia : backFascia) {
      temp.add(fascia.toString());
    }
    return temp;
  }
}
