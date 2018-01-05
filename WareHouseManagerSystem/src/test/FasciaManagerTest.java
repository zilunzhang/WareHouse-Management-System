package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.SkuNotExistException;
import manager.FasciaManager;
import manager.ReplenisherManager;
import util.Fascia;
import util.Location;
import util.WareHouse;
import worker.Replenisher;

public class FasciaManagerTest {
	  private Fascia fascia;
	  private String model = "SE";
	  private String colour = "Green";
	  private String sku = "10";
	  private boolean isFront = true;
	private ArrayList<Replenisher> replenisherList;
	private ReplenisherManager replenisherManager;
	private WareHouse wareHouse;
	private Replenisher replenisher;
	  private int amount;
	  private Location location;
	  private FasciaManager fasciaManager;
	  @Before
	  public void setUp() throws Exception {
		  this.amount = 30;
		  this.fascia = new Fascia("Green", "SE", "10", true);
		  
		  wareHouse = new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
		  replenisher = new Replenisher("Batman", wareHouse);
		  replenisher.setState(true);
		  replenisherList = new ArrayList<Replenisher>(); 
		  replenisherList.add(replenisher);
		  
		  replenisherManager = new ReplenisherManager(replenisherList);
		  this.fasciaManager = new FasciaManager(replenisherManager, fascia,location);
		  this.location = new Location(fascia);
		  location.setFasciaManager(fasciaManager);
		  location.setReplenisherManager(replenisherManager);
		  fasciaManager.setLocation(location);
	  }

	  @After
	  public void tearDown() throws Exception {
	    
	  }

	  @Test
	  public void testGetAmount() {
	    assertEquals(this.amount, fasciaManager.getAmount());
	  }
	  
	  @Test
	  public void testGetFasciaSku() {
	    assertEquals(this.sku, fasciaManager.getFasciaWithoutUpdate().getSku());
	  }
	  
	  
	  @Test
	  public void testGetFasciaColour() {
	    assertEquals(this.colour, fasciaManager.getFasciaColour());
	  }
	  
	  	  
	  @Test
	  public void testGetFasciaModel() {
	    assertEquals(this.model, fasciaManager.getFasciaModel());
	  }
	  
	  	  
	  @Test
	  public void testGetFrontInfo() {
	    assertEquals(this.isFront, fasciaManager.getFrontInfo());
	  }
	  
	  
	  	  @Test
	  public void testToString() {
	    assertEquals("I am fascia manager, " + "my fascia's colour is: " + this.fascia.getColour() 
    + ", and my fascia's model is: " + this.fascia.getModel() + ", and the amount of this fascia is: " + this.amount + ".", fasciaManager.toString());
	  }
	  



	  	  @Test
	  public void testSetAmount() {
	  fasciaManager.setAmount(6);
	    assertEquals(6,fasciaManager.getAmount());
	  }
	  
	  	  @Test
	  public void testGetFascia() {
	  	fasciaManager.setAmount(6);
	  	System.out.println(fasciaManager.getAmount());
	    assertEquals(this.fascia, fasciaManager.getFascia());
	    System.out.println(fasciaManager.getAmount());
	    assertEquals(30, fasciaManager.getAmount());
	    
	  }

	  	  @Test
	  public void testReplenishing() {
	  fasciaManager.setAmount(5);
	  fasciaManager.replenishing();
	    assertEquals(30,fasciaManager.getAmount());
	  }


	  	  @Test
	  public void testSetFascia() {
	  Fascia testFascia = new Fascia("BLUE", "SEE", "9", true);
	  fasciaManager.setFascia(testFascia);
	    assertEquals(testFascia,fasciaManager.getFascia());
	  }

	  	  @Test
	  public void testSetFasciaSku() {
	  Fascia testFascia = new Fascia("BLUE", "SEE", "9", true);
	  String testSku = "6";
	  	fasciaManager.setFasciaSku(testSku);
	    assertEquals(testSku,fasciaManager.getFasciaSku());
	  }

	  	  @Test
	  public void testSetLocation() throws SkuNotExistException {
	  Location testLocation = new Location(fascia);
	  	fasciaManager.setLocation(testLocation);
	    assertEquals(testLocation,fasciaManager.getLocation());
	  }

	  @Test
	  public void testGetLocation() {
	    assertEquals(location, fasciaManager.getLocation());
	  }

}
