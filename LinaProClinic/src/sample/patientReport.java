package sample;

import java.sql.Time;
import java.util.Date;

public class patientReport {

    private int Patient_ID1;
    private String Patient_Name1;
    private String Companion_Name1;
    private String Companion_Phone_Num1;
    private String Patient_Gender1;

    private String Therapist_NameP;
    private int Therapist_IDP;
    private String Therapy_TypeP;
    private Date Session_DateP;
    private Time Session_TimeP;

    public patientReport(String therapist_NameP, int therapist_IDP, String therapy_TypeP, Date session_DateP, Time session_TimeP) {
        Therapist_NameP = therapist_NameP;
        Therapist_IDP = therapist_IDP;
        Therapy_TypeP = therapy_TypeP;
        Session_DateP = session_DateP;
        Session_TimeP = session_TimeP;
    }

    public String getTherapist_NameP() {
        return Therapist_NameP;
    }

    public void setTherapist_NameP(String therapist_NameP) {
        Therapist_NameP = therapist_NameP;
    }

    public int getTherapist_IDP() {
        return Therapist_IDP;
    }

    public void setTherapist_IDP(int therapist_IDP) {
        Therapist_IDP = therapist_IDP;
    }

    public String getTherapy_TypeP() {
        return Therapy_TypeP;
    }

    public void setTherapy_TypeP(String therapy_TypeP) {
        Therapy_TypeP = therapy_TypeP;
    }

    public Date getSession_DateP() {
        return Session_DateP;
    }

    public void setSession_DateP(Date session_DateP) {
        Session_DateP = session_DateP;
    }

    public Time getSession_TimeP() {
        return Session_TimeP;
    }

    public void setSession_TimeP(Time session_TimeP) {
        Session_TimeP = session_TimeP;
    }

    public patientReport(int patient_ID1, String patient_Name1, String patient_Gender1) {
        Patient_ID1 = patient_ID1;
        Patient_Name1 = patient_Name1;
        Patient_Gender1 = patient_Gender1;
    }

    public patientReport(int patient_ID1, String patient_Name1, String companion_Name1, String companion_Phone_Num1, String patient_Gender1) {
        Patient_ID1 = patient_ID1;
        Patient_Name1 = patient_Name1;
        Companion_Name1 = companion_Name1;
        Companion_Phone_Num1 = companion_Phone_Num1;
        Patient_Gender1 = patient_Gender1;
    }

    public int getPatient_ID1() {
        return Patient_ID1;
    }

    public void setPatient_ID1(int patient_ID1) {
        Patient_ID1 = patient_ID1;
    }

    public String getPatient_Name1() {
        return Patient_Name1;
    }

    public void setPatient_Name1(String patient_Name1) {
        Patient_Name1 = patient_Name1;
    }

    public String getCompanion_Name1() {
        return Companion_Name1;
    }

    public void setCompanion_Name1(String companion_Name1) {
        Companion_Name1 = companion_Name1;
    }

    public String getCompanion_Phone_Num1() {
        return Companion_Phone_Num1;
    }

    public void setCompanion_Phone_Num1(String companion_Phone_Num1) {
        Companion_Phone_Num1 = companion_Phone_Num1;
    }

    public String getPatient_Gender1() {
        return Patient_Gender1;
    }

    public void setPatient_Gender1(String patient_Gender1) {
        Patient_Gender1 = patient_Gender1;
    }
}
