package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import manager.ReplenisherManager;
import util.WareHouse;
import worker.Replenisher;

public class ReplenisherManagerTest {

  private ArrayList<Replenisher> replenisherList;
  private ReplenisherManager replenisherManager;
  private WareHouse wareHouse;
  private Replenisher replenisher;

  @Before
  public void setUp() throws Exception {
    wareHouse = new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
    replenisher = new Replenisher("Batman", wareHouse);
    replenisher.setState(true);
    replenisherList = new ArrayList<Replenisher>();
    replenisherList.add(replenisher);
    replenisherManager = new ReplenisherManager(replenisherList);

  }

  @After
  public void tearDown() throws Exception {

  }

  @Test
  public void testGetReplenisherList() {
    assertEquals(this.replenisherList, replenisherManager.getReplenisherList());
  }

  @Test
  public void testGetFreeReplenisher() {
    assertEquals(replenisher, replenisherManager.getFreeReplenisher(replenisherList));

  }

  @Test
  public void testGetReplenisherForManager() {
    assertEquals(replenisher, replenisherManager.getReplenisherForManager());
  }

  @Test
  public void testFindReplenisherThroughName() {
    assertEquals(replenisher,
        replenisherManager.findReplenisherThroughName(replenisherList, "Batman"));
  }

  @Test
  public void testSetReplenisherList() {
    ArrayList<Replenisher> testReplenisherList = new ArrayList<Replenisher>();
    replenisherManager.setReplenisherList(testReplenisherList);
    assertEquals(testReplenisherList, replenisherManager.getReplenisherList());
  }
  
  @Test
  public void testfindReplenisherThroughName() {
    assertEquals(null, this.replenisherManager.findReplenisherThroughName(replenisherList, "Li Ming")); 
  }
  
  @Test
  public void testgetFreeReplenisher() {
    ArrayList<Replenisher> lst = new ArrayList<>();
    assertEquals(null, this.replenisherManager.getFreeReplenisher(lst)); 
  }



}
