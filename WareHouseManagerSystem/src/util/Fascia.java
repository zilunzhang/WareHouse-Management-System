package util;

public class Fascia {

  private boolean isDamage = false;
  private String model;
  private String colour;
  private String sku;
  private boolean isFront;
  
  
  /** Given colour, model, sku, front/back information.
   * Construct the Fascia.
   * */
  public Fascia(String colour, String model, String sku, boolean isFront) {
    this.model = model;
    this.colour = colour;
    this.sku = sku;
    this.isFront = isFront;
  }

  public void setFront(boolean isFront) {
    this.isFront = isFront;
  }

  public boolean isFront() {
    return this.isFront;
  }


  public boolean isDamage() {
    return isDamage;
  }

  public void setDamage(boolean isDamage) {
    this.isDamage = isDamage;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getColour() {
    return colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String toString() {
    return this.colour + " " + this.model + " " + this.sku;
  }

  /** Return true if two they have the same sku. */
  public boolean equals(Fascia fa) {
    if (this.sku.equals(fa.getSku())) {
      return true;
    } else {
      return false;
    }
  }
}
