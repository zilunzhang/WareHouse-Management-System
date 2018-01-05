package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import ordering.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import picking.PickingRequest;

import util.WareHouse;

public class PickingRequestTest {

  private WareHouse wareHouse;
  private ArrayList<Order> orderGroup = new ArrayList<>();
  private PickingRequest pickingRequest;

  @Before
  public void setUp() throws Exception {
    this.wareHouse =
        new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");

    orderGroup.add(new Order("Beige", "S", wareHouse));
    orderGroup.add(new Order("Beige", "SEL", wareHouse));
    orderGroup.add(new Order("Red", "SES", wareHouse));
    orderGroup.add(new Order("Green", "SE", wareHouse));
    pickingRequest = new PickingRequest(orderGroup, wareHouse);

  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testgetAllSku() {

    ArrayList<String> skuList = pickingRequest.getAllSku();
    assertEquals(8, skuList.size());
    assertEquals("9", skuList.get(0));
    assertEquals("10", skuList.get(1));
    assertEquals("15", skuList.get(2));
    assertEquals("16", skuList.get(3));
    assertEquals("21", skuList.get(4));
    assertEquals("22", skuList.get(5));
    assertEquals("27", skuList.get(6));
    assertEquals("28", skuList.get(7));
    int a = this.pickingRequest.getId();
    
  }

  @Test
  public void testgetFrontSku() {

    ArrayList<String> frontSkuList = pickingRequest.getAllFrontSku();
    assertEquals(4, frontSkuList.size());
    assertEquals("9", frontSkuList.get(0));
    assertEquals("15", frontSkuList.get(1));
    assertEquals("21", frontSkuList.get(2));
    assertEquals("27", frontSkuList.get(3));
  }

  @Test
  public void testgetBackSku() {

    ArrayList<String> backSkuList = pickingRequest.getAllBackSku();
    assertEquals(4, backSkuList.size());
    assertEquals((new Order("Beige", "S", wareHouse)).getBackSku(), backSkuList.get(0));
    assertEquals((new Order("Beige", "SEL", wareHouse)).getBackSku(), backSkuList.get(1));
    assertEquals((new Order("Red", "SES", wareHouse)).getBackSku(), backSkuList.get(2));
    assertEquals((new Order("Green", "SE", wareHouse)).getBackSku(), backSkuList.get(3));
  }

  @Test
  public void testgetContentVisualInfo() {
    ArrayList<ArrayList<String>> contentVisualInfo = pickingRequest.getContentVisualInfo();
    assertEquals(4, contentVisualInfo.size());
    assertEquals("S", contentVisualInfo.get(0).get(0));
    assertEquals(2, contentVisualInfo.get(1).size());
    assertEquals("Red", contentVisualInfo.get(2).get(1));
    assertEquals("SE", contentVisualInfo.get(3).get(0));
  }

  @Test
  public void testtoStringList() {
    ArrayList<String> list = this.pickingRequest.toStringList();
    assertEquals(4, list.size());
    assertEquals("This Order's colour is: Beige, this order's model is: S", list.get(0));
  }

  @Test
  public void testtoString() {
    assertEquals("This picking request includes 4 Orders.", this.pickingRequest.toString());
  }

  @Test
  public void testgetOrderGroup() {
    assertEquals(4, this.pickingRequest.getOrderGroup().size());
    assertEquals("21", this.pickingRequest.getOrderGroup().get(2).getFrontSku());
  }
  
  @Test
  public void testLoaded() {
    this.pickingRequest.setLoaded(true);
    assertTrue(this.pickingRequest.isLoaded());
  }
  
  @Test
  public void testWareHouseBelonging() {
    this.pickingRequest.setContentSku(orderGroup);
    assertEquals(this.wareHouse, this.pickingRequest.getWareHouseBelonging());
  }
}
