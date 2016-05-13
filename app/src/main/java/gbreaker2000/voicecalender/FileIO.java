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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Flavi on 4/17/2016.
 */

// File input/output class.

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
        Collections.sort(inputData);
        return inputData;
    }//End FileInput Method

    public static Appointment stringToAppointment(String data) throws Exception
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
        tempApp.setNotes(parts[7]);
        tempApp.setAllDay(Boolean.parseBoolean(parts[8]));
        return tempApp;
    }

    public static void FileOutput(List<Appointment> outdata)
    {
        Collections.sort(outdata);
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
                    outdata.get(i).getLocation() + "," + outdata.get(i).getNotes() + ",");
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

    private static String getFilePathDataAlarm()
    {
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filePath,"VoiceCalendarAudio");
        if(!file.exists())
            file.mkdir();
        return(file.getAbsolutePath() + "/" + "alarms.txt");
    }

    public static List<Long> AlarmSaveIn()
    {
        List<Long> inputData = new ArrayList<>();
        try
        {
            FileInputStream fstream = new FileInputStream(getFilePathDataAlarm());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null)
            {
                inputData.add(Long.parseLong(strLine));
            }
        }

        catch (Exception e)
        {
            //Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        Collections.sort(inputData);
//        AlarmSaveOut(inputData);

        long curTime = System.currentTimeMillis();
        for(int i = 0; i<inputData.size(); i++)
        {
            if(inputData.get(i)<curTime)
            {
                inputData.remove(i);
            }
            else if (inputData.get(i)>curTime)
            {
//                break;
            }
        }

        if (inputData.size()<1)
        {
            inputData.add(new Date(2030-1900,8,23).getTime());
        }
        Collections.sort(inputData);
        return inputData;
    }//End FileInput Method

    public static void AlarmSaveOut(List<Long> outdatain)
    {
        ArrayList<Long> outdata = new ArrayList<>();
        outdata.addAll(outdatain);
        Collections.sort(outdata);
        long curTime = System.currentTimeMillis();
        String fileName = getFilePathDataAlarm();
        //String [][] temp = array;
        PrintWriter outputStream = null;
        long birth = new Date(2030-1900, 9-1, 23).getTime();

        try
        {
            outputStream = new PrintWriter(fileName);// create  the file
        }
        catch (FileNotFoundException e)
        {
            System.out.println ("Error opening the file " + fileName);// if there is  no possible to create  the file
            System.exit (0);
        }
        if (outdata.size() < 1) {outdata.add(birth);}
        for (int i = 0; i < outdata.size(); i++)
        {
            if (outdata.get(i)>curTime)
            {
                outputStream.println(outdata.get(i));
            }

        }//end for loop
        outputStream.close();// close the file
        //System.out.println("Those lines were written to " + fileName);

    }

    public static HashSet<Date> HashInput()
    {
        List<Appointment> inputData = new ArrayList<>();
        HashSet<Date> hashOut= new HashSet<>();
        hashOut.clear();
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
        Collections.sort(inputData);
        for(int i = 0; i<inputData.size();i++)
        {
            hashOut.add(inputData.get(i).dateObjGet());
        }
        return hashOut;
    }//End FileInput Method



}//End FileIO Class
