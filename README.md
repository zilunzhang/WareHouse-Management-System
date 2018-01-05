# WareHouse-Management-System
WareHouse Management System worked with Sheldon Huang, Brain Hu and David Ouyang  

How to use it:


  - Our Main Class is named “Company.java”, which is in model package. You should be able to run it directly.


  - In our main method (Company.java), we input the relative path of translation.csv, traversal_table.csv and initial.csv in order at line     41 to instantiated. Then we input the 16order.txt(using relative path too) file when we instantiate ReadEvent Class at line 58 of         Company.java. If you cannot read the file, please change them into absolutely path.
 

  - You can run the unit test in our test package.


Program explanation:

Our program can be divided into some part. 

  - 1. The first part read translation.csv, traversal_table.csv and initial.csv and generate warehouse and reserve room from file it read. 

  The warehouse generation mainly includes 3 step. 

  The warehouse is actually an ArrayList of “Location”. 
  The “Location” class include “Fascia” attribute, “amount” attribute and “zone”, “aisle”, “rack” and “level” attributes. 
  The “Fascia” class include “isDamage”, “model”, “colour” and “sku” attributes.

  In the first step, we read the translation.csv build an ArrayList of Fascia according to colour, model and sku (in order).

  In the second step, we read the traversal_table.csv, build up an arrayList of “Location” in order according to location information       (“zone”, “aisle”, “rack” and “level”), find Fascia through sku and put the Fascia into the corresponding Location Object. Note that, we   set the initial amount of each location’s amount to be 30.

  In the third step, we read the initial.csv, use location information to locate Fascia and update the amount of that location.

  Until now, we have connected all the information of Location, Fascia, colour, model, sku and amount. We can easily find Fascia, location   or sku if we have one of them, because they are pairwise injection according to our construction.

  Reserve room is another ArrayList of “Location”, corresponding to the location and Fascia in wareHouse, we build reserve room in           WareHouse class too, however, it’s default amount is 40, and always auto-refill.

  - 2. When an order came in through fax, we make each order to be an Order object, this object includes sku, colour and model information     and we put Orders into order processor to store them. 

  - 3. Once order processor has 4 orders, we make 4 orders to be an PickingRequest object (ArrayList of 4 orders), and send PickingRequest     to PickingRequest processor.


  - 4. When a picker is ready, we let that picker check if the PickingRequest processor is empty, if not, the picker input the picking         request to generic software and pick. Picker will check if the amount of the fascia is less than 5 after each pick, if it is, call         replenisher to refill.


  - 5. Once a picker finished picking, the picker send an Arraylist of Fascias(8) and an PickingRequest to marshallingArea’s                  receivedProduct (it is a queue) and receivedRequest (it is a queue, too.) for sequencing.


  - 6. The marshallingArea consists of 4 queues: receivedProduct(queue), receivedRequest(queue), readyPallet(queue) and                       readyRequest(queue). You can think those four queue as 4 processors. The first two queues are transfer stations that picker gives         Fascias to sequencer, once sequencer is ready, sequencer starts sequencing. Sequencer will put pack of Facias after sequencing into       readyPallet(queue) and transfer the PickingRequest to readyRequest(queue) for next processing.


  - 7. Once a Loader ready, loader check the readyPallet(queue) and readyRequest(queue), if they are not empty, loader starts loading,         which is that put groups of Fascias into to truck.


  - 8. Once all of Facias in a picking request is loaded, this picking request is moved to finisher request processor. The finish request     processor will print the orders information into csv “final” in main method once the truck is full and leaving.


  - 9. Note that both order processor, picking request processor, receivedProduct(queue), receivedRequest(queue), readyPallet(queue),         readyRequest(queue) and finish request processor are linked lists data structure (queues). they are all first in first out data           structure.


  - 10. If the disorder situation was detected by sequencer and loader after sequencing, we discard all fascias in this picking request       and repick. In the case, we use “addfirst” method in picking request processor, that is add the picking request to the first position     of picking request processor for reprocessing. Sequencer also checks damage of each fascias in each picking request and do the same as     disorder Fascias.


  - 11. The Company.java is our main, and ReadEvents.java, ReadFile.java are just helper classes.



  - 12. Note that we designed “Fascia” to be a subclass of “Product”, “WareHouse” is observable and replenisher is observer and there is a     nearly empty class “loading room”. All of this is for the convenience of phase two, so we keep them.

  - 13. Our UML is auto-generated by eclipse, each package has a independent uml diagram group extended name with .ucls in the end.


  - 14. In project folder:

    the 16 orders is the orders as a script, we run our main method depend on it. 

    duties describe each teammate’s duty.

    help.txt is this file.

    initial.csv, translation.csv and traversal_table.csv are data for building up warehouse.

    final.csv in the project file is useless, we just don’t have time to delete it, please ignore it.



   - 15. In src folder:

     src folder is our source code folder, when you run our code, there will be a new final.csv and new orders.csv be genarated.
 
