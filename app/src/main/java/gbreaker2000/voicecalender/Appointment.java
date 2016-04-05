package gbreaker2000.voicecalender;

/**
 * Created by Flavi on 4/2/2016.
 */
public class Appointment {

    private String tittle;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String fileName;
    private String location;
    private boolean allDay;

    Appointment()
    {
        tittle = "Place Tittle Here";
        startDate = "Start Date";
        startTime = "Start Time";
        endDate = "End Date";
        endTime = "End Time";
        fileName = null;
        location = null;
        allDay = false;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }
}
