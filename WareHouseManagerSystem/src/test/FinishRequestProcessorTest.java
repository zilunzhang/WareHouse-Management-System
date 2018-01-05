package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ordering.Order;
import picking.PickingRequest;
import processor.FinishRequestProcessor;
import util.WareHouse;

public class FinishRequestProcessorTest {
  private FinishRequestProcessor finishedRequestProcessor;
  private WareHouse wareHouse;
  private PickingRequest pickingRequest1;
  private ArrayList<Order> orderGroup1;
  private ArrayList<Order> orderGroup2;
  private Order order1;
  private Order order2;
  private Order order3;
  private Order order4;
  private Order order5;

  @Before
  public void setUp() throws Exception {
    finishedRequestProcessor = new FinishRequestProcessor();
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
    
    
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testadd(){
    
    finishedRequestProcessor.add(pickingRequest1);
    assertEquals(1, finishedRequestProcessor.getSizeCounter());
    assertEquals(4, finishedRequestProcessor.getContents().size());

  }
  

  @Test
  public void testremove(){
    finishedRequestProcessor.add(pickingRequest1);
    finishedRequestProcessor.remove();
    assertEquals(1, finishedRequestProcessor.getSizeCounter());
    
  }
  
  
  @Test
  public void testoutPut(){
    finishedRequestProcessor.add(pickingRequest1);

    ArrayList<Order> temp = new  ArrayList<Order>();
    temp.add(order1);
    temp.add(order2);
    temp.add(order3);
    temp.add(order4);
    
    assertEquals(41, finishedRequestProcessor.outPut().toString().length());
  }
  
  @Test
  public void testgetFinishedRequestProcessor(){
    assertEquals(true, finishedRequestProcessor.getFinishedRequestProcessor() instanceof ArrayList<?> );
  }

}
