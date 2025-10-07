package Display;

import Sales.Sell_Products;

public class Front extends Main
{

    @Override
    public void Display()
    {
        while (running)
        {
            Start_Lines();

            What_Will_You_Do_Today();
            Line();

            System.out.println("1. Checkout");
            System.out.println("2. Product section (FOR EMPLOYEES ONLY)");
            System.out.println("3. Exit Program");
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
                case 1:
                    Sell_Products sellProducts = new Sell_Products();
                    System.out.println("We will go to process sale!"); End_Lines();
                    sellProducts.Display();
                    checking = false; break;

                case 2:
                    Product product = new Product();
                    System.out.println("Redirecting to Products page!"); End_Lines();
                    product.Display();
                    checking = false; break;

                case 3:
                    System.out.println("Exiting...");
                    checking = false; break;

                default: System.out.println("Wrong input detected, please select between 1-4"); End_Lines();
                    System.out.print("Enter your Choice: "); break;
            }
        }
    }



    @Override
    public void Start_Lines() {System.out.println("---------------Welcome to ABC Supermarket App---------------\n");}
}
