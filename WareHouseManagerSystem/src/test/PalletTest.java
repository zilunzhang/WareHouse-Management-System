package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sequencing.Pallet;
import util.Fascia;

public class PalletTest {
  
  private Pallet pallet;
  private Fascia fascia1;
  private Fascia fascia2;

  @Before
  public void setUp() throws Exception {
    this.pallet = new Pallet();
    this.fascia1 = new Fascia("White", "SE", "3", true);
    this.fascia2 = new Fascia("White", "SE", "4", false);
    this.pallet.addProduct(this.fascia1);
    this.pallet.addProduct(this.fascia2);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testaddProduct() {
    this.pallet.addProduct(new Fascia("White", "SEL", "6", false));
    assertEquals(1, this.pallet.getFrontFascia().size());
    assertEquals(2, this.pallet.getBackFascia().size());
  }
  
  @Test
  public void testgetFrontFascia() {
    assertTrue(this.pallet.getFrontFascia().get(0) instanceof Fascia);
    assertEquals("3", this.pallet.getFrontFascia().get(0).getSku());
  }
  
  @Test
  public void testgetBackFascia() {
    Fascia f = new Fascia("White", "SE", "4", false);
    assertTrue(this.pallet.getBackFascia().get(0).equals(f));
  }
  
  @Test
  public void testtoStringList() {
    assertEquals(2, this.pallet.toStringList().size());
    assertEquals(this.fascia1.toString(), this.pallet.toStringList().get(0));
    assertEquals(this.fascia2.toString(), this.pallet.toStringList().get(1));
    this.pallet.addProduct(new Fascia("White", "SEL", "6", false));
    assertEquals("White " + "SEL " + "6", this.pallet.toStringList().get(2));
    
  }
  
  
}
