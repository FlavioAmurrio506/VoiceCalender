package gbreaker2000.voicecalender;

import android.app.AppOpsManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Appointment implements Comparable<Appointment>{

    private String tittle;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String fileName;
    private String location;
    private String notes;
    private boolean allDay;
    private long milliTime = 0;
    private Date curDate = new Date();
    int reminderInterval = 0;

    public Appointment()
    {
        tittle = "Place Tittle Here";
        startDate = "01/01/1970";
        startTime = "0:00";
        endDate = "01/01/1970";
        endTime = "0:00";
        fileName = "";
        location = "";
        notes = "";
        allDay = false;
        //curAppoint = new Date();
    }

    public Appointment(String sTittle, String date)
    {
        this.tittle = sTittle;
        this.startDate = date;
        startTime = "0:00";
        endDate = "01/01/1970";
        endTime = "0:00";
        fileName = "";
        location = "";
        notes = "";
        allDay = false;
        //curAppoint = new Date();
    }


    public String getNotes() {
        return notes;
    }





    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        if(!tittle.equals(""))
        {
            this.tittle = tittle;
        }
        else
        {
            this.tittle = "Temp Tittle";
        }
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) throws Exception
    {
        if (!startDate.equals(""))
        {
            this.startDate = startDate;
            setMilliSec();
        }
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) throws Exception
    {
        if(!startTime.equals(""))
        {
            this.startTime = startTime;
            setMilliSec();
        }
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        if(!endDate.equals(""))
        {
            this.endDate = endDate;
        }
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        if(!endTime.equals(""))
        {
            this.endTime = endTime;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if(!fileName.equals(""))
        {
            this.fileName = fileName;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if(!location.equals(""))
        {
            this.location = location;
        }
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }
    public boolean hasRecording()
    {
        if (this.getFileName().equals("") || this.getFileName().equals(null))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void setNotes(String notes) {
        if(!notes.equals(""))
        {
            this.notes = notes;
        }
    }
    public static String dateToString(int year, int month, int day)
    {
        Date curDate = new Date (year, month, day);
        return curDate.toString();
    }

    public String shortStringDate() throws Exception
    {
        this.setMilliSec();
        String min;
        if(curDate.getMinutes() <10)
        {
            min = "0" + curDate.getMinutes();
        }
        else
        {
            min = "" + curDate.getMinutes();
        }
        return " (" + (curDate.getMonth()+1) + "/" + curDate.getDate() + "/" + (curDate.getYear()-100)  + ") (" + curDate.getHours() + ":" + min + ") ";
    }

    public void setMilliSec () throws Exception
    {
        int year;
        int month ;
        int day;
        int hour;
        int minute;
        String[] tempdate = this.getStartDate().split("/");
        month = Integer.parseInt(tempdate[0]);
        day = Integer.parseInt(tempdate[1]);
        year = Integer.parseInt(tempdate[2]);
        String[] tempHour = this.getStartTime().split(":");
        hour = Integer.parseInt(tempHour[0]);
        minute = Integer.parseInt(tempHour[1]);
        if(hour>24 || hour<0)
        {
            throw new Exception();
        }
        if(minute>59 ||  minute<0)
        {
            throw new Exception();
        }

        Date cal = new Date(year-1900, month-1, day);
        cal.setHours(hour);
        cal.setMinutes(minute);

        this.curDate = cal;
        this.milliTime = cal.getTime();

    }

    public Date getCurDate() {
        return curDate;
    }

    public Date dateObjGet()
    {

        try
        {
            setMilliSec();
        }
        catch (Exception e)
        {

        }
        return this.curDate;
    }

    public long getMilliTime() {
        return milliTime;
    }

    public void setReminderInterval(String remInt)
    {
        this.reminderInterval = Integer.parseInt(remInt);
    }

    @Override
    public int compareTo(Appointment compApp) {
        long comparemilli = compApp.getMilliTime();

        return (int)((this.getMilliTime() - comparemilli)/(1000*60));
    }

    public static Appointment NextAppointment()
    {
        long curTime = System.currentTimeMillis();
        FileIO fileIO = new FileIO();
        List<Appointment> aptFile = new ArrayList<>();
        aptFile.clear();
        aptFile.addAll(fileIO.FileInput());
        int indexOfNext = 0;
        for(int i = 0; i < aptFile.size(); i++)
        {
            if (aptFile.get(i).getMilliTime() > curTime)
            {
                try
                {
                    indexOfNext = i - 1;
                    break;
                }
                catch (Exception e)
                {
                    indexOfNext = i;
                    break;
                }
            }
        }
        return aptFile.get(indexOfNext-1);
    }
    public static List<Appointment> DayAppointments(String selDay)
    {

        FileIO fileIO = new FileIO();
        List<Appointment> aptFile = new ArrayList<>();
        aptFile.clear();
        aptFile.addAll(fileIO.FileInput());
        List<Appointment> todayApt = new ArrayList<>();
        for(int i = 0; i < aptFile.size(); i++)
        {

            if(aptFile.get(i).getStartDate().equals(selDay))
            {
                todayApt.add(aptFile.get(i));
            }
        }
        return todayApt;
    }

    public static List<Appointment> UpComingAppointments() throws Exception
    {
        long curTime = System.currentTimeMillis()-(24*60*60*1000);


        FileIO fileIO = new FileIO();
        List<Appointment> aptFile = new ArrayList<>();
        aptFile.clear();
        aptFile.addAll(fileIO.FileInput());
        List<Appointment> todayApt = new ArrayList<>();
        for(int i = 0; i < aptFile.size(); i++)
        {
            aptFile.get(i).setMilliSec();
            if(aptFile.get(i).getMilliTime()>curTime)
            {
                todayApt.add(aptFile.get(i));
            }
        }
        return todayApt;
    }
    public static int AptDisSearch(long searchMilli)
    {
//        Appointment foundApt = null;
        int foundIndex = 0;
        FileIO fileIO = new FileIO();
        List<Appointment> aptFile = new ArrayList<>();
        aptFile.clear();
        aptFile.addAll(fileIO.FileInput());
        for(int i = 0; i<aptFile.size(); i++)
        {
            if (aptFile.get(i).getMilliTime() == searchMilli)
            {
                foundIndex = i;
                break;
            }
        }
        return foundIndex;

    }
    public static String aptStringBuilder(Appointment foundApt)
    {
        StringBuilder sb = new StringBuilder();
        try {

            sb.append("Tittle: " + foundApt.getTittle() + "\n");
            sb.append("Date: " + foundApt.getStartDate() + "\n");
            sb.append("Time: " + foundApt.getStartTime() + "\n");
            sb.append("Location: " + foundApt.getLocation() + "\n");
            sb.append("Notes: " + foundApt.getNotes() + "\n");
            sb.append("Recording: " + foundApt.hasRecording() + "\n");
//            Toast.makeText(this, "" + looker.get(indexOfItem - 1).getTittle() + "", Toast.LENGTH_LONG).show();
        }


        catch (Exception e)
        {

        }
        return sb.toString();
    }
}
