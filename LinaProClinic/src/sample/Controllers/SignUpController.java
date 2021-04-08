package sample.Controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import static sample.Controllers.Controller.* ;

public class SignUpController implements Initializable {


    final String[] Therapy_Type = {""};

    @FXML
    private Label TWrongSSN1;

    @FXML
    private RadioButton rbPMale;

    @FXML
    private Label TWrongSSN2;

    @FXML
    private Label TWrongTherapyType;

    @FXML
    private Label PWrongSSN11;

    @FXML
    private Label PWrongPhoneNumber;

    @FXML
    private CheckBox boxMusical;

    @FXML
    private Button btnTSginup;


    @FXML
    private CheckBox boxBehavioral;

    @FXML
    private TextField txfPUserName;

    @FXML
    private Label TWrongUserName;

    @FXML
    private ToggleGroup tG;

    @FXML
    private CheckBox boxOccupational;

    @FXML
    private PasswordField txfPPassword;

    @FXML
    private MenuItem behavioral;

    @FXML
    private TextField txfPName;

    @FXML
    private TextField txfTUserName;

    @FXML
    private RadioButton rbPFemale;

    @FXML
    private TextField txfTName;

    @FXML
    private Label TWrongName;

    @FXML
    private Label TWrongPhoneNumber;

    @FXML
    private TextField txfPPhoneNumber;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnPSignUp;

    @FXML
    private Label PWrongSSN21;

    @FXML
    private PasswordField txfTPassword;

    @FXML
    private MenuItem musical;

    @FXML
    private Label PWrongBirthDate;

    @FXML
    private MenuItem occupational;

    @FXML
    private TextField txfTPhoneNumber;

    @FXML
    private MenuButton therapyType;

    @FXML
    private Label TWrongYearOfExperince;

    @FXML
    private TextField txfyears;

    @FXML
    private Label PWrongPassword;

    @FXML
    private CheckBox boxSpeech;

    @FXML
    private Label PWrongGender;

    @FXML
    private MenuItem speech;

    @FXML
    private Label TWrongBirthDate;

    @FXML
    private Label TWrongPassword;

    @FXML
    private Label PWrongUserName;

    @FXML
    private Label PWrongName;


    private Connection con;

    @FXML
    private DatePicker txfTBD;

