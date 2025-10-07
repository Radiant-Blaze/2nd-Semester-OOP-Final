package Display;

import Product_Manipulation.Add_Products;
import Product_Manipulation.Delete_Products;
import Product_Manipulation.Display_Products;

public class Product extends Main
{
    //Scanner scanner = new Scanner(System.in);

    @Override
    public void Display()
    {
        while (running)
        {
            Start_Lines();
            System.out.println("Total Products : ");
            System.out.println("Total quantity : ");
            Line();

            What_Will_You_Do_Today();
            Line();

            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Delete Products");
            System.out.println("4. Exit");
            Line();

            Selection();
        }
    }

    @Override
    public void Check_Condition()
    {
        boolean checking = true;

        while (checking)
        {
            Enter_Choice();

            switch (choice)
            {
                case 1 :

                    System.out.println("Proceeding to Add product section...");
                    Thread_Sleep();

                    Add_Products addProducts = new Add_Products();
                    addProducts.Display();

                    Return_To_Front_Page();

                    checking = false;
                    break;

                case 2 :
                    Display_Products displayProducts = new Display_Products();
                    displayProducts.Display();
                    scanner.nextLine();

                    Return_To_Front_Page();

                    checking = false;
                    break;

                case 3:
                    Delete_Products deleteProducts = new Delete_Products();
                    deleteProducts.Display();
                    scanner.nextLine();

                    Return_To_Front_Page();

                    checking = false;
                    break;

                case 4 : System.out.println(" Exiting");                            running = false;    checking = false;         break;
                default: System.out.println(" Enter between 1-4");                                      checking = false;         break;
            }
        }
    }

    @Override
    public void Start_Lines() {System.out.println("---------------Welcome  to  products  section---------------");}
}
