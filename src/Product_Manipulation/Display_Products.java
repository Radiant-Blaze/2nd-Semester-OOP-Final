package Product_Manipulation;

import Display.Main;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Display_Products extends Main {

    static final int LOW_STOCK_THRESHOLD = 5;
    static final String file_Name = "products.txt";

    @Override
    public void Display() {
        Start_Lines();
        End_Lines();
        Line();

        Show_Products();
        Line();
        End_Lines();

        System.out.println("Returning to main screen...");
        Thread_Sleep();
    }

    @Override
    public void Check_Condition() {
        // No condition needed for display
    }

    public void Show_Products() {
        System.out.println("\n📋 All Products List:\n");
        boolean anyProduct = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(file_Name))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && line.matches("^\\d+.*")) {
                    System.out.println(line);
                    anyProduct = true;
                }
            }
            if (!anyProduct) {
                System.out.println("❗ No products found in the file.");
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading product list.");
        }
        Line();
    }


    @Override
    public void Start_Lines() {
        System.out.println("--------------- Product Display Section ---------------");
    }
}
