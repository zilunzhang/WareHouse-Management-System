package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import manager.FasciaManager;
import manager.ReplenisherManager;
import util.Fascia;
import util.Location;
import util.WareHouse;
import worker.Replenisher;

public class ReplenisherTest {
	

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
	  public void testUpdate() {
		  fasciaManager.setAmount(5);
		  this.replenisher.update(location);
	    assertEquals(30, fasciaManager.getAmount());
	    assertEquals(false, replenisher.isFree());
	    
	    
	  }
	
	
	


}
