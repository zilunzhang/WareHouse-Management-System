package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import processor.FinishRequestProcessor;
import util.Location;
import util.WareHouse;

public class OutputFileWriter {
  private FileWriter fileWriter1;
  private FileWriter fileWriter2;
  private WareHouse wareHouse;
  private FinishRequestProcessor finishedRequestProcessor;

  public OutputFileWriter(WareHouse wareHouse, FinishRequestProcessor finishedRequestProcessor)
      throws IOException {
    fileWriter1 = new FileWriter(new File("final.csv"));
    fileWriter2 = new FileWriter(new File("orders.csv"));
    this.wareHouse = wareHouse;
    this.finishedRequestProcessor = finishedRequestProcessor;
    System.out.println(finishedRequestProcessor.getContents().toString());
  }



  public void writeCsvFile() {
    for (Location loc : wareHouse.getLocationList()) {
      if (loc.getFasciaManager().getAmount() == 30) {
        continue;
      }
      try {
        fileWriter1.append(loc.getZone());

        fileWriter1.append(',');
        fileWriter1.append(loc.getAisle());
        fileWriter1.append(',');
        fileWriter1.append(loc.getRack());
        fileWriter1.append(',');
        fileWriter1.append(loc.getLevel());
        fileWriter1.append(',');
        fileWriter1.append(Integer.toString(loc.getFasciaManager().getAmount()));
        fileWriter1.append('\n');
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    try {
      fileWriter1.flush();
      fileWriter1.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Read me to shorten the debug time.
    // Next time when you start debugging, shart from here!
    // Hint: check here, the finishRequestProcessor is empty,
    // so nothing is written on the orders.csv
    // System.out.print(finishedRequestProcessor.getContents() + " ooooooooooooooo");

  }


  public void writeOrderFile() {
    try {
      fileWriter2.append("Model");
      fileWriter2.append(',');
      fileWriter2.append("Colour");
      fileWriter2.append(',');
      fileWriter2.append("SKU (front)");
      fileWriter2.append(',');
      fileWriter2.append("SKU (back)");
      fileWriter2.append('\n');
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    for (ArrayList<String> contentInfo : finishedRequestProcessor.getContents()) {


      try {
        fileWriter2.append(contentInfo.get(0));
        fileWriter2.append(',');
        fileWriter2.append(contentInfo.get(1));
        fileWriter2.append(',');
        fileWriter2.append(contentInfo.get(2));
        fileWriter2.append(',');
        fileWriter2.append(contentInfo.get(3));
        fileWriter2.append('\n');



      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
    try {
      fileWriter2.flush();
      fileWriter2.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
