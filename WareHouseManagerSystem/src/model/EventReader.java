
package model;

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
import ordering.Order;
import picking.PickingRequest;
import processor.FinishRequestProcessor;
import processor.OrderProcessor;
import processor.PickingRequestProcessor;
import sequencing.MarshallingArea;
import util.WareHouse;
import worker.*;


/** Reference: https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/. */

public class EventReader {


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

  /** Given a event file ie. 16order.txt do the file reading.*/
  public EventReader(String path, WareHouse wareHouse, OrderProcessor orderProcessor,
      PickingRequestProcessor pickingRequestProcessor, ArrayList<Picker> pickerList,
      ArrayList<Sequencer> sequencerList, ArrayList<Loader> loaderList,
      ArrayList<Replenisher> replenisherList, MarshallingArea marshallingArea,
      ArrayList<Truck> truckList, FinishRequestProcessor finishRequestProcessor) {

    this.path = path;
    this.wareHouse = wareHouse;
    this.orderProcessor = orderProcessor;
    this.pickingRequestProcessor = pickingRequestProcessor;
    this.marshallingArea = marshallingArea;
    this.finishRequestProcessor = finishRequestProcessor;
    this.pickerList = pickerList;
    this.sequencerList = sequencerList;
    this.loaderList = loaderList;
    this.replenisherList = replenisherList;
    this.truckList = truckList;

  }



  public ArrayList<Picker> getPickerList() {
    return pickerList;
  }



  public ArrayList<Sequencer> getSequencerList() {
    return sequencerList;
  }



  public ArrayList<Loader> getLoaderList() {
    return loaderList;
  }



  public ArrayList<Replenisher> getReplenisherList() {
    return replenisherList;
  }


