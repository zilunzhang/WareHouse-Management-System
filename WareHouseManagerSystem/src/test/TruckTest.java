package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import loading.Truck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sequencing.Pallet;

public class TruckTest {

  private Truck truck;
  private Pallet pallet1;
  private Pallet pallet2;

  /** Construct a truck object. */
  @Before
  public void setUp() throws Exception {
    this.pallet1 = new Pallet();
    this.pallet2 = new Pallet();
    this.truck = new Truck();
    this.truck.addPallet(pallet1);
    this.truck.addPallet(pallet2);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testisFull() {
    assertFalse(truck.isFull());
  }

  @Test
  public void testgetLeftLoadedPallet() {
    assertEquals(1, truck.getLeftLoadedPallet().size());
    assertEquals(pallet1, truck.getLeftLoadedPallet().get(0));
  }

  @Test
  public void testgetRightLoadedPallet() {
    assertEquals(1, truck.getRightLoadedPallet().size());
    assertEquals(pallet2, truck.getRightLoadedPallet().get(0));
  }

  @Test
  public void testaddPallet() {
    this.truck.addPallet(new Pallet());
    assertEquals(2, truck.getLeftLoadedPallet().size());
    assertEquals(1, truck.getRightLoadedPallet().size());
    this.truck.addPallet(new Pallet());
    assertEquals(2, truck.getLeftLoadedPallet().size());
    assertEquals(2, truck.getRightLoadedPallet().size());
    assertFalse(truck.isFull());

    for (int i = 1; i < 50; i++) {
      this.truck.addPallet(new Pallet());
    }

    assertTrue(truck.isFull());
    assertEquals(10, truck.getLeftLoadedPallet().size());
    assertEquals(10, truck.getRightLoadedPallet().size());

  }
}
