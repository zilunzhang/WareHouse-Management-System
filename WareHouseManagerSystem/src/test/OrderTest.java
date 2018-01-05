package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ordering.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.SkuNotExistException;
import util.WareHouse;


public class OrderTest {

  private Order order;
  private WareHouse wareHouse;

  @Before
  public void setUp() throws Exception {
    this.wareHouse =
        new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
    this.order = new Order("Blue", "SE", wareHouse);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testgetSku() {
    assertEquals("35", this.order.getSku(true));
    assertEquals("36", this.order.getSku(false));
  }
  
  @Test
  public void testgetFrontSku() {
    assertEquals("35", this.order.getFrontSku());
  }
  
  @Test
  public void testgetBackSku() {
    assertEquals("36", this.order.getBackSku());
  }
  
  @Test
  public void testgetWareHouseBelonging() {
    assertEquals(this.wareHouse, this.order.getWareHouseBelonging());
  }
  
  @Test
  public void testgetColourInfo() {
    assertEquals("Blue", this.order.getColourInfo());
  }
  
  @Test
  public void testgetModelInfo() {
    assertEquals("SE", this.order.getModelInfo());
  }
  
  @Test
  public void testtoString() {
    assertEquals("This Order's colour is: Blue, this order's model is: SE", this.order.toString());
  }
  
//  @Test
//  public void testOrderThrowsSkuNotExistException() throws SkuNotExistException{
//    boolean thrown = false;
//    Order order = new Order("I am", "SSSSS", wareHouse);
//    order.toString();
//    assertEquals(false, thrown);
//  }
  
}
