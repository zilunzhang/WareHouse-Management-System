package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import picking.WarehousePicking;
import util.Location;
import util.WareHouse;



public class WareHousePickingTest {
  
  private WarehousePicking wp;
  private WareHouse wareHouse;
  private ArrayList<String> skus = new ArrayList<>();
  
  @Before
  public void setUp() throws Exception {
    wp = new WarehousePicking();
    this.wareHouse =
        new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
    skus.add("7");
    skus.add("8");
    skus.add("9");
    skus.add("19");
  }

  @After
  public void tearDown() throws Exception {}
  
  @Test
  public void testoptimize() {
    ArrayList<Location> lst = WarehousePicking.optimize(skus, wareHouse);
    assertEquals(4, lst.size());
    assertEquals("A012", lst.get(0).getZone() + lst.get(0).getAisle() + lst.get(0).getRack() + lst.get(0).getLevel());
    assertEquals("8", lst.get(1).getFasciaManager().getFasciaSku());
    assertEquals("A020", lst.get(2).getZone() + lst.get(2).getAisle() + lst.get(2).getRack() + lst.get(2).getLevel());
    assertEquals("A112", lst.get(3).getZone() + lst.get(3).getAisle() + lst.get(3).getRack() + lst.get(3).getLevel());
  }
}
