package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ordering.Order;
import picking.PickingRequest;
import sequencing.MarshallingArea;
import sequencing.Pallet;
import util.Fascia;
import util.WareHouse;


public class MarshallingAreaTest {

  private WareHouse wareHouse;
  private MarshallingArea ms;
  private ArrayList<Fascia> products = new ArrayList<>();
  private ArrayList<Order> orders = new ArrayList<>();
  private Pallet pallet;
  private PickingRequest request;

  @Before
  public void setUp() throws Exception {
    this.wareHouse =
        new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
    this.ms = new MarshallingArea(wareHouse);
    products.add(new Fascia("White", "SES", "5", true));
    products.add(new Fascia("White", "SES", "6", false));
    products.add(new Fascia("White", "SEL", "7", true));
    products.add(new Fascia("Blue", "SE", "35", true));

    orders.add(new Order("Blue", "SE", wareHouse));
    orders.add(new Order("White", "SE", wareHouse));
    orders.add(new Order("White", "SES", wareHouse));
    orders.add(new Order("White", "SEL", wareHouse));

    this.pallet = new Pallet();
    this.pallet.addProduct(new Fascia("White", "SE", "3", true));
    this.pallet.addProduct(new Fascia("White", "SE", "4", false));

    request = new PickingRequest(this.orders, this.wareHouse);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testaddReceivedProduct() {
    this.ms.addReceivedProduct(products);
    assertEquals(1, ms.getReceivedFasciaSetSizeCount());
    this.ms.addReceivedProduct(products);
    assertEquals(2, ms.getReceivedFasciaSetSizeCount());
  }

  @Test
  public void testgetFirstReceivedProduct() {
    this.ms.addReceivedProduct(products);
    assertEquals(1, ms.getReceivedFasciaSetSizeCount());
    ArrayList<Fascia> lst1 = ms.getFirstReceivedProduct();
    assertEquals(4, lst1.size());
    assertEquals("White SES 5", lst1.get(0).toString());
    assertEquals("White SEL 7", lst1.get(2).toString());
    assertEquals(0, ms.getReceivedFasciaSetSizeCount());

    this.ms.addReceivedProduct(products);
    this.ms.addReceivedProduct(products);
    assertEquals(2, ms.getReceivedFasciaSetSizeCount());
    ArrayList<Fascia> lst2 = ms.getFirstReceivedProduct();
    assertEquals(4, lst2.size());
    assertEquals("Blue SE 35", lst2.get(3).toString());
    assertEquals(1, ms.getReceivedFasciaSetSizeCount());
    this.ms.addReceivedProduct(products);
    this.ms.addReceivedProduct(products);
    assertEquals(3, ms.getReceivedFasciaSetSizeCount());

    assertEquals("White SES 6", this.ms.getFirstReceivedProduct().get(1).toString());
  }

  @Test
  public void testReceivedRequest() {
    this.ms.addReceivedRequest(this.request);
    assertEquals(1, this.ms.getReceivedRequestSizeCount());
    PickingRequest p = this.ms.getFirstReceivedRequest();
    assertEquals(p.toString(), this.request.toString());
    assertEquals(0, this.ms.getReceivedRequestSizeCount());
  }

  @Test
  public void testReadyPallet() {
    this.ms.addReadyPallet(pallet);
    assertEquals(this.pallet, this.ms.getReadyPalletNotRemove());
    assertEquals(1, this.ms.getReadyPalletSizeCount());
    Pallet pt = ms.getReadyPallet();
    assertEquals(0, this.ms.getReadyPalletSizeCount());
    assertEquals(pt.toString(), pallet.toString());
  }

  @Test
  public void testReadyRequest() {
    this.ms.addReadyRequest(request);
    assertEquals(this.request, ms.getReadyRequestNotRemove());
    assertEquals(1, ms.getReadyRequestSizeCount());
    PickingRequest pr = ms.getReadyRequest();
    assertEquals(0, ms.getReadyRequestSizeCount());
    assertEquals(pr.toString(), request.toString());
  }

  @Test
  public void testgetWareHouseBelonging() {
    assertEquals(this.wareHouse, this.ms.getWareHouseBelonging());
  }

}
