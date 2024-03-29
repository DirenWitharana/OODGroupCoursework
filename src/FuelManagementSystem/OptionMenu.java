package FuelManagementSystem;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import static java.lang.System.*;

public class OptionMenu {
    DBConnector dbConnector=new DBConnector();
    Scanner sc = new Scanner(in);



    public void MainMenuDisplay() throws SQLException, ClassNotFoundException {
        boolean userIntReceived = false;
        int Choice;

        out.format("%n%n****************************************************************************%n");
        out.format("                             MAIN MENU                                %n");
        out.format("+***************************************************************************%n");
        out.format("%n%n       1. MANAGE CUSTOMERS                                              %n");
        out.format("       2. MANAGE QUEUES                                     %n");
        out.format("       3. MANAGE DISPENSERS                            %n");
        out.format("       4. VIEW STATS                                                      %n");
        out.format("       0. EXIT                                                            %n");

        out.println();
        while (!userIntReceived) {
            try {
                out.print("PLEASE SELECT YOUR OPTION : ");
                Choice = Integer.parseInt(sc.nextLine());
                userIntReceived = true;
                switch (Choice) {
                    case 1 -> manageCustomers();
                    case 2 -> manageQueues();
                    case 3 -> manageDispensers();
                    case 4 -> {
                        out.println("\n*********|| Stats ||**********\n");
                        double Total_Petrol_sales=0;
                        double Total_Diesel_sales=0;

                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fuelmanager", "user", "123");
                        Statement stmt = con.createStatement();

                        ResultSet rs=stmt.executeQuery("SELECT SUM(payment) FROM `customer` WHERE fuel_Type=\"petrol\" ");
                        while (rs.next()){
                            Total_Petrol_sales=rs.getDouble(1);
                        }
                        ResultSet rs2=stmt.executeQuery("SELECT SUM(payment) FROM `customer` WHERE fuel_Type=\"diesel\" ");
                        while (rs2.next()){
                            Total_Diesel_sales=rs2.getDouble(1);
                        }



                        out.println("Total Petrol Sales = "+Total_Petrol_sales+"\nTotal DieselSales = "+Total_Diesel_sales);



                    }
                    case 0 -> exit(0);

                    default -> MainMenuDisplay();
                }
            } catch (Exception e) {
                out.println("Error");}
        }

    }
    public void viewStats()  {


    }


