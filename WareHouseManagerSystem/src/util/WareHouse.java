package util;

import exception.EmptyBlankInFileException;
import exception.SkuNotExistException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import manager.ReplenisherManager;
import manager.WareHouseManager;
import model.FileReader;


public class WareHouse {

  private ArrayList<Location> locationList;
  private ArrayList<Location> reserveRoom;
  private WareHouseManager wareHouseManager;
  private ReplenisherManager replenisherManager;

  /** Given three input files construct a wareHouse.*/
  public WareHouse(String translationTablePath, String traverseTablePath, String initialPath)
      throws FileNotFoundException, SkuNotExistException, NumberFormatException,
      EmptyBlankInFileException {
    buildWareHouse(translationTablePath, traverseTablePath, initialPath);
    buildReserveRoom(translationTablePath, traverseTablePath, initialPath);
    setWareHouseManager(new WareHouseManager(this));

  }

  private void buildWareHouse(String translationTablePath, String traverseTablePath,
      String initialPath) throws FileNotFoundException, SkuNotExistException, NumberFormatException,
      EmptyBlankInFileException {

    ArrayList<Fascia> inputFaList = readTranslationTable(translationTablePath);
    ArrayList<Location> inputLocationList = readTraverseTable(traverseTablePath, inputFaList);
    ArrayList<Location> finalLocationList = readInitial(initialPath, inputLocationList);
    locationList = finalLocationList;

  }

  private void buildReserveRoom(String translationTablePath, String traverseTablePath,
      String initialPath) throws FileNotFoundException, NumberFormatException, SkuNotExistException,
      EmptyBlankInFileException {
    ArrayList<Fascia> inputFaList = readTranslationTable(translationTablePath);
    ArrayList<Location> inputLocationList = readTraverseTable(traverseTablePath, inputFaList);
    ArrayList<Location> finalReserveRoomLocationList = readInitial(initialPath, inputLocationList);
    setReserveRoom(finalReserveRoomLocationList);
    for (Location location : reserveRoom) {
      location.getFasciaManager().setAmount(40);
    }
  }

  public ArrayList<Location> getLocationList() {
    return locationList;
  }
  
  /**set the ReplenisherManage of all location in the locationList. */
  public void setAllReplenisherManager(ReplenisherManager replenisherManager) {
    for (Location location : locationList) {
      location.setReplenisherManager(replenisherManager);
    }
  }

  /**
   * ReadTranslationTable Build relationship from color model to sku. 
   * Build Fascia Fascia list with color, model and sku.
   */
  public ArrayList<Fascia> readTranslationTable(String translationTablePath)
      throws FileNotFoundException {

    ArrayList<String> skuList = new ArrayList<String>();
    ArrayList<String> modelList = new ArrayList<String>();
    ArrayList<String> colourList = new ArrayList<String>();
    ArrayList<Boolean> frontBackList = new ArrayList<Boolean>();
    ArrayList<Fascia> faList = new ArrayList<Fascia>();

    File file = new File(translationTablePath);

    Scanner scanner = new Scanner(file);

    int a1 = 0;
    while (scanner.hasNext()) {

      List<String> line = FileReader.parseLine(scanner.nextLine());

      a1 += 1;

      if (a1 != 1) {
        String[] info1 = new String[4];

        String[] info2 = new String[4];

        // info color
        info1[0] = line.get(0);
        colourList.add(info1[0]);

        // info model
        info1[1] = line.get(1);
        modelList.add(info1[1]);

        // info front
        info1[2] = "true";
        frontBackList.add(Boolean.parseBoolean(info1[2]));

        // info sku
        info1[3] = line.get(2);
        skuList.add(info1[3]);

        // info color
        info2[0] = line.get(0);
        colourList.add(info2[0]);

        // info model
        info2[1] = line.get(1);
        modelList.add(info2[1]);

        // info back
        info2[2] = "false";
        frontBackList.add(Boolean.parseBoolean(info2[2]));

        // info sku
        info2[3] = line.get(3);
        skuList.add(info2[3]);


      }

    }
    scanner.close();


    for (int i = 0; i < Math.min(Math.min(skuList.size(), modelList.size()),
        Math.min(colourList.size(), frontBackList.size())); i++) {
      Fascia fa =
          new Fascia(colourList.get(i), modelList.get(i), skuList.get(i), frontBackList.get(i));
      faList.add(fa);
    }

    return faList;
  }

