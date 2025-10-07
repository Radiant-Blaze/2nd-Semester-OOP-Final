package Product_Manipulation;

import Display.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Add_Products extends Main {
    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    String name;
    String manufacturing_Date;
    String expiry_Date;
    Double price;
    int quantity;

    @Override
    public void Display() {
        while (running) {
            int new_ID = Product_Sorter.getNextID();

            Start_Lines();
            Line();
            What_Will_You_Do_Today();
            Line();

            new Display_Products().Display();

            try {
                while (true) {
                    Line();
                    System.out.print("Enter the product's Name: ");
                    name = scanner.nextLine().toLowerCase();
                    Line();
                    if (Duplicate_Name_check(name)) {
                        System.out.println("❌ This product name already exists. Please enter a different name.");
                        Line();
                    } else {
                        break;
                    }
                }

                while (true) {
                    try {
                        System.out.print("Enter the product's Price: ");
                        price = scanner.nextDouble();
                        scanner.nextLine();
                        if (price > 0) break;
                        else System.out.println("❌ Price should be greater than 0.");
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Enter price like 1.1 — not words.");
                        scanner.nextLine();
                    }
                    Line();
                }

                while (true) {
                    try {
                        System.out.print("Enter the product's Quantity: ");
                        quantity = scanner.nextInt();
                        scanner.nextLine();
                        if (quantity > 0) break;
                        else System.out.println("❌ Quantity must be greater than 0.");
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Enter quantity in whole numbers (e.g., 1, 5, 10).");
                        scanner.nextLine();
                    }
                    Line();
                }

                do {
                    System.out.print("Enter the product's Manufacturing Date (dd-mm-yyyy): ");
                    manufacturing_Date = scanner.nextLine();
                    Line();
                    if (!Date_Format_Checker(manufacturing_Date)) {
                        System.out.println("❌ Invalid format! Please enter in dd-mm-yyyy.");
                        Line();
                    }
                } while (!Date_Format_Checker(manufacturing_Date));

                do {
                    System.out.print("Enter the product's Expiry Date (dd-mm-yyyy): ");
                    expiry_Date = scanner.nextLine();
                    Line();
                    if (!Date_Format_Checker(expiry_Date)) {
                        System.out.println("❌ Invalid format! Please enter in dd-mm-yyyy.");
                        Line();
                    }
                } while (!Date_Format_Checker(expiry_Date));

                End_Lines();
            } catch (InputMismatchException e) {
                System.out.println("Error occurred!");
                System.out.println("Please check the following");
                System.out.println("Name                -> String");
                System.out.println("Price               -> Double (CAN CONTAIN DECIMALS)");
                System.out.println("Quantity            -> Integer (ONLY WHOLE NUMBERS)");
                System.out.println("Manufacturing date  -> String");
                System.out.println("Expiry date         -> String");
                Buffer_Remover();
            }

            try {
                File file = new File("products.txt");
                boolean isNewFile = !file.exists() || file.length() == 0;
                FileWriter fileWriter = new FileWriter(file, true);

                if (isNewFile) {
                    fileWriter.write("╔═════╦════════════════════════════════════╦══════╦════════════════════╦═══════════════════════════════════╗\n");
                    fileWriter.write("║ID   ║Name                                ║Price ║Expiry Date         ║Manufacturing Date         Quantity║\n");
                    fileWriter.write("╠═════╬════════════════════════════════════╬══════╬════════════════════╬═══════════════════════════════════╣\n");
                }

                String formattedLine = String.format(
                        "║%04d║ %-30s║ %10.2f║  %-20s║ %-20s║ %9d║\n",
                        new_ID, name, price, expiry_Date, manufacturing_Date, quantity
                );

                fileWriter.write(formattedLine);
                fileWriter.close();

                Check_Condition();
            } catch (IOException e) {
                System.out.println("IO Exception Occurred!");
            }
        }

        Product_Sorter.sortAndReassignIDs();
        System.out.println("Returning to main screen");
        Thread_Sleep();
    }

    @Override
    public void Check_Condition() {
        while (true) {
            System.out.print("Enter new product? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                return;
            } else if (choice.equals("n")) {
                System.out.println("Exiting...");
                running = false;
                return;
            } else {
                System.out.println("❌ Wrong Input! Please type y (yes) or n (no).\n");
            }
        }
    }

    public boolean Date_Format_Checker(String date) {
        return date.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    @Override
    public void Start_Lines() {
        System.out.println("----------------- Welcome to Add Products -----------------");
    }
}
