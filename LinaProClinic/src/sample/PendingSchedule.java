package sample;

import java.sql.Time;
import java.sql.Date;

public class PendingSchedule {
    private Date SessionDatecol1;
    private Time SessionTimecol1;
    private String PatientNamecol1;
    private int PatientSSNcol1;

    public PendingSchedule(Date sessionDatecol1, Time sessionTimecol1, String patientNamecol1, int patientSSNcol1) {
        SessionDatecol1 = sessionDatecol1;
        SessionTimecol1 = sessionTimecol1;
        PatientNamecol1 = patientNamecol1;
        PatientSSNcol1 = patientSSNcol1;
    }

    public Date getSessionDatecol1() {
        return SessionDatecol1;
    }

    public void setSessionDatecol1(Date sessionDatecol1) {
        SessionDatecol1 = sessionDatecol1;
    }

    public Time getSessionTimecol1() {
        return SessionTimecol1;
    }

    public void setSessionTimecol1(Time sessionTimecol1) {
        SessionTimecol1 = sessionTimecol1;
    }

    public String getPatientNamecol1() {
        return PatientNamecol1;
    }

    public void setPatientNamecol1(String patientNamecol1) {
        PatientNamecol1 = patientNamecol1;
    }

    public int getPatientSSNcol1() {
        return PatientSSNcol1;
    }

    public void setPatientSSNcol1(int patientSSNcol1) {
        PatientSSNcol1 = patientSSNcol1;
    }

    @Override
    public String toString() {
        return "PendingSchedule{" +
                "SessionDatecol1=" + SessionDatecol1 +
                ", SessionTimecol1=" + SessionTimecol1 +
                ", PatientNamecol1='" + PatientNamecol1 + '\'' +
                ", PatientSSNcol1=" + PatientSSNcol1 +
                '}';
    }
}
