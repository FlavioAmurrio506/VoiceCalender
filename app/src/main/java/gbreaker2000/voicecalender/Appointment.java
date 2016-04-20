package gbreaker2000.voicecalender;

public class Appointment {

    private String tittle;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String fileName;
    private String location;
    private String notes;
    private boolean allDay;

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
        }
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        if(!startTime.equals(""))
        {
            this.startTime = startTime;
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
}
