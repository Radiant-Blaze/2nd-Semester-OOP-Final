package Product_Manipulation;

import Display.Main;
import java.io.*;
import java.util.Scanner;

public class Delete_Products extends Main {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void Display() {
        Start_Lines();
        Line();

        Display_Products displayProducts = new Display_Products();
        displayProducts.Show_Products();
        Line();

        System.out.println("1. Delete by Name");
        System.out.println("2. Delete by ID");

        Check_Condition();

        System.out.println("✅ Returning to main screen...");
        Thread_Sleep();
    }

    @Override
    public void Check_Condition() {
        boolean checking = true;
        while (checking) {
            Enter_Choice();
            switch (choice) {
                case 1 -> { Delete_By_Name(); checking = false; }
                case 2 -> { Delete_By_ID();   checking = false; }
                default -> System.out.println("❌ Enter 1 or 2 only.");
            }
        }
    }

    public void Delete_By_Name() {
        System.out.print("Enter the product name to delete: ");
        String nameToDelete = scanner.nextLine().toLowerCase().trim();
        Line();
        Delete_Product(nameToDelete, -1);
    }

    public void Delete_By_ID() {
        try {
            System.out.print("Enter the product ID to delete: ");
            int idToDelete = Integer.parseInt(scanner.nextLine().trim());
            Line();
            Delete_Product(null, idToDelete);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID. Only numbers allowed.");
            Line();
        }
    }

    public void Delete_Product(String nameToDelete, int idToDelete) {
        boolean found = false;

        File inputFile = new File(file_Name);
        File tempFile = new File("Temp.txt");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader || line.contains("Name") || line.contains("---")) {
                    writer.write(line + "\n");
                    if (line.contains("ID")) isHeader = false;
                    continue;
                }

                if (line.length() >= 85 && Character.isDigit(line.charAt(0))) {
                    int id = Integer.parseInt(line.substring(0, 5).trim());
                    String name = line.substring(5, 35).trim().toLowerCase();

                    boolean match = (nameToDelete != null && name.equals(nameToDelete)) ||
                            (idToDelete != -1 && id == idToDelete);

                    if (match) {
                        found = true;
                        continue; // Skip this line
                    }
                }

                writer.write(line + "\n");
            }

        } catch (IOException e) {
            System.out.println("❌ Error processing files.");
            return;
        }

        if (found) {
            if (inputFile.delete() && tempFile.renameTo(inputFile)) {
                System.out.println("✅ Product deleted successfully.");
                Product_Sorter.sortAndReassignIDs();
            } else {
                System.out.println("❌ File operation failed during delete.");
            }
        } else {
            tempFile.delete();
            System.out.println("❌ Product not found.");
        }

        Ask_To_Delete_Again();
    }

    public void Ask_To_Delete_Again() {
        while (true) {
            System.out.print("Delete another product? (y/n): ");
            String choice = scanner.nextLine().toLowerCase().trim();

            if (choice.equals("y")) {
                Display();
                break;
            } else if (choice.equals("n")) {
                break;
            } else {
                System.out.println("❌ Enter y or n only.");
            }
        }
    }

    @Override
    public void Start_Lines() {
        System.out.println("--------------- Product Deletion Section ---------------");
    }
}
