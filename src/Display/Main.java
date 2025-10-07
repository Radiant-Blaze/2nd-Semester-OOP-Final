package Display;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Main
{
    //IMPORTANT VARIABLES
    public boolean running = true;
    public int     choice;
    public static String file_Name = "Products.txt";

    //Methods
    Scanner scanner = new Scanner(System.in);


    public abstract void Display            ();
    public abstract void Check_Condition    ();
    public abstract void Start_Lines        ();

    public void End_Lines                   ()      {System.out.println("------------------------------------------------------------\n");}
    public void What_Will_You_Do_Today      ()      {System.out.println("What will you do today?");}
    public void Line                        ()      {System.out.println();}
    public void Buffer_Remover              ()      {System.out.println();}

    public void Thread_Sleep()
    {
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            System.out.println("Interrupted (Thread error)");
        }
    }
    public void Selection()
    {
        Line();
        System.out.println("Remember to type  \"NUMBERS\" ONLY ");
        End_Lines();
        System.out.print("Please Select the following: ");

        Check_Condition();
        running = false;
    }

    public void Enter_Choice()
    {
        boolean checking = true;

        while (checking)
        {
            try {
                choice = scanner.nextInt();
                System.out.println();
            } catch (InputMismatchException e)
            {
                System.out.println("Wrong input! Input must be an Integer!");
                End_Lines();
                scanner.nextLine(); // Clear invalid input
                System.out.print("Enter your Choice: ");
                continue;
            }

            checking = false;
        }
    }

    public void Return_To_Front_Page()
    {
        boolean run = true;

        while (run)
        {
            System.out.print("Return to Main page? (y/n): ");
            String choice = scanner.nextLine().toLowerCase().trim();

            if (choice.equals("y"))
            {
                run = false;
                Front front = new Front();
                front.Display();
            }
            else if (choice.equals("n"))
            {
                System.out.println("Exiting");
                run = false;
            }
            else
            {
                System.out.println("❌ Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    public boolean Duplicate_Name_check(String new_Name)
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file_Name));

            String name_Checker;

            while ((name_Checker = bufferedReader.readLine()) != null )
            {
                if (name_Checker.length() >= 5 && Character.isDigit(name_Checker.charAt(0)))
                {
                    String existing_name = name_Checker.substring(5, 35).trim().toLowerCase();

                    if (existing_name.equals(new_Name.toLowerCase()))
                    {
                        return true;
                    }
                }

            }

        } catch (FileNotFoundException e)
        {
            System.out.println("File not Found");
        } catch (IOException e)
        {
            System.out.println("IO exception occurred");
        }
        return false;
    }

    public static String padRight(String text, int length) {
        return String.format("%-" + length + "s", text);
    }


}
