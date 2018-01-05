package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.SkuNotExistException;
import manager.ReplenisherManager;
import manager.WareHouseManager;
import util.Fascia;
import util.Location;
import util.WareHouse;
import worker.Replenisher;

public class WareHouseManagerTest {
  private WareHouse wareHouse;
  private WareHouseManager wareHouseManager;
  private Fascia fasc1;
  private Location loc1;
  private ReplenisherManager replenisherManager;
  private ArrayList<Replenisher> replenisherList;
  

  @Before
  public void setUp() throws Exception {
    this.wareHouse =
        new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
    wareHouseManager = new WareHouseManager(wareHouse);
    
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
  public void testgetLocationFromSku() {
    try {
      assertEquals(
          "A021", wareHouseManager.getLocationFromSku("10").getZone() + wareHouseManager.getLocationFromSku("10").getAisle() + wareHouseManager.getLocationFromSku("10").getRack() + wareHouseManager.getLocationFromSku("10").getLevel());
    } catch (SkuNotExistException e) {
      e.printStackTrace();
    }

  }
  @Test
  public void testgetSkuFromLocation(){
    assertEquals(
        "3", wareHouseManager.getSkuFromLocation(loc1));
  }
  @Test
  public void testgetSkuFromInfo(){
    try {
      assertEquals(
          "3", wareHouseManager.getSkuFromInfo("White", "SE", true));
    } catch (SkuNotExistException e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void testgetInfoFromSku(){
    assertEquals( "Color is: White, Model is: SE, is Front: true", wareHouseManager.getInfoFromSku("3"));
  }
  
  @Test
  public void testgetFasciaFromLocation(){
    assertEquals( "3", wareHouseManager.getFasciaFromLocation(loc1).getSku());
  }
  @Test
  public void testgetConsolehandler(){
    System.out.println(WareHouseManager.getConsolehandler().getClass().toString());
    assertEquals( "class java.util.logging.ConsoleHandler", WareHouseManager.getConsolehandler().getClass().toString());
  }
  @Test
  public void testgetFileHandler(){
    System.out.println(WareHouseManager.getFileHandler().getClass().toString());
    assertEquals( "class java.util.logging.FileHandler", WareHouseManager.getFileHandler().getClass().toString());
  } 
}
