package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import manager.ReplenisherManager;
import util.Location;
import util.WareHouse;
import worker.Replenisher;


public class WareHouseTest {
  
  private WareHouse wareHouse;
  

  @Before
  public void setUp() throws Exception {
    this.wareHouse = 
        new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");

  }

  @After
  public void tearDown() throws Exception {}
  
  @Test
  public void testgetLocationListSize() {
    assertEquals(48 ,this.wareHouse.getLocationList().size());
  }
  
  @Test
  public void testgetFirstLocationList() {
    Location firstLoc = this.wareHouse.getLocationList().get(0);
    assertEquals("A" , firstLoc.getZone());
    assertEquals("0" , firstLoc.getAisle());
    assertEquals("0" , firstLoc.getRack());
    assertEquals("0" , firstLoc.getLevel());
    assertEquals(20 , firstLoc.getFasciaManager().getAmount());
  }
  
  @Test
  public void testgetReserveRoom() {
    assertEquals("A", this.wareHouse.getReserveRoom().get(0).getZone());
  }
  
  @Test
  public void testReplenisherManager() {
    ArrayList<Replenisher> replenisherList = new ArrayList<>();
    replenisherList.add(new Replenisher("Tom", this.wareHouse));
    replenisherList.add(new Replenisher("Ana", this.wareHouse));
    ReplenisherManager epm = new ReplenisherManager(replenisherList);
    this.wareHouse.setReplenisherManager(epm);
    assertEquals(epm, wareHouse.getReplenisherManager());
    this.wareHouse.setAllReplenisherManager(epm);
    for (int i = 0; i < 48; i++) {
      assertEquals(epm, wareHouse.getLocationList().get(i).getReplenisherManager());
    }
    
  }

  

}