  /** Helper.*/
  public void readEvent()
      throws IOException, NamingPickerNotFreeException, PickerNotExistException {
    BufferedReader br = null;
    FileReader fr = null;
    try {
      fr = new FileReader(path);
      br = new BufferedReader(fr);
      String sCurrentLine;
      br = new BufferedReader(new FileReader(path));
      while ((sCurrentLine = br.readLine()) != null) {
        // make a string array store all info in this line.
        String[] parts = sCurrentLine.split(" ");
        if (parts[0].equals("Order")) {
          // if first part of this line is picker, identify this line is a order.
          // produce a order
          Order order = new Order(parts[2], parts[1], wareHouse);
          // deal with order
          dealWithOrder(order, orderProcessor, pickingRequestProcessor);
        }
        // if first part of this line is picker, identify this line is a picker.
        if (parts[0].equals("Picker")) {
          Picker picker = null;
          boolean flag = true;
          picker = findPickerThroughName(parts[1]);
          if (picker != null) {
            flag = false;
          }
          // we know picker not exist before.
          // build a new picker to deal with things.
          if (flag) {
            picker = new Picker(parts[1], wareHouse);
          }
          if (parts[2].equals("ready")) {
            pickerIsReady(picker, pickingRequestProcessor, pickerList);
          }
          // if third part of this line is ready, we know there is a picker is able to be used.
          else if (parts[2].equals("pick")) {
            pickerPick(picker, parts[3]);
          } else if (parts[3].equals("Marshaling")) {
            pickerToMarshalling(picker, marshallingArea);
          }
        }

        if (parts[0].equals("Sequencer")) {
          Sequencer sequencer = null;
          boolean flag = true;

          sequencer = findSequencerThroughName(parts[1]);
          if (sequencer != null) {
            flag = false;
          }
          // build a new sequencer to deal with things.
          if (flag) {
            sequencer = new Sequencer(parts[1], wareHouse);
          }
          if (parts[2].equals("ready")) {
            sequencerIsReady(sequencer, marshallingArea, sequencerList);
          }
          // if third part of this line is sequence, we know there is a picker is able to be used
          // and we need him/her do sth.
          else if (parts[2].equals("sequence")) {
            sequencerSequence(sequencer, pickingRequestProcessor, parts[3]);
          } else if (parts[2].equals("rescan")) {
            sequencerRescan(sequencer, pickingRequestProcessor);
          } else if (parts[2].equals("finish")) {
            sequencerFinish(sequencer, marshallingArea);
          }
        }

        if (parts[0].equals("Loader")) {
          Loader loader = null;
          boolean flag = true;
          loader = findLoaderThroughName(parts[1]);
          if (loader != null) {
            flag = false;
          }
          // We know sequencer not exist before.
          // build a new loader to deal with things.
          if (flag) {
            loader = new Loader(parts[1], wareHouse);
          }
          if (parts[2].equals("ready")) {
            loaderReady(loader, marshallingArea);
          } else if (parts[2].equals("loads")) {
            loaderLoad(loader, pickingRequestProcessor, finishRequestProcessor, truckList);
          } else if (parts[2].equals("rescan")) {
          }
        }
        if (parts[0].equals("Replenisher")) {

          Replenisher replenisher = null;
          boolean flag = true;
          replenisher = findReplenisherThroughName(parts[1]);
          if (replenisher != null) {
            flag = false;
          }
          if (flag) {
            replenisher = new Replenisher(parts[1], wareHouse);
          }
          if (parts[2].equals("ready")) {
            replenisher.setState(true);
            WareHouseManager.getLogger().log(Level.INFO,
                "INPUT: " + replenisher.getName() + " is ready.");
            replenisherList.add(replenisher);
            System.out.println("replenisherlist's size is: " + replenisherList.size());
            this.replenisherManager = new ReplenisherManager(this.replenisherList);
            wareHouse.setReplenisherManager(replenisherManager);
            wareHouse.setAllReplenisherManager(replenisherManager);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)
          br.close();
        if (fr != null)
          fr.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    System.out.println("Read event finished!!!!");
    WareHouseManager.getLogger().log(Level.INFO, "OUTPUT: " + "Read event finished!!!!");
    WareHouseManager.getFileHandler().close();
    WareHouseManager.getConsolehandler().close();
  }

  public Picker getFreePicker() {
    for (Picker picker : pickerList) {
      if (picker.isFree()) {
        return picker;
      }
    }
    return null;
  }

  public Sequencer getFreeSequencer() {
    for (Sequencer sequencer : sequencerList) {
      if (sequencer.isFree()) {
        sequencer.setState(false);
        return sequencer;
      }
    }
    return null;
  }

  public Loader getFreeLoader() {
    for (Loader loader : loaderList) {
      if (loader.isFree()) {
        return loader;
      }
    }
    return null;
  }

  public Replenisher getFreeReplenisher() {
    for (Replenisher replenisher : replenisherList) {
      if (replenisher.isFree()) {
        return replenisher;
      }
    }
    return null;
  }

  public Picker findPickerThroughName(String name) throws PickerNotExistException {
    for (Picker picker : pickerList) {
      if (picker.getName().equals(name)) {
        return picker;
      }
    }
    return null;

  }

  public Sequencer findSequencerThroughName(String name) {
    for (Sequencer sequencer : sequencerList) {
      if (sequencer.getName().equals(name)) {
        return sequencer;
      }
    }
    return null;
  }

  public Loader findLoaderThroughName(String name) {
    for (Loader loader : loaderList) {
      if (loader.getName().equals(name)) {
        return loader;
      }
    }
    return null;
  }

  public Replenisher findReplenisherThroughName(String name) {
    for (Replenisher replenisher : replenisherList) {
      if (replenisher.getName().equals(name)) {
        return replenisher;
      }
    }
    return null;
  }


  public ReplenisherManager getReplenisherManager() {
    return replenisherManager;
  }


  public void setReplenisherManager(ReplenisherManager replenisherManager) {
    this.replenisherManager = replenisherManager;
  }

  public void dealWithOrder(Order order, OrderProcessor orderProcessor,
      PickingRequestProcessor pickingRequestProcessor) {
    // add this order to orderProcessor
    orderProcessor.add(order);
    WareHouseManager.getLogger().log(Level.INFO,
        "INPUT: Added a new order: " + order.getModelInfo() + " " + order.getColourInfo());
    System.out.println("Order processor now has " + orderProcessor.getSizeCounter() + " orders");
    // check if order processor reaches four, if so, out put, if not, do nothing.
    if (orderProcessor.isGreaterThanThree()) {
      // store output.
      ArrayList<Order> orderPack = orderProcessor.outPut();
      // wrap a group of 4 orders to be a picking request.
      PickingRequest pickingRequest = new PickingRequest(orderPack, wareHouse);
      // add to processor.
      pickingRequestProcessor.add(pickingRequest);
      System.out.println("There is a picking request added to picking request processor.");
      WareHouseManager.getLogger().log(Level.INFO,
          "NOTE: There is a picking request added to picking request processor. Skus are: "
              + pickingRequest.getAllSku());
    }
  }


  public void pickerIsReady(Picker picker, PickingRequestProcessor pickingRequestProcessor,
      ArrayList<Picker> pickerList) {
    //// if third part of this line is ready, we know there is a picker is able to be used.
    picker.setState(true);
    // System.out.println("I, " + picker.getName() + " is Ready");
    WareHouseManager.getLogger().log(Level.INFO, "INPUT: " + picker.getName() + " is Ready.");
    if (pickingRequestProcessor.getSizeCounter() > 0) {
      PickingRequest pickingRequest = pickingRequestProcessor.outPut();
      picker.addRequestToList(pickingRequest);
      picker.dealWithPickingRequest(pickingRequest.getAllSku());
      WareHouseManager.getLogger().log(Level.INFO,
          "NOTE: There is a picking request added to " + picker.getName()
              + " from picking request processor. Now, there are "
              + pickingRequestProcessor.getSizeCounter() + " picking requests in the processor.");
    }

    pickerList.add(picker);
    System.out.println("now, pickerlist's size is: " + pickerList.size());
  }

  public void pickerPick(Picker picker, String sku) {

    WareHouseManager.getLogger().log(Level.INFO,
        "INPUT: " + picker.getName() + " received a 'pick' order.");
    if (picker.isFree()) {
      picker.setState(false);
      // put everything into fork lift.
      picker.picking(sku);
    } else {
      WareHouseManager.getLogger().log(Level.SEVERE,
          "NOTE:: " + picker.getName() + " is not free.");
      try {
        throw new NamingPickerNotFreeException("This Picker is not free");
      } catch (NamingPickerNotFreeException e) {
        e.printStackTrace();
      }
    }
  }

  public void pickerToMarshalling(Picker picker, MarshallingArea marshallingArea) {
    try {
      picker.toMarshalling(marshallingArea);
    } catch (NotFinishPickingAllEightFascias e) {

      e.printStackTrace();
    }
    WareHouseManager.getLogger().log(Level.INFO,
        "NOTE: Picking Request and Fascias are sent to marshalling area by " + picker.getName()
            + ".");

    // System.out.println("Picking Request and Fascias sent to marshalling area by " +
    // picker.getName() + " .");
    System.out.println("Now, the marshalling area has "
        + marshallingArea.getReceivedFasciaSetSizeCount() + " arrayList of Fascias and "
        + marshallingArea.getReceivedRequestSizeCount() + " picking request.");
  }

  public void sequencerIsReady(Sequencer sequencer, MarshallingArea marshallingArea,
      ArrayList<Sequencer> sequencerList) {
    // if third part of this line is ready, we know there is a picker is able to be used.
    sequencer.setState(true);

    WareHouseManager.getLogger().log(Level.INFO, "INPUT: " + sequencer.getName() + " is Ready");

    // System.out.println("I, " + sequencer.getName() + " is Ready");

    if (marshallingArea.getReceivedFasciaSetSizeCount() > 0
        && marshallingArea.getReceivedRequestSizeCount() > 0) {
      sequencer.addUnorderedFascias(marshallingArea);
      // System.out.println("There is a set of Unordered Fascias added to " + sequencer.getName()
      // + "'s unordered Fascia set from marshalling area. Now, there are " +
      // marshallingArea.getReceivedFasciaSetSizeCount() + " unordered fascia set in received
      // Request.");

      WareHouseManager.getLogger().log(Level.INFO,
          "Note: There is a set of Unordered Fascias added to " + sequencer.getName()
              + "'s unordered Fascia set from marshalling area. Now, there are "
              + marshallingArea.getReceivedFasciaSetSizeCount()
              + " unordered fascia set in received Request.");


      sequencer.addRequest(marshallingArea);
    }

    WareHouseManager.getLogger().log(Level.INFO,
        "NOTE: There is a picking request added to " + sequencer.getName()
            + "'s requestlist from marshalling area. Now, there are "
            + marshallingArea.getReceivedRequestSizeCount() + " requests in received Request.");



    sequencerList.add(sequencer);
    System.out.println("now, sequencerlist's size is: " + sequencerList.size());
  }



  public void sequencerSequence(Sequencer sequencer,
      PickingRequestProcessor pickingRequestProcessor, String sku) {
    sequencer.setState(false);


    WareHouseManager.getLogger().log(Level.INFO,
        "OUTPUT: " + sequencer.getName() + " starts sequencing.");
    sequencer.sequencing(sku);

    System.out.println("skus correct here?");
    if (sequencer.checkPickerWork() != true) {
      WareHouseManager.getLogger().log(Level.SEVERE,
          "OUTPUT: " + "Sku not correct. Repick!" + sequencer.checkPickerWork());
      sequencer.discard(pickingRequestProcessor);
      sequencer.setState(true);
    }

    System.out.println("Damaged, why???? " + sequencer.isDamage());
    System.out.println("isdamaged here?");
    if (sequencer.isDamage() == true) {
      System.out.println(" is damaged. " + sequencer.isDamage());
      WareHouseManager.getLogger().log(Level.SEVERE,
          "OUTPUT: " + "Damaged. Repick!" + sequencer.isDamage());
      sequencer.discard(pickingRequestProcessor);
      sequencer.setState(true);
    }
    System.out.println("is Vasually correct here?");
    if (sequencer.isVisuallyCorrect() != true) {
      WareHouseManager.getLogger().log(Level.SEVERE,
          "OUTPUT: " + "Visually not correct. Repick!" + sequencer.isVisuallyCorrect());
      System.out.println("visually not correct. " + sequencer.isVisuallyCorrect());
      sequencer.discard(pickingRequestProcessor);
      sequencer.setState(true);
    }

    // System.out.println("after checking here ?");
    // Did not go this way.
    WareHouseManager.getLogger().log(Level.INFO,
        "NOTE: " + sequencer.getName() + " finished sequencing.");
  }


  public void sequencerRescan(Sequencer sequencer,
      PickingRequestProcessor pickingRequestProcessor) {

    WareHouseManager.getLogger().log(Level.SEVERE,
        "INPUT: " + sequencer.getName() + " is Rescanning");
    if ((sequencer.barcodeReader()) != true) {
      WareHouseManager.getLogger().log(Level.SEVERE,
          "OUTPUT: " + "barcode not correct. Repick!" + sequencer.barcodeReader());
      // discard
      sequencer.discard(pickingRequestProcessor);
      // re-add picking request to picking request processor.
      sequencer.setState(true);
    }


  }

  public void sequencerFinish(Sequencer sequencer, MarshallingArea marshallingArea) {

    sequencer.toMarshallingArea(marshallingArea);

    WareHouseManager.getLogger().log(Level.INFO,
        "INPUT: " + sequencer.getName() + " sent Fascias and Picking Requests for loading.");

    System.out.println("Ready Pallet size is: " + marshallingArea.getReadyPalletSizeCount());
    System.out
        .println("Ready Pallets are: " + marshallingArea.getReadyPalletNotRemove().toStringList());
    System.out.println("Ready Request size is: " + marshallingArea.getReadyRequestSizeCount());
    System.out.println(
        "Ready Picking Requests are: " + marshallingArea.getReadyPalletNotRemove().toStringList());
  }

  public void loaderReady(Loader loader, MarshallingArea marshallingArea) {
    loader.setState(true);
    WareHouseManager.getLogger().log(Level.INFO, "INPUT: " + loader.getName() + " is Ready");

    // System.out.println("I, " + loader.getName() + " is Ready");

    System.out.println("getReadyPalletSizeCount is: " + marshallingArea.getReadyPalletSizeCount());
    if (marshallingArea.getReadyPalletSizeCount() > 0
        && marshallingArea.getReadyRequestSizeCount() > 0) {


      loader.setPalletOnHold(marshallingArea);

      WareHouseManager.getLogger().log(Level.INFO,
          "NOTE: " + loader.getName() + " gets pallets from marshalling area.");

      loader.addRequest(marshallingArea);
      WareHouseManager.getLogger().log(Level.INFO,
          "NOTE: " + loader.getName() + " gets picking request from marshalling area.");

    }
    loaderList.add(loader);
    System.out.println("loaderlist's size is: " + loaderList.size());
  }

  public void loaderLoad(Loader loader, PickingRequestProcessor pickingRequestProcessor,
      FinishRequestProcessor finishRequestProcessor, ArrayList<Truck> truckList) {
    loader.setState(false);
    WareHouseManager.getLogger().log(Level.INFO, "INPUT: " + loader.getName() + " starts loading.");
    System.out.println(loader.getName() + " starts loading.");

    if (truckList.size() == 0) {
      Truck truck = new Truck();
      truckList.add(truck);
    }

    System.out.println("isdamaged here?");
    System.out.println("PalletOnHold is " + loader.getPalletOnhold());

    if (loader.isDamage() == true) {
      // System.out.println(" is damaged. " + loader.isDamage());
      WareHouseManager.getLogger().log(Level.SEVERE,
          "OUTPUT: " + "Damaged. Repick!" + loader.isDamage());
      loader.discard(pickingRequestProcessor);
      loader.setState(true);

    }


    System.out.println("is Vasually correct here?");
    if (loader.isVisuallyCorrect() != true) {
      WareHouseManager.getLogger().log(Level.SEVERE,
          "OUTPUT: " + "Visually not correct. Repick!" + loader.isVisuallyCorrect());
      // System.out.println("visually not correct. " + loader.isVisuallyCorrect());
      loader.discard(pickingRequestProcessor);
      loader.setState(true);
    }

    System.out.println("barcode here?");

    if ((loader.barcodeReader()) != true) {
      WareHouseManager.getLogger().log(Level.SEVERE,
          "OUTPUT: " + "barcode not correct. Repick!" + loader.barcodeReader());
      // discard
      loader.discard(pickingRequestProcessor);
      // re-add picking request to picking request processor.
      loader.setState(true);
    }

    // System.out.println("after checking here ?");
    // Did not go this way.
    loader.loading(truckList.get(0), finishRequestProcessor);
    WareHouseManager.getLogger().log(Level.INFO,
        "OUTPUT: " + loader.getName() + " finished loading.");
    System.out.println("truck size is: " + truckList.get(0).getLeftLoadedPallet().size()
        + truckList.get(0).getRightLoadedPallet().size());

    System.out.println("Finished processor size is: "
        + finishRequestProcessor.getFinishedRequestProcessor().size());
    // System.out.println("Ready Request size is: "+marshallingArea.getReadyRequestSizeCount());
    // System.out.println("Ready Picking Requests are: " +
    // marshallingArea.getReadyPalletNotRemove().toStringList());
    //
  }

  public void loaderRescan(Loader loader, PickingRequestProcessor pickingRequestProcessor) {
    if ((loader.barcodeReader()) != true) {
      WareHouseManager.getLogger().log(Level.SEVERE,
          "OUTPUT: " + "barcode not correct. Repick!" + loader.barcodeReader());
      // discard
      loader.discard(pickingRequestProcessor);
      loader.setState(true);
    }

  }

  public FinishRequestProcessor getFinishRequestProcessor() {
    return finishRequestProcessor;
  }

  public void setLoaderList(ArrayList<Loader> loaderList) {
    this.loaderList = loaderList;
  }

  public void setSequencerList(ArrayList<Sequencer> sequencerList) {
    this.sequencerList = sequencerList;
  }

  public void setPickerList(ArrayList<Picker> pickerList) {
    this.pickerList = pickerList;
  }

  public void setReplenisherList(ArrayList<Replenisher> replenisherList) {
    this.replenisherList = replenisherList;
  }


}
