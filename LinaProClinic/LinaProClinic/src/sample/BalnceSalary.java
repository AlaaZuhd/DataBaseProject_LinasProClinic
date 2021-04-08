package sample;

import java.sql.Time;
import java.util.Date;

public class BalnceSalary {
    private int S_ID;
    private Date S_Session_Date;
    private Time S_Start_Time;
    private int T_SSN;
    private String T_Name;
    private double T_Session_Rate;
    private Date Last_Withdraw_Date;
    private double T_Balance;

    public BalnceSalary(int s_ID, Date s_Session_Date, Time s_Start_Time, int t_SSN, String t_Name, double t_Session_Rate, Date last_Withdraw_Date, double t_Balance) {
        S_ID = s_ID;
        S_Session_Date = s_Session_Date;
        S_Start_Time = s_Start_Time;
        T_SSN = t_SSN;
        T_Name = t_Name;
        T_Session_Rate = t_Session_Rate;
        Last_Withdraw_Date = last_Withdraw_Date;
        T_Balance = t_Balance;
    }

    public int getS_ID() {
        return S_ID;
    }

    public void setS_ID(int s_ID) {
        S_ID = s_ID;
    }

    public Date getS_Session_Date() {
        return S_Session_Date;
    }

    public void setS_Session_Date(Date s_Session_Date) {
        S_Session_Date = s_Session_Date;
    }

    public Time getS_Start_Time() {
        return S_Start_Time;
    }

    public void setS_Start_Time(Time s_Start_Time) {
        S_Start_Time = s_Start_Time;
    }

    public int getT_SSN() {
        return T_SSN;
    }

    public void setT_SSN(int t_SSN) {
        T_SSN = t_SSN;
    }

    public String getT_Name() {
        return T_Name;
    }

    public void setT_Name(String t_Name) {
        T_Name = t_Name;
    }

    public double getT_Session_Rate() {
        return T_Session_Rate;
    }

    public void setT_Session_Rate(double t_Session_Rate) {
        T_Session_Rate = t_Session_Rate;
    }

    public Date getLast_Withdraw_Date() {
        return Last_Withdraw_Date;
    }

    public void setLast_Withdraw_Date(Date last_Withdraw_Date) {
        Last_Withdraw_Date = last_Withdraw_Date;
    }

    public double getT_Balance() {
        return T_Balance;
    }

    public void setT_Balance(double t_Balance) {
        T_Balance = t_Balance;
    }

    @Override
    public String toString() {
        return "BalnceSalary{" +
                "S_ID=" + S_ID +
                ", S_Session_Date=" + S_Session_Date +
                ", S_Start_Time=" + S_Start_Time +
                ", T_SSN=" + T_SSN +
                ", T_Name='" + T_Name + '\'' +
                ", T_Session_Rate=" + T_Session_Rate +
                ", Last_Withdraw_Date=" + Last_Withdraw_Date +
                ", T_Balance=" + T_Balance +
                '}';
    }
}
