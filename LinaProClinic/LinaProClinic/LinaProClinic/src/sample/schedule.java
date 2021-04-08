package sample;

import java.sql.Time;
import java.sql.Date;

public class schedule {

    private int Session_ID ;
    private int Therapist_ID ;
    private String Therapist_Name ;
    private Date Session_Date;
    private Time Session_Time;

    public schedule(int S_ID, Date Session_Date, Time Session_Time, int T_SSN, String T_Name) {
        this.Session_ID = S_ID;
        this.Session_Date = Session_Date;
        this.Session_Time = Session_Time;
        this.Therapist_ID = T_SSN;
        this.Therapist_Name = T_Name;
    }

    public int getSession_ID() {
        return Session_ID;
    }

    public void setSession_ID(int session_ID) {
        Session_ID = session_ID;
    }

    public int getTherapist_ID() {
        return Therapist_ID;
    }

    public String getTherapist_Name() {
        return Therapist_Name;
    }

    public void setTherapist_Name(String therapist_Name) {
        Therapist_Name = therapist_Name;
    }

    public Date getSession_Date() {
        return Session_Date;
    }

    public void setSession_Date(Date session_Date) {
        Session_Date = session_Date;
    }

    public Time getSession_Time() {
        return Session_Time;
    }

    public void setSession_Time(Time session_Time) {
        Session_Time = session_Time;
    }

    public void setTherapist_ID(int therapist_SSN) {
        Therapist_ID = therapist_SSN;
    }

    @Override
    public String toString() {
        return "schedule{" +
                "Session_ID=" + Session_ID +
                ", Therapist_ID=" + Therapist_ID +
                ", Therapist_Name='" + Therapist_Name + '\'' +
                ", Session_Date=" + Session_Date +
                ", Session_Time=" + Session_Time +
                '}';
    }
}
