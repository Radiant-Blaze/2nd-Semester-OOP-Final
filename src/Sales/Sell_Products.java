package Sales;

import Display.*;
import Product_Manipulation.Display_Products;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Sell_Products extends Main {
    Scanner scanner = new Scanner(System.in);
    static final String SALE_FILE = "sale.txt";
    String fileName = Main.file_Name;

    @Override
    public void Display() {
        Start_Lines();
        Line();
        new Display_Products().Show_Products();
        Sell_Product();
        System.out.println("Returning to main screen...");
        Thread_Sleep();
    }

    @Override
    public void Check_Condition() {}

    public void Sell_Product() {
        System.out.print("Enter product ID to sell: ");
        int productId = getIntInput();

        System.out.print("Enter quantity to sell: ");
        int qtyToSell = getIntInput();

        File inputFile = new File(fileName);
        boolean productFound = false;
        double totalIncome = 0;

        List<String> updatedProducts = new ArrayList<>();
        List<String> newSales = new ArrayList<>();
        String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() < 90 || !Character.isDigit(line.charAt(0))) {
                    updatedProducts.add(line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(line.substring(0, 5).trim());
                    String name = line.substring(7, 37).trim();
                    double price = Double.parseDouble(line.substring(39, 49).trim());
                    String exp = line.substring(51, 71).trim();
                    String mfg = line.substring(71, 91).trim();
                    int qty = Integer.parseInt(line.substring(93).trim());

                    if (id == productId) {
                        productFound = true;
                        if (qty >= qtyToSell) {
                            qty -= qtyToSell;
                            double income = qtyToSell * price;
                            totalIncome += income;

                            System.out.println("✅ Sold " + qtyToSell + " items of " + name);

                            newSales.add(String.format("%05d  %-30s  %10.2f  %-20s  %-20s  %10d",
                                    id, name, price, exp, mfg, qtyToSell));
                        } else {
                            System.out.println("❌ Insufficient stock. Available: " + qty);
                        }
                    }

                    String lineToWrite = String.format(
                            "%05d  %-30s  %10.2f  %-20s  %-20s  %10d",
                            id, name.toLowerCase(), price, exp, mfg, qty);
                    updatedProducts.add(lineToWrite);
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    updatedProducts.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error processing sale.");
            return;
        }

        if (productFound && totalIncome > 0) {
            updatedProducts.sort((s1, s2) -> {
                try {
                    return Integer.compare(
                            Integer.parseInt(s1.substring(0, 5).trim()),
                            Integer.parseInt(s2.substring(0, 5).trim()));
                } catch (NumberFormatException e) {
                    return 0;
                }
            });

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
                for (String prod : updatedProducts) {
                    writer.write(prod + "\n");
                }
            } catch (IOException e) {
                System.out.println("❌ Failed to update product file.");
            }

            try (BufferedWriter saleWriter = new BufferedWriter(new FileWriter(SALE_FILE, true))) {
                boolean isFirstSale = new File(SALE_FILE).length() == 0;
                if (isFirstSale || !lastSaleDateLineExists(today)) {
                    saleWriter.write("\n========= Sales for " + today + " =========\n");
                    saleWriter.write("-----------------------------------------------------------------------------------------------------------\n");
                    saleWriter.write("ID     Name                                 Price  Expiry Date          Manufacturing Date         Quantity\n");
                    saleWriter.write("-----------------------------------------------------------------------------------------------------------\n");
                }

                for (String sale : newSales) {
                    saleWriter.write(sale + "\n");
                }

                saleWriter.write(String.format("Total Income: Rs %.2f\n", totalIncome));
            } catch (IOException e) {
                System.out.println("❌ Error writing to sale file.");
            }
        } else if (!productFound) {
            System.out.println("❌ Product ID not found.");
        }

        Line();
    }

    private boolean lastSaleDateLineExists(String date) {
        try (BufferedReader reader = new BufferedReader(new FileReader(SALE_FILE))) {
            String line;
            String expected = "========= Sales for " + date + " =========";
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(expected)) return true;
            }
        } catch (IOException ignored) {}
        return false;
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid number, try again: ");
            }
        }
    }

    @Override
    public void Start_Lines() {
        System.out.println("--------------- Product Selling Section ---------------");
    }
}