    @FXML
    private DatePicker txfPBD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<MenuItem> items = therapyType.getItems();
        for (MenuItem item : items) {
            item.setOnAction(e -> {
                        Therapy_Type[0] = item.getText().trim();
                        therapyType.setText(Therapy_Type[0]);
                    }
            );
        }

    }


    @FXML
    void setBack(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage MainStage = (Stage) btnBack.getScene().getWindow(); // MainStage is the current stage
        // do what you have to do
        MainStage.close();
        final Stage AboutUsStage = new Stage();
        AboutUsStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        AboutUsStage.initOwner(app_stage);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/sample.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        AboutUsStage.setScene(scene11);
        AboutUsStage.show();
    }


    @FXML
    void setPSignup(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        PWrongBirthDate.setVisible(false);
        PWrongUserName.setVisible(false);
        PWrongPhoneNumber.setVisible(false);
        PWrongPassword.setVisible(false);
        PWrongSSN11.setVisible(false);
        PWrongSSN21.setVisible(false);
        PWrongGender.setVisible(false);


        boolean flag = false;
        /////////////////////////Need to check the textfield
        //-----//
        String P_Name = "";
        try {
            P_Name = txfPName.getText().trim();
            if (P_Name.length() == 0)
                throw new Exception("Empty");
        } catch (Exception e) {
            PWrongName.setVisible(true);
            txfPName.clear();
            flag = true;
        }

        int P_SSN = -1;
        try {
            if(txfPUserName.getText().trim().length() != 5 ) throw new Exception("Need 5 digits");
            P_SSN = Integer.parseInt(txfPUserName.getText().trim());
        } catch (Exception e) {
            PWrongUserName.setVisible(true);
            txfPUserName.clear();
            flag = true;
        }

        LocalDate P_Birth_Date = null;
        try {
            P_Birth_Date = txfPBD.getValue();
            java.util.Date B_date= java.sql.Date.valueOf(P_Birth_Date);
            java.util.Date todayDate = Calendar.getInstance().getTime(); //Today's Date
            if (B_date.after(todayDate)) {
                throw new Exception("Date greater than today!!");
            }
        } catch (Exception e) {
            PWrongBirthDate.setVisible(true);
            txfPBD.getEditor().clear();
            flag = true;
        }
        String P_Gender="";
        if(rbPFemale.isSelected())
            P_Gender="Female";
        else if (rbPMale.isSelected())
            P_Gender="Male";
        else {
            PWrongGender.setVisible(true);
            flag = false;
        }
        String P_Password = "";
        try {
            P_Password = txfPPassword.getText().trim();
            if (P_Password.length() == 0) throw new Exception("Not valid pass");
            // hassing for the password
            SimpleMD5Example s=new SimpleMD5Example();
            P_Password = s.hash(P_Password);
        } catch (Exception e) {
            PWrongPassword.setVisible(true);
            txfPPassword.clear();
            flag = true;
        }
        String P_Phone_Num = "";
        try {
            P_Phone_Num = txfPPhoneNumber.getText().trim();
            if (!(P_Phone_Num.matches("[0-9]+")))
                throw new Exception("Not Valid");
        } catch (Exception e) {
            PWrongPhoneNumber.setVisible(true);
            flag = true;
        }
        if (!flag) {
            DBInitializing DB=new DBInitializing();  //connecting to database
            String sql3 = "SELECT P_SSN FROM lina_pro_clinic.patient P where P.P_SSN = " + P_SSN + " ;";
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            con = a.connectDB();
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery(sql3);

            if (rs3.next() == false) {
                patient temp = new patient(P_SSN, P_Name, P_Birth_Date,P_Gender,P_Password,P_Phone_Num);
                String sql4 = "Insert into lina_pro_clinic.patient (P_SSN, P_Name, P_BirthDate,P_Gender,P_Password,P_Phone_Num) values('" + temp.getP_SSN() + "','" + temp.getP_Name() + "','" + temp.getP_Birth_Date() + "','" + temp.getP_Gender() + "','" + temp.getP_password() + "'," + temp.getP_Phone_Num() + ");";
                DB.ExecuteUpdateStatement(sql4);
                /////////////////////////////////////////////////////////////////////////////////////
                // we are getting the therapy type of the patient
                if(boxSpeech.isSelected()) {
                    int TherapyID = gettingTherapyID("Speech");
                    if(TherapyID == 0){
                    }
                    // insert into takes
                    insertIntoTakes(TherapyID, P_SSN);
                    // insert into has
                    insertIntoHas(TherapyID,P_SSN);
                }
                if(boxOccupational.isSelected()){
                    int TherapyID = gettingTherapyID("Occupational");
                    if(TherapyID == 0){
                    }
                    // insert into takes
                    insertIntoTakes(TherapyID, P_SSN);
                    // insert into has
                    insertIntoHas(TherapyID,P_SSN);
                }
                if(boxMusical.isSelected()){
                    int TherapyID = gettingTherapyID("Musical");
                    if(TherapyID == 0){
                    }
                    // insert into takes
                    insertIntoTakes(TherapyID, P_SSN);
                    // insert into has
                    insertIntoHas(TherapyID,P_SSN);
                }
                if(boxBehavioral.isSelected()){
                    int TherapyID = gettingTherapyID("Behavioral");
                    if(TherapyID == 0){
                    }
                    // insert into takes
                    insertIntoTakes(TherapyID, P_SSN);
                    // insert into has
                    insertIntoHas(TherapyID,P_SSN);
                }

                LoginP_SSN = P_SSN ;
                LoginP_Name = P_Name ;
                LoginP_Gender = P_Gender;

                // get a handle to the stage
                Stage MainStage = (Stage) btnPSignUp.getScene().getWindow(); // MainStage is the current stage
                // do what you have to do
                MainStage.close();
                final Stage AboutUsStage = new Stage();
                AboutUsStage.initModality(Modality.NONE);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
                AboutUsStage.initOwner(app_stage);
                Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/PatientPage.fxml"));
                Scene scene11 = new Scene(nextSceneParent);
                AboutUsStage.setScene(scene11);
                AboutUsStage.show();
            }
            else {
                PWrongSSN11.setVisible(true);
                PWrongSSN21.setVisible(true);
            }
            stmt3.close();
            con.close();
            rs3.close();

        }
        else {
//            txfPName.clear();
//            txfPUserName.clear();
//            txfPPassword.clear();
//            txfPPhoneNumber.clear();

        }
    }


    int gettingTherapyID(String therapyType) throws SQLException, ClassNotFoundException {
        int therapyID = 0 ;
        DBInitializing DB=new DBInitializing();  //connecting to database
        String sql = "SELECT * FROM lina_pro_clinic.therapy where Therapy_Type = '" + therapyType+ "';";
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        con = a.connectDB();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next() == false){
          //  System.out.print("Can't be false!!!");
        }
        else {
            therapyID = Integer.parseInt(rs.getString(1));
        }

        stmt.close();
        con.close();
        rs.close();

        return therapyID ;
    }

    void insertIntoTakes(int TherapyID , int P_SSN) throws SQLException, ClassNotFoundException {
        DBInitializing DB=new DBInitializing();  //connecting to database
        String sql = "insert into lina_pro_clinic.takes(P_SSN , Therapy_ID) values('" + P_SSN + "','" + TherapyID + "');";
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        con = a.connectDB();
        Statement stmt = con.createStatement();
        DB.ExecuteUpdateStatement(sql);

        stmt.close();
        con.close();

    }

    void insertIntoHas(int TherapyID , int P_SSN) throws SQLException, ClassNotFoundException {
        // trying to find a therapist with no patient
        int T_SSN = 1 ;
        String sql = "select t.T_SSN from lina_pro_clinic.therapist_provide t where t.Therapy_ID= '" + TherapyID + "' AND t.T_SSN Not in(select h.T_SSN from lina_pro_clinic.has h);";
        DBInitializing DB=new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        con = a.connectDB();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        // if there is one or more therapist with no patient, then the first therapist will take the patient
        if(rs.next() == true){
            T_SSN = Integer.parseInt(rs.getString(1));
            sql = "insert into lina_pro_clinic.has (P_SSN,T_SSN) values('" + P_SSN + "','" + T_SSN + "');";
            //Statement stmt1 = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
        }
        else { // if there is no therapist with no patient, then we will get the therapist with the minimum number of patients
            int min = Integer.MAX_VALUE;
            sql = "select count(h.P_SSN),h.T_SSN " +
                    "from lina_pro_clinic.has h,lina_pro_clinic.therapist_provide t " +
                    "where h.T_SSN=t.T_SSN AND " +
                    "t.Therapy_ID='" + TherapyID + "'" +
                    "group by h.T_SSN;";
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next() == false) { // we have to think in this and how to avoid this
               // System.out.print("No Therapist is available !!!");
            } else {
                do {
                    if (Integer.parseInt(rs.getString(1)) < min) {
                        T_SSN = Integer.parseInt(rs.getString(2));
                    }
                } while (rs.next() == true);
                // now insert into has
                sql = "insert into lina_pro_clinic.has (P_SSN,T_SSN) values('" + P_SSN + "','" + T_SSN + "');";
                //Statement stmt1 = con.createStatement();
                DB.ExecuteUpdateStatement(sql);
            }
        }
        stmt.close();
        con.close();
        rs.close();
    }


    //Therapist sign up for a new account
    @FXML
    void setTSignup(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, ParseException {
        TWrongBirthDate.setVisible(false);
        TWrongName.setVisible(false);
        TWrongUserName.setVisible(false);
        TWrongTherapyType.setVisible(false);
        TWrongYearOfExperince.setVisible(false);
        TWrongPhoneNumber.setVisible(false);
        TWrongPassword.setVisible(false);
        TWrongSSN1.setVisible(false);
        TWrongSSN2.setVisible(false);


        boolean flag = false ;
        /////////////////////////Need to check the textfields
        //getting the rate for the entered number years of experience
        //-----//
        String T_Name  = "";
        try {
            T_Name = txfTName.getText().trim();
            if(T_Name.length() == 0)
                throw new Exception("Empty");
        } catch (Exception e){
            TWrongName.setVisible(true);
            txfTName.clear();
            flag = true ;
        }
        int T_SSN = -1;
        try {
            if(txfTUserName.getText().trim().length() != 3) throw  new Exception("Need 3 digits");
            T_SSN = Integer.parseInt(txfTUserName.getText().trim());
        }
        catch (Exception e) {
            TWrongUserName.setVisible(true);
            txfTUserName.clear();
            flag = true ;
        }
        LocalDate T_Birth_Date=null;
        try {
            T_Birth_Date = txfTBD.getValue();
            java.util.Date B_date= java.sql.Date.valueOf(T_Birth_Date);
            java.util.Date todayDate = Calendar.getInstance().getTime(); //Today's Date
            int y=T_Birth_Date.getYear();
            if (B_date.after(todayDate)) {
                throw new Exception("Date greater than today!!");
            }
            if(y>=2000){
                throw new Exception("How did you graduate!!");
            }
        }catch (Exception e){
            TWrongBirthDate.setVisible(true);
            txfTBD.getEditor().clear();
            flag = true ;
        }

        double T_Session_Rate = 0 ;
        DBInitializing DB=new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt1 = null;
        ResultSet rs1 = null;
        try {
            int yearsOfExperience=Integer.parseInt(txfyears.getText().trim());
            String sql1="select Session_Rate from session_rate s where s.Years_of_Experience="+yearsOfExperience+";"; //this is the statement to be executed
            con = a.connectDB();
            stmt1 = con.createStatement();
            rs1 = stmt1.executeQuery(sql1);
            if(rs1.next()==false){//if the years of experience are more than 25, then the rate = 300 by default
                if(yearsOfExperience < 0 ){
                    TWrongYearOfExperince.setVisible(true);
                    flag = true ;
                }
                else {
                    T_Session_Rate = 300;
                }
            }
            else {
                T_Session_Rate = Integer.parseInt(rs1.getString(1));
            }
        } catch (Exception e) {
            TWrongYearOfExperince.setVisible(true);
            txfyears.clear();
            flag = true ;
        }finally {
            if(stmt1 != null) {
                stmt1.close();
                con.close();
                rs1.close();
            }
        }

        String T_Phone_Num = "";
        try {
            T_Phone_Num = txfTPhoneNumber.getText().trim();
            if(!(T_Phone_Num.matches("[0-9]+")))
                throw new Exception("Not Valid");
        }catch (Exception e){
            TWrongPhoneNumber.setVisible(true);
            txfTPhoneNumber.clear();
            flag = true ;
        }

        String T_Password = "";
        try {
            T_Password = txfTPassword.getText().trim();
            if(T_Password.length() == 0 ) throw new Exception("Not valid pass");
            // hassing for the password
            SimpleMD5Example s=new SimpleMD5Example();
            T_Password = s.hash(T_Password);
        } catch (Exception e){
            TWrongPassword.setVisible(true);
            txfTPassword.clear();
            flag = true ;
        }

        int Therapy_ID  = -1 ;
        Statement stmt2 = null ;
        ResultSet rs2 = null ;
        try {
            String sql2="select Therapy_ID from lina_pro_clinic.therapy t where t.Therapy_Type="+"'" + Therapy_Type[0] +"'" + ";"; //this is the statement to be executed
            a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            con = a.connectDB();
            stmt2 = con.createStatement();
            rs2 = stmt2.executeQuery(sql2);

            if(rs2.next()==false){//if the years of experience are more than 25, then the rate = 300 by default
                TWrongTherapyType.setVisible(true);
                flag = true ;
            }
            else {
                Therapy_ID = Integer.parseInt(rs2.getString(1)); // get the therapy_id
            }
        }catch (Exception e){
            TWrongTherapyType.setVisible(true);
            flag = true ;
        }finally {
            if(stmt2 != null) {
                stmt2.close();
                con.close();
                rs2.close();
            }
        }
        if(!flag){
            String sql3 = "SELECT T_SSN FROM lina_pro_clinic.therapist_provide t where t.T_SSN = " + T_SSN + " ;" ;
            a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            con = a.connectDB();
            Statement stmt3 = con.createStatement();
            ResultSet rs3 = stmt3.executeQuery(sql3);

            if (rs3.next() == false){
                therapist_provide temp = new therapist_provide(T_Name,T_SSN,Therapy_ID,T_Session_Rate,T_Password,T_Phone_Num,T_Birth_Date);
                String sql4 = "Insert into lina_pro_clinic.therapist_provide (T_Name, T_SSN, Therapy_ID,T_Session_Rate,T_Password,T_Phone_Num,T_Birth_Date) values('" + temp.getT_Name() + "'," + temp.getT_SSN() + "," + temp.getTherapy_ID() + "," + temp.getT_Session_Rate() + ",'" + temp.getT_password() +"','" + temp.getT_Phone_Num() + "','" + temp.getT_Birth_Date() + "');";
                DB.ExecuteUpdateStatement(sql4);
                LoginT_SSN = T_SSN ;
                Namethearpist = "";
                Namethearpist = T_Name;
                upDateAvailable(LoginT_SSN);

                // get a handle to the stage
                Stage MainStage = (Stage) btnPSignUp.getScene().getWindow(); // MainStage is the current stage
                // do what you have to do
                MainStage.close();
                final Stage AboutUsStage = new Stage();
                AboutUsStage.initModality(Modality.NONE);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
                AboutUsStage.initOwner(app_stage);
                Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/TherapistView.fxml"));
                Scene scene11 = new Scene(nextSceneParent);
                AboutUsStage.setScene(scene11);
                AboutUsStage.show();
            }
            else {//one tuple at a time
                TWrongSSN1.setVisible(true);
                TWrongSSN2.setVisible(true);
            }
            stmt3.close();
            con.close();
            rs3.close();

        }
//        else {
//            txfTName.clear();
//            txfTUserName.clear();
//            txfTPassword.clear();
//            txfTPhoneNumber.clear();
//            txfyears.clear();
//        }
    }
    void upDateAvailable(int T_SSN) throws FileNotFoundException, ParseException, SQLException, ClassNotFoundException, FileNotFoundException {
        // get start date
        java.util.Date date11 = new java.util.Date();
        LocalDate localDate = date11.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        int day = localDate.getDayOfMonth();
        String todayDate = year + "-" ;
        if(month/10 ==0) todayDate += "0" + month + "-";
        else todayDate += month + "-";
        if(day/10 == 0) todayDate += "0" + day ;
        else todayDate += day ;

        // get last modification date
        String lastModificationDate11 ;
        File file = new File("src/sample/lastModificationDate.txt");
        Scanner scan = new Scanner(file);
        lastModificationDate11 = scan.nextLine();

        java.util.Date lastModificationDate1=new SimpleDateFormat("yyyy-MM-dd").parse(lastModificationDate11);
        localDate = lastModificationDate1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        month = localDate.getMonthValue();
        year = localDate.getYear();
        day = localDate.getDayOfMonth();
        String lastModificationDate = year + "-" ;
        if(month/10 ==0) lastModificationDate += "0" + month + "-";
        else lastModificationDate += month + "-";
        if(day/10 == 0) lastModificationDate += "0" + day ;
        else lastModificationDate += day ;

        // end date = lastModificationDate + 30


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(todayDate, dtf);
        LocalDate date2 = LocalDate.parse(lastModificationDate, dtf);
        // end date
        LocalDate date3 = date2.plusDays(30); // end date
        // number of days needed to be added into available
        long days = ChronoUnit.DAYS.between(date1, date3);
        // insert available times for this therapist
        String sql = "insert into lina_pro_clinic.available  (A_Date,A_Start_Time,A_Request,A_Approve,T_SSN)" +
                " values ";
        date2 = date1 ;
        for(int i=0; i<days; i++){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date2 = date2.plusDays(1);
            // add available time from 8:00Am to 2:00PM
            int Time = 8;
            for (int j = 0; j < 8; j++) {
                if (Time > 12) Time = 1;
                String timeFormat = "";
                if (Time / 10 == 0) timeFormat += "0" + Time + ":";
                else timeFormat = Time + ":";
                timeFormat += "00:00";
                if(j == 7 && i == days-1)
                    sql += "  ('" + date2 + "','" + timeFormat + "','0','0','" + T_SSN + "') ;";
                else
                    sql += "  ('" + date2 + "','" + timeFormat + "','0','0','" + T_SSN + "') ,";
                Time++;
            }
        }
        System.out.println(sql);
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        con = a.connectDB();
        DB.ExecuteUpdateStatement(sql);

    }
}

