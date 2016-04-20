package gbreaker2000.voicecalender;

public class Appointment {

    private String tittle;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String fileName;
    private String location;
    private boolean allDay;

    public Appointment()
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
    public String[] toArray()
    {
        String[] appointmentInfo = new String[8];
        appointmentInfo[0] = this.getTittle();
        appointmentInfo[1] = this.getStartDate();
        appointmentInfo[2] = this.getStartTime();
        appointmentInfo[3] = this.getEndDate();
        appointmentInfo[4] = this.getEndTime();
        appointmentInfo[5] = this.getFileName();
        appointmentInfo[6] = this.getLocation();
        appointmentInfo[7] = String.valueOf(this.isAllDay());
        return appointmentInfo;
    }
}
