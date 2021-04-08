package sample;

import java.util.Date;

public class therapist_provide {
    private String T_Name;
    private int T_SSN;
    private int Therapy_ID;
    private double T_Session_Rate;
    private String T_password;
    private String T_Phone_Num;
    private int Years_of_Experience;
    private Date T_Birth_Date ;

    public therapist_provide(String t_Name, int t_SSN, int therapy_ID, double t_Session_Rate, String t_password, String t_Phone_Num, Date T_Birth_Date ) {
        T_Name = t_Name;
        T_SSN = t_SSN;
        Therapy_ID = therapy_ID;
        T_Session_Rate = t_Session_Rate;
        T_password = t_password;
        T_Phone_Num = t_Phone_Num;
        this.T_Birth_Date = T_Birth_Date ;
    }
    public therapist_provide(String t_password){
        T_password=t_password;
    }
    public String getT_Name() {
        return T_Name;
    }

    public void setT_Name(String t_Name) {
        T_Name = t_Name;
    }

    public int getT_SSN() {
        return T_SSN;
    }

    public void setT_SSN(int t_SSN) {
        T_SSN = t_SSN;
    }

    public int getTherapy_ID() {
        return Therapy_ID;
    }

    public void setTherapy_ID(int therapy_ID) {
        Therapy_ID = therapy_ID;
    }

    public double getT_Session_Rate() {
        return T_Session_Rate;
    }

    public void setT_Session_Rate(double t_Session_Rate) {
        T_Session_Rate = t_Session_Rate;
    }

    public String getT_password() {
        return T_password;
    }

    public void setT_password(String t_password) {
        T_password = t_password;
    }

    public String getT_Phone_Num() {
        return T_Phone_Num;
    }

    public void setT_Phone_Num(String t_Phone_Num) {
        T_Phone_Num = t_Phone_Num;
    }

    public int getYears_of_Experience() {
        return Years_of_Experience;
    }

    public void setYears_of_Experience(int years_of_Experience) {
        Years_of_Experience = years_of_Experience;
    }

    public void setT_Birth_Date(Date t_Birth_Date) {
        T_Birth_Date = t_Birth_Date;
    }

    public Date getT_Birth_Date() {
        return T_Birth_Date;
    }

    @Override
    public String toString() {
        return "therapist_provide{" +
                "T_Name='" + T_Name + '\'' +
                ", T_SSN=" + T_SSN +
                ", Therapy_ID=" + Therapy_ID +
                ", T_Session_Rate=" + T_Session_Rate +
                ", T_password='" + T_password + '\'' +
                ", T_Phone_Num='" + T_Phone_Num + '\'' +
                ", Years_of_Experience=" + Years_of_Experience +
                '}';
    }
}
