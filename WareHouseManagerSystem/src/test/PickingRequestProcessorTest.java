package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ordering.Order;
import picking.PickingRequest;
import processor.PickingRequestProcessor;
import util.WareHouse;

public class PickingRequestProcessorTest {
  private PickingRequestProcessor pickingRequestProcessor;
  private WareHouse wareHouse;
  private PickingRequest pickingRequest1;
  private PickingRequest pickingRequest2;
  private ArrayList<Order> orderGroup1;
  private ArrayList<Order> orderGroup2;
  private Order order1;
  private Order order2;
  private Order order3;
  private Order order4;
  private Order order5;

  @Before
  public void setUp() throws Exception {
    pickingRequestProcessor = new PickingRequestProcessor();
    wareHouse = new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
    order1 = new Order("Blue", "SES" , wareHouse);
    order2 = new Order("Red", "SES", wareHouse);
    order3 = new Order("Black", "SE", wareHouse);
    order4 = new Order("Black", "SE", wareHouse);
    order5 = new Order("Beige", "S", wareHouse);
    orderGroup1= new ArrayList<Order>();
    orderGroup2= new ArrayList<Order>();
    orderGroup1.add(order1);
    orderGroup1.add(order2);
    orderGroup1.add(order3);
    orderGroup1.add(order4);
    pickingRequest1 = new PickingRequest(orderGroup1, wareHouse);
    
    
    orderGroup2.add(order5);
    orderGroup2.add(order2);
    orderGroup2.add(order3);
    orderGroup2.add(order4);
    pickingRequest2 = new PickingRequest(orderGroup2, wareHouse);
    
    
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testadd(){
    
    pickingRequestProcessor.add(pickingRequest1);
    assertEquals(1, pickingRequestProcessor.getSizeCounter());

  }
  @Test
  public void testaddFirst(){
    pickingRequestProcessor.add(pickingRequest1);
    pickingRequestProcessor.addFirst(pickingRequest2);
    pickingRequestProcessor.remove();
    assertEquals("37", pickingRequestProcessor.outPut().getAllSku().get(0));
  }
  

  @Test
  public void testremove(){
    pickingRequestProcessor.add(pickingRequest1);
    pickingRequestProcessor.remove();
    assertEquals(0, pickingRequestProcessor.getSizeCounter());
    
  }
  
  
  @Test
  public void testoutPut(){
    pickingRequestProcessor.add(pickingRequest1);

    ArrayList<Order> temp = new  ArrayList<Order>();
    temp.add(order1);
    temp.add(order2);
    temp.add(order3);
    temp.add(order4);
    
    assertEquals(temp, pickingRequestProcessor.outPut().getOrderGroup());
  }
  
  @Test
  public void testgetSizeCounter(){
    pickingRequestProcessor.add(pickingRequest1);
    assertEquals(1, pickingRequestProcessor.getSizeCounter());
  }
  
  @Test
  public void getContent(){
    
    assertEquals(true, pickingRequestProcessor.getContent() instanceof LinkedList );
  }
  
}
