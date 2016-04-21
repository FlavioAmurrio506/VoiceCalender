package gbreaker2000.voicecalender;

import java.util.Calendar;
import java.util.Date;

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

    public void setStartDate(String startDate) {
        if (!startDate.equals(""))
        {
            this.startDate = startDate;
            setMilliSec();
        }
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
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
    public void setMilliSec()
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
        Date cal = new Date(year-1900, month-1, day);
        cal.setHours(hour);
        cal.setMinutes(minute);

        this.curDate = cal;
        this.milliTime = cal.getTime();

    }

    public Date getCurDate() {
        return curDate;
    }

    public long getMilliTime() {
        return milliTime;
    }

    @Override
    public int compareTo(Appointment compApp) {
        long comparemilli = compApp.getMilliTime();

        return (int)((this.getMilliTime() - comparemilli)/(1000*60));
    }
}
