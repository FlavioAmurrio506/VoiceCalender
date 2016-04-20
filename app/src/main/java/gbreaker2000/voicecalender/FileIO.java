package gbreaker2000.voicecalender;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavi on 4/17/2016.
 */
public class FileIO {
    public static List<Appointment> FileInput()
    {
        List<Appointment> inputData = new ArrayList<>();
        try
        {
            FileInputStream fstream = new FileInputStream(getFilePathData());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null)
            {
                inputData.add(stringToAppointment(strLine));
            }
        }

        catch (Exception e)
        {
            //Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return inputData;
    }//End FileInput Method

    public static Appointment stringToAppointment(String data)
    {
        String[] parts = data.split(",");
        Appointment tempApp = new Appointment();
        tempApp.setTittle(parts[0]);
        tempApp.setStartDate(parts[1]);
        tempApp.setStartTime(parts[2]);
        tempApp.setEndDate(parts[3]);
        tempApp.setEndTime(parts[4]);
        tempApp.setFileName(parts[5]);
        tempApp.setLocation(parts[6]);
        tempApp.setAllDay(Boolean.parseBoolean(parts[7]));
        return tempApp;
    }

    public static void FileOutput(List<Appointment> outdata)
    {
        String fileName = getFilePathData();
        //String [][] temp = array;
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
        for (int i = 0; i < outdata.size(); i++)
        {
            outputStream.print(outdata.get(i).getTittle() + "," + outdata.get(i).getStartDate() + "," +
                    outdata.get(i).getStartTime() + "," + outdata.get(i).getEndDate() + "," +
                    outdata.get(i).getEndTime() + "," + outdata.get(i).getFileName() + "," +
                    outdata.get(i).getLocation() + "," );
            outputStream.println(outdata.get(i).isAllDay());
        }//end for loop
        outputStream.close();// close the file
        //System.out.println("Those lines were written to " + fileName);

    }//End FileOutput Method

    private static String getFilePathData()
    {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filePath,"VoiceCalendarAudio");
        if(!file.exists())
            file.mkdir();
        return(file.getAbsolutePath() + "/" + "appointdata.txt");
    }

}//End FileIO Class
