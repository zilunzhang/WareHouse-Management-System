package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ordering.Order;
import picking.PickingRequest;
import processor.PickingRequestProcessor;
import sequencing.MarshallingArea;
import util.Fascia;
import util.WareHouse;
import worker.Sequencer;

public class SequencerTest {

  private WareHouse wareHouse;
  private MarshallingArea marshallingArea;
  private Sequencer john;
  private PickingRequestProcessor requestProcessor;
  
  @Before
  public void setUp() throws Exception {
    this.wareHouse = new WareHouse("../translation.csv", 
        "../traversal_table.csv", 
        "../initial.csv");
    
    this.marshallingArea = new MarshallingArea(wareHouse);
   
    this.john = new Sequencer("John", wareHouse);
    Fascia f1 = new Fascia("White", "S", "1", true);
    Fascia f2 = new Fascia("White", "S", "2", false);
    Fascia f3 = new Fascia("White", "SES", "5", true);
    Fascia f4 = new Fascia("White", "SES", "6", false);
    Fascia f5 = new Fascia("Black", "SE", "43", true);
    Fascia f6 = new Fascia("Black", "SE", "44", false);
    Fascia f7 = new Fascia("Birch", "S", "49", true);
    Fascia f8 = new Fascia("Brich", "S", "50", false);
     
    ArrayList<Fascia> FasciaList = new ArrayList<>();
    FasciaList.add(f1);
    FasciaList.add(f2);
    FasciaList.add(f3);
    FasciaList.add(f4);
    FasciaList.add(f5);
    FasciaList.add(f6);
    FasciaList.add(f7);
    FasciaList.add(f8);
    
    this.marshallingArea.addReceivedProduct(FasciaList);
    
    Order order1 = new Order("White", "S", wareHouse);
    Order order2 = new Order("White", "SES", wareHouse);
    Order order3 = new Order("Black", "SE", wareHouse);
    Order order4 = new Order("Birch", "S", wareHouse);
    
    ArrayList<Order> orderGroup = new ArrayList<>();
    orderGroup.add(order1);
    orderGroup.add(order2);
    orderGroup.add(order3);
    orderGroup.add(order4);
    
    this.requestProcessor = new PickingRequestProcessor();
    
    PickingRequest request = new PickingRequest(orderGroup, wareHouse);
    this.requestProcessor.addFirst(request);
  }

  @After
  public void tearDown() throws Exception {}
  
  @Test
  public void testSequencerName() {
    assertEquals(john.getName(), "John");
  }
  
  @Test
  public void testSequencerState() {
    assertTrue(john.isFree());
  }
  
  @Test
  public void testSequencing() {
    john.addUnorderedFascias(marshallingArea);
    john.sequencing("2");
    john.sequencing("1");
    
    assertEquals(john.getPallet().getFrontFascia().get(0).getSku(), "1");
    assertEquals(john.getPallet().getBackFascia().get(0).getSku(), "2");
  }
  
  @Test 
  public void testVisuallyCorrect() {
    assertTrue(john.isVisuallyCorrect());
    john.setVisuallyCorrect(false);
    assertFalse(john.isVisuallyCorrect());
  }
  
  @Test
  public void testGetRequest(){
    this.marshallingArea.addReceivedRequest(this.requestProcessor.outPut());
    john.addRequest(marshallingArea);
    assertEquals(john.requestList.size(), 1);
    assertEquals(john.requestList.get(0).getAllSku().size(), 8);
    assertEquals(john.getRequest().getAllSku().get(0), "1");
    assertEquals(john.getRequest().getAllSku().get(1), "2");
  }
  
  @Test
  public void testBarcodeReader() {
    Fascia fascia = new Fascia("White", "S", "1", true);
    assertTrue(john.barcodeReader(fascia, "1"));
  }
  
  @Test
  public void testAddProuducts() {
    john.addUnorderedFascias(marshallingArea);
    assertEquals(john.getUnorderedProduct().size(), 8);
  }
  
  @Test
  public void testDiscard() {
    john.addUnorderedFascias(marshallingArea);
    john.sequencing("4");
    john.sequencing("3");
    
    john.discard(this.requestProcessor);
    assertTrue(john.getPallet().getFrontFascia().size() == 0);
    assertTrue(john.getPallet().getBackFascia().size() == 0);
  }
  
  @Test
  public void testCheckPickerWork() {
    this.marshallingArea.addReceivedRequest(this.requestProcessor.outPut());
    john.addRequest(marshallingArea);
    john.addUnorderedFascias(marshallingArea);
    
    assertFalse(john.checkPickerWork());
  }
  
//  @Test
//  public void testCheck() {
//    this.marshallingArea.addReceivedRequest(this.requestProcessor.outPut());
//    john.addRequest(marshallingArea);
//    john.addUnorderedFascias(marshallingArea);
//    
//    john.sequencing();
//    
//    assertTrue(john.check(john.getPallet()));
//    
//    john.getPallet().getFrontFascia().get(0).setModel("XL");
//    assertFalse(john.check(john.getPallet()));
//    
//    john.getPallet().getFrontFascia().get(0).setColour("Yellow");
//    assertFalse(john.check(john.getPallet()));
//    
//    john.getPallet().getFrontFascia().remove(0);
//    assertFalse(john.check(john.getPallet()));
//    
//  }
  
  @Test
  public void testBarCodeReader() {
    this.marshallingArea.addReceivedRequest(this.requestProcessor.outPut());
    john.addRequest(marshallingArea);
    john.addUnorderedFascias(marshallingArea);
    
    john.sequencing();
    
    assertFalse(john.barcodeReader());
  }
  
  @Test
  public void testBarCodeReaderMissingFront() {
    this.marshallingArea.addReceivedRequest(this.requestProcessor.outPut());
    john.addRequest(marshallingArea);
    john.addUnorderedFascias(marshallingArea);
    
    john.sequencing();
    
    john.getPallet().getFrontFascia().remove(0);
    assertFalse(john.barcodeReader());
  }
  
  @Test
  public void testBarCodeReaderMissingBack() {
    this.marshallingArea.addReceivedRequest(this.requestProcessor.outPut());
    john.addRequest(marshallingArea);
    john.addUnorderedFascias(marshallingArea);
    
    john.sequencing();
    
    john.getPallet().getBackFascia().remove(0);
    assertFalse(john.barcodeReader());
  }
  
  @Test
  public void testBarCodeReaderWrongSku() {
    this.marshallingArea.addReceivedRequest(this.requestProcessor.outPut());
    john.addRequest(marshallingArea);
    john.addUnorderedFascias(marshallingArea);
    
    john.sequencing();
    
    john.getPallet().getFrontFascia().get(0).setSku("10");
    
    assertFalse(john.barcodeReader());
  }
  
  @Test
  public void testIsDamaged() {
    this.marshallingArea.addReceivedRequest(this.requestProcessor.outPut());
    john.addRequest(marshallingArea);
    john.addUnorderedFascias(marshallingArea);
    
    assertFalse(john.isDamage());
    
    john.getUnorderedProduct().get(0).setDamage(true);
    
    assertTrue(john.isDamage());
  }
  
  @Test
  public void testToMarshallingArea() {
    this.marshallingArea.addReceivedRequest(this.requestProcessor.outPut());
    john.addRequest(marshallingArea);
    john.addUnorderedFascias(marshallingArea);
    
    john.sequencing();

    john.toMarshallingArea(marshallingArea);
    assertTrue(john.getPallet().getFrontFascia().size() == 0);
    assertTrue(john.getPallet().getBackFascia().size() == 0);
  }

}
