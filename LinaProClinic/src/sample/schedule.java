package sample;

import java.sql.Time;
import java.sql.Date;

public class schedule {

    private int Session_ID ;
    private int Therapist_ID ;
    private String Therapist_Name ;
    private Date Session_Date;
    private Time Session_Time;
    private Boolean paid=false;
    private double Session_Price=0;
    private String Therapy_Type;
    private String Phone_Number;
    private double Session_Rate;
    private String Patient_Name;
    private int Patient_ID;
    private String Status;

    //This is used for therapists Report
    public schedule( String therapist_Name,int therapist_ID, String therapy_Type, double session_Rate,String phone_Number) {
        Therapist_ID = therapist_ID;
        Therapist_Name = therapist_Name;
        Therapy_Type = therapy_Type;
        Phone_Number = phone_Number;
        Session_Rate = session_Rate;
    }

    //This is used for sessions in admin after choosing the therapist
    public schedule(int session_ID, Date session_Date, Time session_Time, String patient_Name,int P_ID) {
        Session_ID = session_ID;
        Session_Date = session_Date;
        Session_Time = session_Time;
        Patient_Name = patient_Name;
        Patient_ID = P_ID;

    }

    //Used for Barchart
    public schedule(int therapist_ID, double session_Rate) {
        Therapist_ID = therapist_ID;
        Session_Rate = session_Rate;
    }

    public int getPatient_ID() {
        return Patient_ID;
    }

    public void setPatient_ID(int patient_ID) {
        Patient_ID = patient_ID;
    }

    public String getPatient_Name() {
        return Patient_Name;
    }

    public void setPatient_Name(String patient_Name) {
        Patient_Name = patient_Name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public double getSession_Rate() {
        return Session_Rate;
    }

    public void setSession_Rate(double session_Rate) {
        Session_Rate = session_Rate;
    }

    //This constructor used for MySchedule Table
    public schedule(int S_ID, Date Session_Date, Time Session_Time, int T_SSN, String T_Name) {
        this.Session_ID = S_ID;
        this.Session_Date = Session_Date;
        this.Session_Time = Session_Time;
        this.Therapist_ID = T_SSN;
        this.Therapist_Name = T_Name;
    }

    //This constructor for reservation details
    public schedule(int therapist_ID, String therapist_Name, Date session_Date, Time session_Time, String therapy_Type, String status) {
        Therapist_ID = therapist_ID;
        Therapist_Name = therapist_Name;
        Session_Date = session_Date;
        Session_Time = session_Time;
        Therapy_Type = therapy_Type;
        Status = status;
    }

    //This constructor used for payment Table
    public schedule(int S_ID, Date Session_Date, Time Session_Time, int T_SSN, String T_Name, Boolean paid,double s_price) {
        this.Session_ID = S_ID;
        this.Session_Date = Session_Date;
        this.Session_Time = Session_Time;
        this.Therapist_ID = T_SSN;
        this.Therapist_Name = T_Name;
        this.paid = paid;
        this.Session_Price=s_price;
    }

    //This constructor used for reserve table
    public schedule(Date session_Date, Time session_Time, String therapy_type,int therapist_ID) {
        this.Session_Date = session_Date;
        this.Session_Time = session_Time;
        this.Therapy_Type = therapy_type;
        this.Therapist_ID=therapist_ID;
    }

    public String getTherapy_Type() {
        return Therapy_Type;
    }

    public void setTherapy_Type(String therapy_Type) {
        Therapy_Type = therapy_Type;
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

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public double getSession_Price() {
        return Session_Price;
    }

    public void setSession_Price(double session_price) {
        Session_Price = session_price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "schedule{" +
                "Therapist_ID=" + Therapist_ID +
                ", Session_Rate=" + Session_Rate +
                '}';
    }
}
