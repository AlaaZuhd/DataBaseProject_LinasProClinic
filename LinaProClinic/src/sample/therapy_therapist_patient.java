package sample;

public class therapy_therapist_patient {

    private int T_SSN ;
    private int P_SSN ;
    private int Therapy_ID ;
    private String Therapy_Type;
    private int Number_Of_Therapist;
    private int Number_Of_Patient;

    public therapy_therapist_patient(String therapy_Type) {
        Therapy_Type = therapy_Type;
    }

    public therapy_therapist_patient(int t_SSN, int p_SSN, int therapy_ID, String therapy_Type) {
        T_SSN = t_SSN;
        P_SSN = p_SSN;
        Therapy_ID = therapy_ID;
        Therapy_Type = therapy_Type;
    }

    public therapy_therapist_patient(String therapy_Type, int number_Of_Therapist, int number_Of_Patient) {
        Therapy_Type = therapy_Type;
        Number_Of_Therapist = number_Of_Therapist;
        Number_Of_Patient = number_Of_Patient;
    }

    public int getT_SSN() {
        return T_SSN;
    }

    public void setT_SSN(int t_SSN) {
        T_SSN = t_SSN;
    }

    public int getP_SSN() {
        return P_SSN;
    }

    public void setP_SSN(int p_SSN) {
        P_SSN = p_SSN;
    }

    public int getTherapy_ID() {
        return Therapy_ID;
    }

    public void setTherapy_ID(int therapy_ID) {
        Therapy_ID = therapy_ID;
    }

    public String getTherapy_Type() {
        return Therapy_Type;
    }

    public void setTherapy_Type(String therapy_Type) {
        Therapy_Type = therapy_Type;
    }

    public int getNumber_Of_Therapist() {
        return Number_Of_Therapist;
    }

    public void setNumber_Of_Therapist(int number_Of_Therapist) {
        Number_Of_Therapist = number_Of_Therapist;
    }

    public int getNumber_Of_Patient() {
        return Number_Of_Patient;
    }

    public void setNumber_Of_Patient(int number_Of_Patient) {
        Number_Of_Patient = number_Of_Patient;
    }
}
