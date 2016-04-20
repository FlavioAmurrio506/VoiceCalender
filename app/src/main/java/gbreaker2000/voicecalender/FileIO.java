package gbreaker2000.voicecalender;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by Flavi on 4/17/2016.
 */
public class FileIO {
    public static String[][] FileInput()
    {
        String [][] tempArray = new String[115][11];
        try
        {


            FileInputStream fstream = new FileInputStream("CustomerAccounts.txt");
            // Get the object of DataInputStream

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            //String [][] tempArray = new String[20][8];
            //Read File Line By Line

            int r = 0;

            while ((strLine = br.readLine()) != null)
            {
                //Split the string into an array
                String[] delims = strLine.split(",");
                //Loop will assign new the split line into the array

                for (int c = 0; c < tempArray[r].length; c++)
                {
                    tempArray[r][c] = delims[c];
                }

                r++;
            }
        }

        catch (Exception e)
        {
            //Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        //System.out.println("studentlist.txt has been modified saved as output.txt");

        return tempArray;
    }//End FileInput Method


    public static void printArray(String [][] array)
    {
        String [][] tempArray = array;
        System.out.println("Index\tAccNum\t\tName\t\tLastName\t\tSSN\t\tDOB\t\tAddress\t\tCity\t\tZipCode\t\tChecking\t\tSaving");
        for (int i = 0; i < tempArray.length; i++)
        {
            for (int j = 0; j < tempArray[i].length; j++)
            {
                System.out.print(" " + tempArray[i][j] + "\t");
            }

            System.out.println(" ");
        }
    }// end printArray


    public static void FileOutput(String[][] array)
    {
        String fileName = "Appointments.txt";
        String [][] temp = array;
        PrintWriter outputStream = null;

        try
        {
            outputStream = new PrintWriter(fileName);// create  the file
        }

        catch (FileNotFoundException e)
        {
            System.out.println ("Error opening the file " + fileName);// if there is  no possible to create  the file
            System.exit (0);
        }

        //Change to For loop

        for (int i = 0; i < temp.length; i++)
        {
            for (int j = 0; j < ((temp[i].length)-1); j++)
            {
                outputStream.print(temp[i][j] + ",");
            }

            outputStream.println(temp[i][10]);
            //outputStream.println("\n");
        }//end for loop

        outputStream.close();// close the file
        System.out.println("Those lines were written to " + fileName);

    }//End FileOutput Method

}//End FileIO Class
