package manager;

import exception.SkuNotExistException;
import java.io.IOException;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import util.Fascia;
import util.Location;
import util.WareHouse;

public class WareHouseManager {
  private WareHouse warehouse;
  private static final Logger logger = Logger.getLogger(WareHouseManager.class.getName());
  private static final Handler consoleHandler = new ConsoleHandler();
  private static Handler fileHandler;
  private static String filePath = "../log.txt";


  /** Given the warehouse, construct the warehouse manager. */
  public WareHouseManager(WareHouse warehouse) {
    this.warehouse = warehouse;
    try {
      fileHandler = new FileHandler(filePath);
    } catch (SecurityException | IOException e1) {
      e1.printStackTrace();
    }

    logger.setLevel(Level.ALL);
    consoleHandler.setLevel(Level.SEVERE);
    fileHandler.setLevel(Level.ALL);
    SimpleFormatter formatter = new SimpleFormatter();
    fileHandler.setFormatter(formatter);
    logger.addHandler(consoleHandler);
    logger.addHandler(fileHandler);
  }

  /**
   * Given a sku find the corresponding location. Throws SkuNotExistException if there is no such
   * sku exist.
   * 
   */
  public Location getLocationFromSku(String sku) throws SkuNotExistException {
    for (Location location : warehouse.getLocationList()) {
      if (location.getFasciaManager().getFasciaSku().equals(sku)) {
        return location;
      }
    }
    logger.log(Level.SEVERE, "Input Sku Not Exist");
    throw new SkuNotExistException("Sku Not Exist");
  }


  /**
   * Given sku, find the location that stores the fascia has the sku.
   */
  public String getSkuFromLocation(Location location) {
    for (Location tempLocation : warehouse.getLocationList()) {
      if (tempLocation.equals(location)) {
        return tempLocation.getFasciaManager().getFasciaSku();
      }
    }
    return null;
  }

  /**
   * Given colour and model, find the corresponding sku.
   */
  public String getSkuFromInfo(String color, String model, boolean bool)
      throws SkuNotExistException {
    for (Location tempLocation : warehouse.getLocationList()) {
      if (tempLocation.getFasciaManager().getFasciaColour().equals(color)
          && tempLocation.getFasciaManager().getFasciaModel().equals(model)
          && tempLocation.getFasciaManager().getFrontInfo() == bool) {
        return tempLocation.getFasciaManager().getFasciaSku();
      }
    }
    logger.log(Level.SEVERE, "Cannot find sku from given info.");
    throw new SkuNotExistException("Cannot find sku from given info.");
  }

  /** Given sku, find the corresponding color and model. */
  public String getInfoFromSku(String sku) {
    Fascia fascia = null;
    try {
      fascia = getFasciaFromSku(sku);
    } catch (SkuNotExistException except) {
      // TODO Auto-generated catch block
      except.printStackTrace();
    }
    return "Color is: " + fascia.getColour() + ", Model is: " + fascia.getModel() + ", is Front: "
        + fascia.isFront();
  }


  public String getColorFromSku(String sku) throws SkuNotExistException {
    return this.getFasciaFromSku(sku).getColour();
  }

  public String getModelFromSku(String sku) throws SkuNotExistException {
    return this.getFasciaFromSku(sku).getModel();
  }

  public Fascia getFasciaFromLocation(Location location) {
    return location.getFasciaManager().getFascia();
  }

  public Fascia getFasciaFromSku(String sku) throws SkuNotExistException {
    return getLocationFromSku(sku).getFasciaManager().getFascia();
  }

  public static Logger getLogger() {
    return logger;
  }

  public static Handler getConsolehandler() {
    return consoleHandler;
  }

  public static Handler getFileHandler() {
    return fileHandler;
  }
}
