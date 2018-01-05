package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.SkuNotExistException;
import manager.FasciaManager;
import manager.ReplenisherManager;
import util.Fascia;
import util.Location;
import worker.Replenisher;



public class LocationTest {
  
  private Fascia fasc1;
  private Location loc1;
  private ReplenisherManager replenisherManager;
  private ArrayList<Replenisher> replenisherList;
  @Before
  public void setUp() throws Exception {
    this.fasc1 = new Fascia("White", "SE", "3", true);
    this.loc1 = new Location(fasc1);
    this.loc1.setInfo("A", "0", "0", "2");
    this.replenisherList = new ArrayList<Replenisher>();
    loc1.setReplenisherManager(replenisherManager);
    this.replenisherManager = new ReplenisherManager(replenisherList);
  }
  

  @After
  public void tearDown() throws Exception {}

  
  @Test
  public void testGetAmount() {
    assertEquals(30, loc1.getFasciaManager().getAmount());

  }


  @Test
  public void testGetSku() {
    assertEquals(fasc1.getSku(), "3");
  }


  @Test
  public void testGetZone() {
    assertEquals(loc1.getZone(), "A");
  }

  @Test
  public void testGetAisle() {
    assertEquals(loc1.getAisle(), "0");
  }
  
  @Test
  public void testGetRack() {
    assertEquals(loc1.getRack(), "0");

  }

  @Test
  public void testGetLevel() {
    assertEquals(loc1.getLevel(), "2");
  }


  @Test
  public void testGetFasciaManager() {
    assertTrue(loc1.getFasciaManager() instanceof FasciaManager);
  }

  @Test
  public void testSetZone() {
    loc1.setZone("B");
    assertEquals(loc1.getZone(), "B");
  }

  @Test
  public void testSetAmount() {
    assertEquals(30, loc1.getFasciaManager().getAmount());
    loc1.getFasciaManager().setAmount(1);
    assertEquals(1, loc1.getFasciaManager().getAmount());
  }


  @Test
  public void testSetAisle() {
    String one = new String("1");
    loc1.setAisle(one);
    assertEquals(loc1.getAisle(), one);
  }

  @Test
  public void testSetRack() {
    String one = new String("1");
    loc1.setRack(one);
    assertEquals(loc1.getRack(), one);
  }


  @Test
  public void testSetLevel() {
    String one = new String("1");
    loc1.setLevel(one);
    assertEquals(loc1.getLevel(), "1");
  }

  @Test
  public void testSetFasciaManager() {
	  
	FasciaManager fasciaManager = new FasciaManager(replenisherManager, fasc1,loc1);
    loc1.setFasciaManager(fasciaManager);
    assertTrue(loc1.getFasciaManager() instanceof FasciaManager);
  }


  @Test
  public void testToString() {
    assertEquals(loc1.toString(), "The location is: A002. I am fascia manager, "
        + "my fascia's colour is: White, and my fascia's model is: SE, "
        + "and the amount of this fascia is: 30.");
  }

  @Test
  public void testSetInfo() {
    loc1.setInfo("A", "2", "2", "2");
    assertEquals(loc1.getZone(), "A");
    assertEquals(loc1.getAisle(), "2");
    assertEquals(loc1.getRack(), "2");
    assertEquals(loc1.getLevel(), "2");
  }
  
  @Test
  public void testGetRepenisherManager() {
	  loc1.setReplenisherManager(replenisherManager);
    assertEquals(replenisherManager,loc1.getReplenisherManager() );
  }
  
  @Test
  public void testEqual() throws SkuNotExistException {
	  Location loc2 = new Location(fasc1);
	  loc2.setInfo("A", "0", "0", "2");
	    assertEquals(true, loc1.equals(loc2) );
	  Location loc3 = new Location(fasc1);
	  loc3.setInfo("A", "0", "0", "4");
    assertEquals(false, loc1.equals(loc3) );
  }
  
  
}