  /** ReadTranslationTable put Fascia List into location list. */
  public ArrayList<Location> readTraverseTable(String traverseTablePath,
      ArrayList<Fascia> inputFaList) throws FileNotFoundException, SkuNotExistException {

    ArrayList<String> zoneList = new ArrayList<String>();
    ArrayList<String> asileList = new ArrayList<String>();
    ArrayList<String> rackList = new ArrayList<String>();
    ArrayList<String> levelList = new ArrayList<String>();
    ArrayList<Location> locationList = new ArrayList<Location>();

    File file = new File(traverseTablePath);
    Scanner scanner = new Scanner(file);
    while (scanner.hasNext()) {
      List<String> line = FileReader.parseLine(scanner.nextLine());

      // check valid input

      String zone = line.get(0);
      zoneList.add(zone);


      String asile = line.get(1);
      asileList.add(asile);

      String rack = line.get(2);
      rackList.add(rack);

      String level = line.get(3);
      levelList.add(level);

    }
    scanner.close();

    for (int i = 0; i < Math.min(Math.min(zoneList.size(), asileList.size()),
        Math.min(rackList.size(), levelList.size())); i++) {

      Location lo = new Location(inputFaList.get(i));

      lo.setZone(zoneList.get(i));
      lo.setAisle(asileList.get(i));
      lo.setRack(rackList.get(i));
      lo.setLevel(levelList.get(i));
      locationList.add(lo);
    }
    return locationList;

  }

  /** Read Initial, put amount into locationList. */
  public ArrayList<Location> readInitial(String initailPath, ArrayList<Location> locationList)
      throws FileNotFoundException, NumberFormatException, EmptyBlankInFileException {

    ArrayList<ArrayList<String>> finalInfoList = new ArrayList<ArrayList<String>>();
    File file = new File(initailPath);
    @SuppressWarnings("resource")
    Scanner scanner = new Scanner(file);
    while (scanner.hasNext()) {
      List<String> line = FileReader.parseLine(scanner.nextLine());
      for (String string : line) {
        if (string.equals("")) {
          throw new EmptyBlankInFileException("there is an empty blank in file, check!");
        }
      }

      ArrayList<String> infoList = new ArrayList<String>();

      String zone = line.get(0);
      infoList.add(zone);

      String asile = line.get(1);
      infoList.add(asile);

      String rack = line.get(2);
      infoList.add(rack);

      String level = line.get(3);
      infoList.add(level);

      String amount = line.get(4);
      infoList.add(amount);

      finalInfoList.add(infoList);
    }
    scanner.close();


    for (ArrayList<String> finalInfo : finalInfoList) {
      for (Location location : locationList) {
        if (finalInfo.get(0).equals(location.getZone())
            && finalInfo.get(1).equals(location.getAisle())
            && finalInfo.get(2).equals(location.getRack())
            && finalInfo.get(3).equals(location.getLevel())) {
          location.getFasciaManager().setAmount(Integer.parseInt(finalInfo.get(4)));
        }
      }
    }
    return locationList;
  }

  public ArrayList<Location> getReserveRoom() {
    return reserveRoom;
  }

  public void setReserveRoom(ArrayList<Location> reserveRoom) {
    this.reserveRoom = reserveRoom;
  }

  public WareHouseManager getWareHouseManager() {
    return wareHouseManager;
  }

  public void setWareHouseManager(WareHouseManager wareHouseManager) {
    this.wareHouseManager = wareHouseManager;
  }

  public void setReplenisherManager(ReplenisherManager replenisherManager) {
    this.replenisherManager = replenisherManager;
  }

  public ReplenisherManager getReplenisherManager() {
    return replenisherManager;
  }

}
