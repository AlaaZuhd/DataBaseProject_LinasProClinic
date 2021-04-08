package sample;

import java.sql.Date;
import java.sql.Time;

public class TherapistSessionSchedule {
    private int SessionIDcol ;
    private int Patientcol ;
    private String PatientNamecol ;
    private Date SessionDatecol;
    private Time SessionTimecol;

    public TherapistSessionSchedule(int sessionIDcol, int patientcol, String patientNamecol, Date sessionDatecol, Time sessionTimecol) {
        SessionIDcol = sessionIDcol;
        Patientcol = patientcol;
        PatientNamecol = patientNamecol;
        SessionDatecol = sessionDatecol;
        SessionTimecol = sessionTimecol;
    }

    public int getSessionIDcol() {
        return SessionIDcol;
    }

    public void setSessionIDcol(int sessionIDcol) {
        SessionIDcol = sessionIDcol;
    }

    public int getPatientcol() {
        return Patientcol;
    }

    public void setPatientcol(int patientcol) {
        Patientcol = patientcol;
    }

    public String getPatientNamecol() {
        return PatientNamecol;
    }

    public void setPatientNamecol(String patientNamecol) {
        PatientNamecol = patientNamecol;
    }

    public Date getSessionDatecol() {
        return SessionDatecol;
    }

    public void setSessionDatecol(Date sessionDatecol) {
        SessionDatecol = sessionDatecol;
    }

    public Time getSessionTimecol() {
        return SessionTimecol;
    }

    public void setSessionTimecol(Time sessionTimecol) {
        SessionTimecol = sessionTimecol;
    }

    @Override
    public String toString() {
        return "TherapistSessionSchedule{" +
                "SessionIDcol=" + SessionIDcol +
                ", Patientcol=" + Patientcol +
                ", PatientNamecol='" + PatientNamecol + '\'' +
                ", SessionDatecol=" + SessionDatecol +
                ", SessionTimecol=" + SessionTimecol +
                '}';
    }
}
