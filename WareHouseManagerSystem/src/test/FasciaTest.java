package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.Fascia;

public class FasciaTest {

  private String model = "SE";
  private String colour = "Green";
  private String sku = "10";
  private Fascia fascia;

  @Before
  public void setUp() throws Exception {
    this.fascia = new Fascia("Green", "SE", "10", true);
  }

  @After
  public void tearDown() throws Exception {}
  
  @Test
  public void testequals() {
    assertTrue(this.fascia.equals(new Fascia("Green", "SE", "10", true)));
    assertFalse(this.fascia.equals(new Fascia("White", "S", "1", true)));
  }

  @Test
  public void testGetModel() {
    assertEquals(this.model, fascia.getModel());
  }

  @Test
  public void testGetColour() {
    assertEquals(this.colour, fascia.getColour());
  }

  @Test
  public void testGetSku() {
    assertEquals(this.sku, fascia.getSku());
  }

  @Test
  public void testIsFront() {
    assertEquals(fascia.isFront(), true);
  }

  @Test
  public void testIsDamage() {
    assertFalse(fascia.isDamage());
  }

  @Test
  public void testSetDamage() {
    fascia.setDamage(true);
    assertTrue(fascia.isDamage());
  }

  @Test
  public void TestSetColour() {
    fascia.setColour("Blue");
    assertEquals(fascia.getColour(), "Blue");
  }


  @Test
  public void TestSetModel() {
    fascia.setModel("SEL");
    assertEquals(fascia.getModel(), "SEL");
  }

  @Test
  public void TestSetFront() {
    fascia.setFront(false);
    assertEquals(fascia.isFront(), false);
  }
  
  @Test
  public void TestsetSku() {
    fascia.setSku("50");
    assertEquals("50", fascia.getSku());
  }

}
