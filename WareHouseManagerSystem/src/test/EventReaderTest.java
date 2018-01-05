package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import exception.NamingPickerNotFreeException;
import exception.NotFinishPickingAllEightFascias;
import exception.PickerNotExistException;
import loading.Truck;
import manager.ReplenisherManager;
import manager.WareHouseManager;
import model.EventReader;
import ordering.Order;
import picking.PickingRequest;
import processor.FinishRequestProcessor;
import processor.OrderProcessor;
import processor.PickingRequestProcessor;
import sequencing.MarshallingArea;
import util.Fascia;
import util.Location;
import util.WareHouse;
import worker.*;




public class EventReaderTest {


      private String path;
      private WareHouse wareHouse;
      private OrderProcessor orderProcessor;
      private PickingRequestProcessor pickingRequestProcessor;
      private MarshallingArea marshallingArea;
      private FinishRequestProcessor finishRequestProcessor;
      private ArrayList<Picker> pickerList;
      private ArrayList<Sequencer> sequencerList;
      private ArrayList<Loader> loaderList;
      private ArrayList<Replenisher> replenisherList;
      private ArrayList<Truck> truckList;
      private ReplenisherManager replenisherManager;

      private EventReader eventReader;

      
      
      
      @Before
      public void setUp() throws Exception {
          
            this.path = "../testorders.txt";
            this.wareHouse = new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
            this.orderProcessor = new OrderProcessor();
            this.pickingRequestProcessor = new PickingRequestProcessor();
            this.marshallingArea =  new MarshallingArea(wareHouse);
            this.finishRequestProcessor =  new FinishRequestProcessor();
            
            this.pickerList = new ArrayList<Picker>();
            this.sequencerList = new ArrayList<Sequencer>();
            this.loaderList = new ArrayList<Loader>();
            this.replenisherList =  new ArrayList<Replenisher>();
            this.truckList = new ArrayList<Truck>();
            this.eventReader = new EventReader(path, wareHouse, orderProcessor,
                      pickingRequestProcessor,  pickerList,
                      sequencerList, loaderList,
                      replenisherList, marshallingArea,
                       truckList, finishRequestProcessor);
 
      }
      
      @After
      public void tearDown() throws Exception {}



    @Test
    public void testReadEvent() throws IOException, NamingPickerNotFreeException, PickerNotExistException {
        eventReader.readEvent();
        assertEquals(2, eventReader.getFinishRequestProcessor().getSizeCounter());
    }
    
    @Test
    public void testGetReplenisherManager() throws IOException, NamingPickerNotFreeException, PickerNotExistException {
        assertEquals(this.replenisherManager, eventReader.getReplenisherManager());

    }
    
    
    @Test
    public void testSetReplenisherManager() throws IOException, NamingPickerNotFreeException, PickerNotExistException {
        ReplenisherManager testRM = new ReplenisherManager(replenisherList);
        eventReader.setReplenisherManager(testRM );
        assertEquals(testRM , eventReader.getReplenisherManager());

    }
    
    @Test
    public void testgetFreePicker() throws IOException, NamingPickerNotFreeException, PickerNotExistException {
        
        Picker testPicker = new Picker("pickerTest", wareHouse);
        eventReader.setPickerList(new ArrayList<Picker>());
        assertEquals(null, eventReader.getFreePicker());
        testPicker.setState(true);
        eventReader.getPickerList().add(testPicker);
        
        assertEquals(testPicker, eventReader.getFreePicker());

    }
    
    
    @Test
    public void testgetFreeSequencer() throws IOException, NamingPickerNotFreeException, PickerNotExistException {
        
        Sequencer testSequencer = new Sequencer("SequencerTest", wareHouse);
        eventReader.setSequencerList(new ArrayList<Sequencer>());
        assertEquals(null, eventReader.getFreeSequencer());
        testSequencer.setState(true);
        eventReader.getSequencerList().add(testSequencer);
        assertEquals(testSequencer, eventReader.getFreeSequencer());
    }

    @Test
    public void testgetFreeReplenisher() throws IOException, NamingPickerNotFreeException, PickerNotExistException {
        
        Replenisher testReplenisher = new Replenisher("ReplenisherTest", wareHouse);
        eventReader.setReplenisherList(new ArrayList<Replenisher>());
        assertEquals(null, eventReader.getFreeReplenisher());
        testReplenisher.setState(true);
        eventReader.getReplenisherList().add(testReplenisher);
        assertEquals(testReplenisher, eventReader.getFreeReplenisher());

    }

    @Test
    public void testgetFreeLoader() throws IOException, NamingPickerNotFreeException, PickerNotExistException {

        Loader testLoader = new Loader("LoaderTest", wareHouse);
        eventReader.setLoaderList(new ArrayList<Loader>());
        assertEquals(null, eventReader.getFreeLoader());
        testLoader.setState(true);
        eventReader.getLoaderList().add(testLoader);
        assertEquals(testLoader, eventReader.getFreeLoader());

    }
    

    @Test
    public void testFindReplenisherThroughName() throws IOException, NamingPickerNotFreeException, PickerNotExistException {
        
        Replenisher testReplenisher = new Replenisher("ReplenisherTest", wareHouse);
        eventReader.setReplenisherList(new ArrayList<Replenisher>());
        assertEquals(null, eventReader.findReplenisherThroughName("ReplenisherTest"));
        testReplenisher.setState(true);
        eventReader.getReplenisherList().add(testReplenisher);
        assertEquals(testReplenisher, eventReader.findReplenisherThroughName("ReplenisherTest"));

    }

    
}