    public void manageCustomers() {
        boolean userIntReceived = false;
        int Choice;

        out.format("%n%n****************************************************************************%n");
        out.format("                             MANAGE CUSTOMER MENU                                %n");
        out.format("+***************************************************************************%n");
        out.format("%n%n       1. ENTER CUSTOMER DETAILS                                           %n");
        out.format("       2. VIEW CUSTOMER DETAILS                                               %n");
        out.format("       0. EXIT TO MAIN MENU                                                            %n");


        out.println();
        while (!userIntReceived) {
            try {
                out.print("PLEASE SELECT YOUR OPTION : ");
                Choice = Integer.parseInt(sc.nextLine());
                userIntReceived = true;
                switch (Choice) {
                    case 1 -> {
                        out.println("\n------ CUSTOMER DETAILS ENTRY ------\n");

                        out.println("ENTER FUEL TYPE [Petrol / Diesel] ");
                        String fuelType = sc.next().toLowerCase();

                        out.print("ENTER FUEL AMOUNT: ");
                        double fuelInput = sc.nextDouble();

                        out.print("ENTER VEHICLE TYPE: ");
                        String vehicleType = sc.next().toLowerCase();

                        out.print("ENTER CUSTOMER NAME: ");
                        String customerName = sc.next().toLowerCase();

                        Customer customer = new Customer(fuelType, fuelInput, false, vehicleType, customerName, null);

                        dbConnector.AddCusToDB(customer);

                        manageCustomers();

                    }
                    case 2 -> {
                        out.println("--------CUSTOMER DETAILS----------\n\n\n");

                        dbConnector.ViewCusFromDB();
                    }
                    case 0 -> MainMenuDisplay();
                    default -> manageCustomers();
                }
            } catch (Exception e) {
                out.println("Error");}
        }

        }
        public void QueueSelect() throws SQLException, ClassNotFoundException {
            boolean userIntReceived = false;
            int Choice;


            out.format("%n%n****************************************************************************%n");
            out.format("                             Queue List                                %n");
            out.format("+***************************************************************************%n");
            dbConnector.ViewDispensers();


            out.println();

        }
    public void manageQueues() {
        boolean userIntReceived = false;
        int Choice;

        out.format("%n%n****************************************************************************%n");
        out.format("                             MANAGE QUEUE MENU                                %n");
        out.format("+***************************************************************************%n");
        out.format("%n%n       1. ADD                                            %n");
        out.format("       2. REMOVE                                                %n");
        out.format("       3. VIEW QUEUES                                                %n");
        out.format("       0. EXIT TO MAIN MENU                                                            %n");


        out.println();
        while (!userIntReceived) {
            try {
                boolean user2IntReceived = false;
                int Choice2;
                out.print("PLEASE SELECT YOUR OPTION : ");
                Choice = Integer.parseInt(sc.nextLine());
                userIntReceived = true;
                switch (Choice) {
                    case 1 ->{
                        out.println("\nADD VEHICLE TO QUEUE\n");
                        Customer customer;
                        customer=dbConnector.getLastCustomerFromDB();
                        String Type= customer.getVehicleType();
                        if (customer.getFuelType().equals("petrol")){


                            if (Type.equals("car") || Type.equals("van")){
                                Queue queue= new Queue();
                                queue.Enqueue(customer);



                            } else if (Type.equals("motorbike")) {
                                Queue queue= new Queue();
                                queue.Enqueue(customer);

                            } else if (Type.equals("threewheel")) {
                                Queue queue= new Queue();
                                queue.Enqueue(customer);

                            }else {
                                Queue queue= new Queue();
                                queue.Enqueue(customer);

                            }


                        } else if (customer.getFuelType().equals("diesel")) {

                            if (Type.equals("car") || Type.equals("van")){
                                Queue queue= new Queue();
                                queue.Enqueue(customer);

                            } else if (Type.equals("publictransport")) {
                                Queue queue= new Queue();
                                queue.Enqueue(customer);

                            }else {
                                Queue queue= new Queue();
                                queue.Enqueue(customer);

                            }
                        }
                        else{
                            out.println("please Enter Again");
                            manageDispensers();
                        }
                        MainMenuDisplay();


                    }
                    case 2 -> {
                        out.println("REMOVE VEHICLE FROM QUEUE\n");
                        user2IntReceived = false;

                        Queue queue= new Queue();
                        dbConnector.ViewDispensers();

                        while (!user2IntReceived) {
                            try {
                                out.print("PLEASE SELECT YOUR OPTION : ");
                                Choice2 = Integer.parseInt(sc.nextLine());
                                user2IntReceived = true;
                                switch (Choice2) {

                                    case 1 -> {
                                        if (dbConnector.getQueueCountFromDB("car","petrol")==0){
                                            out.println("Queue empty");
                                        }else {
                                            queue.Dequeue("pqueue_carandvan");
                                        }
                                    }
                                    case 2 -> {
                                        if (dbConnector.getQueueCountFromDB("motorbike","petrol")==0){
                                            out.println("Queue empty");
                                        }else {
                                            queue.Dequeue("pqueue_bike");
                                        }

                                    }
                                    case 3 ->{
                                        if (dbConnector.getQueueCountFromDB("threewheel","petrol")==0){
                                            out.println("Queue empty");
                                        }else {
                                            queue.Dequeue("pqueue_threewheel");
                                        }

                                    }
                                    case 4 ->{
                                        if (dbConnector.getQueueCountFromDB("other","petrol")==0){
                                            out.println("Queue empty");
                                        }else {
                                            queue.Dequeue("pqueue_other");
                                        }

                                    }
                                    case 5 ->{
                                        if (dbConnector.getQueueCountFromDB("car","diesel")==0){
                                            out.println("Queue empty");
                                        }else {
                                            queue.Dequeue("dqueue_carandvan");
                                        }

                                    }
                                    case 6 ->{
                                        if (dbConnector.getQueueCountFromDB("publictransport","diesel")==0){
                                            out.println("Queue empty");
                                        }else {
                                            queue.Dequeue("dqueue_public");
                                        }

                                    }
                                    case 7 ->{
                                        if (dbConnector.getQueueCountFromDB("other","diesel")==0){
                                            out.println("Queue empty");
                                        }else {
                                            queue.Dequeue("dqueue_other");
                                        }

                                    }

                                    case 0 -> MainMenuDisplay();
                                    default -> manageCustomers();
                                }
                            } catch (Exception e) {
                                out.println("Error");}
                        }
                        manageQueues();


                    }
                    case 3 -> {
                        user2IntReceived = false;

                        out.println("DISPLAY VEHICLES IN QUEUE");

                        QueueSelect();
                        while (!user2IntReceived) {
                            try {
                                out.print("PLEASE SELECT YOUR OPTION : ");
                                Choice2 = Integer.parseInt(sc.nextLine());
                                userIntReceived = true;
                                switch (Choice2) {
                                    //has to complete...................
                                    case 1 -> {
                                        dbConnector.Viewtables("pqueue_carandvan");
                                        manageQueues();
                                    }
                                    case 2 -> {

                                        dbConnector.Viewtables("pqueue_bike");
                                        manageQueues();

                                    }
                                    case 3 ->{
                                        dbConnector.Viewtables("pqueue_threewheel");
                                        manageQueues();

                                    }
                                    case 4 ->{
                                        dbConnector.Viewtables("pqueue_other");
                                        manageQueues();

                                    }
                                    case 5 ->{

                                        dbConnector.Viewtables("dqueue_carandvan");
                                        manageQueues();

                                    }
                                    case 6 ->{
                                        dbConnector.Viewtables("dqueue_public");
                                        manageQueues();

                                    }
                                    case 7 ->{
                                        dbConnector.Viewtables("dqueue_other");
                                        manageQueues();


                                    }

                                    case 0 -> MainMenuDisplay();
                                    default -> manageQueues();
                                }
                            } catch (Exception e) {
                                out.println("Error");}
                        }

                    }
                    case 0 -> MainMenuDisplay();
                    default -> manageQueues();
                }
            } catch (NumberFormatException e) {
                out.println("Error");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void manageDispensers() {
        boolean userIntReceived = false;
        int Choice;

        out.format("%n%n****************************************************************************%n");
        out.format("                             MANAGE DISPENSER MENU                                %n");
        out.format("+***************************************************************************%n");
        out.format("%n%n       1. ADD NEW DISPENSER                                           %n");
        out.format("       2. CHECK FUEL LEVEL                                                %n");
        out.format("       3. RESTOCK FUEL                                                 %n");
        out.format("       0. EXIT TO MAIN MENU                                                            %n");


        out.println();
        while (!userIntReceived) {
            try {
                out.print("PLEASE SELECT YOUR OPTION : ");
                Choice = Integer.parseInt(sc.nextLine());
                userIntReceived = true;
                switch (Choice) {
                    case 1 ->{ out.println("\n*******************\n");


                        out.println("ENTER FUEL TYPE FOR DISPENSER [Petrol / Diesel] ");
                        String fuelType = sc.next().toLowerCase();

                        out.println(fuelType);

                        if (fuelType.equals("petrol")){
                            System.out.print("ENTER DISPENSER TYPE [CAR/VAN, MORTORBIKE, THREEWHEEL, OTHER] : ");
                            String Type = sc.next().toLowerCase();


                            if (Type.equals("car") || Type.equals("van")){
                                PetrolDispenser PD= new PetrolDispenser(Type);
                                dbConnector.AddPetrolDispenserToDB(PD,Type);



                            } else if (Type.equals("motorbike")) {
                                PetrolDispenser PD= new PetrolDispenser(Type);
                                dbConnector.AddPetrolDispenserToDB(PD,Type);

                            } else if (Type.equals("threewheel")) {
                                PetrolDispenser PD= new PetrolDispenser(Type);
                                dbConnector.AddPetrolDispenserToDB(PD,Type);

                            }else {
                                PetrolDispenser PD= new PetrolDispenser(Type);
                                dbConnector.AddPetrolDispenserToDB(PD,Type);

                            }


                        } else if (fuelType.equals("diesel")) {
                            out.print("ENTER DISPENSER TYPE [CAR/VAN, PUBLIC TRANSPORT, OTHER] : ");
                            String Type = sc.next().toLowerCase();


                            if (Type.equals("car") || Type.equals("van")){
                                DieselDispenser DD= new DieselDispenser(Type);
                                dbConnector.AddDieselDispenserToDB(DD,Type);

                            } else if (Type.equals("publictransport")) {
                                DieselDispenser DD= new DieselDispenser(Type);
                                dbConnector.AddDieselDispenserToDB(DD,Type);

                            }else {
                                DieselDispenser DD= new DieselDispenser(Type);
                                dbConnector.AddDieselDispenserToDB(DD,Type);

                            }
                        }
                        else{
                            out.println("please Enter Again");
                            manageDispensers();
                        }
                        manageDispensers();

                    }
                    case 2 -> {
                        out.println("DISPLAY FUEL LEVEL IN REPOSITORY");
                        OctaneFuelDispenseManager petrolD= new OctaneFuelDispenseManager();
                        DieselFuelDispenseManager dieselD= new DieselFuelDispenseManager();
                        out.println("Amount of Petrol Left = "+petrolD.getFuelAmount()+"\n");
                        out.println("Amount of Diesel Left = "+dieselD.getFuelAmount()+"\n");
                    }
                    case 3 -> {
                        OctaneFuelDispenseManager petrolD= new OctaneFuelDispenseManager();
                        DieselFuelDispenseManager dieselD= new DieselFuelDispenseManager();

                        out.println("RESTOCK FUEL\n");
                        out.println("ENTER FUEL TYPE FOR REFIL [Petrol / Diesel] ");
                        String fuelType = sc.next().toLowerCase();
                        if(fuelType.equals("petrol")){
                            out.println("ENTER FUEL Amount: ");
                            String amount = sc.next().toLowerCase();
                            petrolD.restockFuel(Integer.parseInt(amount));

                        } else if (fuelType.equals("diesel")) {
                            out.println("ENTER FUEL Amount: ");
                            String amount = sc.next().toLowerCase();
                            dieselD.restockFuel(Integer.parseInt(amount));
                        }
                        manageDispensers();
                    }
                    case 0 -> MainMenuDisplay();
                    default -> manageDispensers();
                }
            } catch (NumberFormatException e) {
                out.println("Error");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }



}