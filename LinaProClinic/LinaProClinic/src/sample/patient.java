package sample;

import java.util.Date;

public class patient {
    private String P_Name;
    private int P_SSN;
    private String P_password;
    private String P_Phone_Num;
    private Date P_Birth_Date ;
    private String P_Gender;

    public patient(int p_SSN,String p_Name,Date p_Birth_Date,String p_Gender, String p_password, String p_Phone_Num) {
        P_Name = p_Name;
        P_SSN = p_SSN;
        P_password = p_password;
        P_Phone_Num = p_Phone_Num;
        P_Birth_Date = p_Birth_Date;
        P_Gender = p_Gender;
    }

    public String getP_Name() {
        return P_Name;
    }

    public void setP_Name(String p_Name) {
        P_Name = p_Name;
    }

    public int getP_SSN() {
        return P_SSN;
    }

    public void setP_SSN(int p_SSN) {
        P_SSN = p_SSN;
    }

    public String getP_password() {
        return P_password;
    }

    public void setP_password(String p_password) {
        P_password = p_password;
    }

    public String getP_Phone_Num() {
        return P_Phone_Num;
    }

    public void setP_Phone_Num(String p_Phone_Num) {
        P_Phone_Num = p_Phone_Num;
    }

    public Date getP_Birth_Date() {
        return P_Birth_Date;
    }

    public void setP_Birth_Date(Date p_Birth_Date) {
        P_Birth_Date = p_Birth_Date;
    }

    public String getP_Gender() {
        return P_Gender;
    }

    public void setP_Gender(String p_Gender) {
        P_Gender = p_Gender;
    }
}
