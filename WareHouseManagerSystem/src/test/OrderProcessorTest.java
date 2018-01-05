package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ordering.Order;
import processor.OrderProcessor;
import util.WareHouse;

public class OrderProcessorTest {
  
  
  private OrderProcessor orderProcessor;
  private WareHouse wareHouse;
  private Order order1;
  private Order order2;
  private Order order3;
  private Order order4;

  @Before
  public void setUp() throws Exception {
    orderProcessor = new OrderProcessor();
    wareHouse = new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
    order1 = new Order("Blue", "SES" , wareHouse);
    order2 = new Order("Red", "SES", wareHouse);
    order3 = new Order("Black", "SE", wareHouse);
    order4 = new Order("Black", "SE", wareHouse);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testadd(){
    
    orderProcessor.add(order1);
    orderProcessor.add(order2);
    orderProcessor.add(order3);
    orderProcessor.add(order4);
    
    assertEquals(4, orderProcessor.getSizeCounter());

  }
  
  @Test
  public void testremove(){
    orderProcessor.add(order1);
    orderProcessor.remove();
    assertEquals(0, orderProcessor.getSizeCounter());
  }
  
  
  @Test
  public void testoutPut(){
    orderProcessor.add(order1);
    orderProcessor.add(order2);
    orderProcessor.add(order3);
    orderProcessor.add(order4);
    ArrayList<Order> temp = new  ArrayList<Order>();
    temp.add(order1);
    temp.add(order2);
    temp.add(order3);
    temp.add(order4);
    
    assertEquals(temp, orderProcessor.outPut());
  }
  
  @Test
  public void testgetOrderProcessor(){
    assertEquals(true, orderProcessor.getOrderProcessor() instanceof LinkedList<?>);
  }
  
  @Test
  public void testisGreaterThanThree(){
    orderProcessor.add(order1);
    orderProcessor.add(order2);
    orderProcessor.add(order3);
    orderProcessor.add(order4);
    assertEquals(true, orderProcessor.isGreaterThanThree());
    orderProcessor.remove();
    assertEquals(false, orderProcessor.isGreaterThanThree());
  }
  

}
