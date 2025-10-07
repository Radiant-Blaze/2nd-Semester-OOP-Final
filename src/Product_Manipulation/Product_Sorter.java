package Product_Manipulation;

import Display.Main;
import java.io.*;
import java.util.*;

public class Product_Sorter {
    static final String file_Name = "products.txt";

    static class Product {
        String name, exp, mfg;
        double price;
        int quantity;

        Product(String name, double price, String exp, String mfg, int quantity) {
            this.name = name;
            this.price = price;
            this.exp = exp;
            this.mfg = mfg;
            this.quantity = quantity;
        }
    }

    public static int getNextID() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file_Name))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.length() < 5 || !Character.isDigit(line.charAt(0)))
                    continue;
                count++;
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading product file for next ID");
        }
        return count + 1;
    }

    public static void sortAndReassignIDs() {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file_Name))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() < 100 || !Character.isDigit(line.charAt(0)))
                    continue;

                try {
                    String name = line.substring(7, 37).trim();
                    double price = Double.parseDouble(line.substring(39, 49).trim());
                    String exp = line.substring(51, 71).trim();
                    String mfg = line.substring(73, 93).trim();
                    int qty = Integer.parseInt(line.substring(95, 105).trim());

                    products.add(new Product(name, price, exp, mfg, qty));
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("⚠️ Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error while reading for sorting.");
            return;
        }

        // Sort alphabetically by name
        products.sort(Comparator.comparing(p -> p.name));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_Name))) {
            writer.write("-----------------------------------------------------------------------------------------------------------\n");
            writer.write("ID     Name                                 Price  Expiry Date          Manufacturing Date         Quantity\n");
            writer.write("-----------------------------------------------------------------------------------------------------------\n");

            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);

                String formattedLine =
                        String.format("%05d", i + 1) + "  " +
                                String.format("%-30s", p.name) + "  " +
                                String.format("%10.2f", p.price) + "  " +
                                String.format("%-20s", p.exp) + "  " +
                                String.format("%-20s", p.mfg) + "  " +
                                String.format("%10d", p.quantity) + "\n";

                writer.write(formattedLine);
            }
        } catch (IOException e) {
            System.out.println("❌ Error while writing sorted data.");
        }
    }
}
