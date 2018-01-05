package model;

import java.io.IOException;
import java.util.ArrayList;

import exception.EmptyBlankInFileException;
import exception.NamingPickerNotFreeException;
import exception.PickerNotExistException;
import exception.SkuNotExistException;
import loading.Truck;
import processor.FinishRequestProcessor;
import processor.OrderProcessor;
import processor.PickingRequestProcessor;
import sequencing.MarshallingArea;
import util.WareHouse;
import worker.Loader;
import worker.Picker;
import worker.Replenisher;
import worker.Sequencer;



public class Company {

  /** Run the simulation. */
  public static void main(String[] args)
      throws SkuNotExistException, NumberFormatException, IOException, NamingPickerNotFreeException,
      PickerNotExistException, EmptyBlankInFileException {
    // do some initial work.
    ArrayList<Picker> pickerList = new ArrayList<Picker>();
    ArrayList<Sequencer> sequencerList = new ArrayList<Sequencer>();
    ArrayList<Loader> loaderList = new ArrayList<Loader>();
    ArrayList<Replenisher> replenisherList = new ArrayList<Replenisher>();
    WareHouse wareHouse =
        new WareHouse("../translation.csv", "../traversal_table.csv", "../initial.csv");
    // build an OrderProcessor
    OrderProcessor orderProcessor = new OrderProcessor();
    // build an PickingRequestProcessor
    PickingRequestProcessor pickingRequestProcessor = new PickingRequestProcessor();
    // build an Marshaling Area
    MarshallingArea marshallingArea = new MarshallingArea(wareHouse);
    // build an Truck List
    ArrayList<Truck> truckList = new ArrayList<Truck>();
    FinishRequestProcessor finishRequestProcessor = new FinishRequestProcessor();
    EventReader readEvent = new EventReader("../testorders.txt", wareHouse, orderProcessor,
        pickingRequestProcessor, pickerList, sequencerList, loaderList, replenisherList,
        marshallingArea, truckList, finishRequestProcessor);
    readEvent.readEvent();
    OutputFileWriter writeFile = new OutputFileWriter(wareHouse, finishRequestProcessor);
    writeFile.writeCsvFile();
    writeFile.writeOrderFile();


  }
}
