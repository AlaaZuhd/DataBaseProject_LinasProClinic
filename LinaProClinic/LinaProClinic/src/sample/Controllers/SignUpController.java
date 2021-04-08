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
import sample.DBConn;
import sample.DBInitializing;
import sample.patient;
import sample.therapist_provide;

import java.net.URL;
import java.sql.Date;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class SignUpController implements Initializable {


    final String[] Therapy_Type = {""};

    @FXML
    private Button btnBack;

    @FXML
    private TextField txfPName;

    @FXML
    private TextField txfPUserName;

    @FXML
    private TextField txfPBD;

    @FXML
    private TextField txfPPhoneNumber;


    @FXML
    private PasswordField txfPPassword;

    @FXML
    private PasswordField txfTPassword;

    @FXML
    private TextField txfTName;

    @FXML
    private TextField txfTUserName;

    @FXML
    private TextField txfTBD;

    @FXML
    private TextField txfyears;

    @FXML
    private TextField txfTPhoneNumber;



    @FXML
    private Button btnPSignUp;

    @FXML
    private Button btnTSginup;

    @FXML
    private RadioButton rbPFemale;

    @FXML
    private RadioButton rbPMale;

    @FXML
    private MenuButton therapyType;

    @FXML
    private MenuItem speech;

    @FXML
    private MenuItem musical;

    @FXML
    private MenuItem occupational;

    @FXML
    private MenuItem behavioral;

    @FXML
    private Label TWrongYearOfExperince;

    @FXML
    private Label TWrongName;

    @FXML
    private Label TWrongUserName;

    @FXML
    private Label TWrongBirthDate;

    @FXML
    private Label TWrongPhoneNumber;

    @FXML
    private Label PWrongName;

    @FXML
    private Label PWrongUserName;

    @FXML
    private Label PWrongBirthDate;

    @FXML
    private Label PWrongPhoneNumber;

    @FXML
    private Label TWrongTherapyType;

    @FXML
    private Label PWrongGender;

    @FXML
    private Label TWrongPassword;

    @FXML
    private Label PWrongPassword;

    @FXML
    private Label TWrongSSN1;

    @FXML
    private Label TWrongSSN2;

    @FXML
    private Label PWrongSSN11;

    @FXML
    private Label PWrongSSN21;

    @FXML
    private ToggleGroup tG;


    private Connection con;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<MenuItem> items = therapyType.getItems();
        for (MenuItem item : items) {
            item.setOnAction(e -> {
                        Therapy_Type[0] = item.getText().trim();
                        System.out.print(Therapy_Type[0]);
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
        System.out.println("YAY\n\n");
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/sample.fxml"));
        System.out.println("WTF");
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
            flag = true;
        }

        int P_SSN = -1;
        try {
            P_SSN = Integer.parseInt(txfPUserName.getText().trim());
        } catch (Exception e) {
            PWrongUserName.setVisible(true);
            flag = true;
        }

        Date P_Birth_Date = null;
        try {
            P_Birth_Date = Date.valueOf(txfPBD.getText().trim());
            java.util.Date todayDate= Calendar.getInstance().getTime();
            if(P_Birth_Date.after(todayDate)) {
                throw new Exception("Date greater than today!!");
            }
        } catch (Exception e) {
            PWrongBirthDate.setVisible(true);
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
        System.out.println("Gender"+P_Gender);




        String P_Password = "";
        try {
            P_Password = txfPPassword.getText().trim();
            if (P_Password.length() == 0) throw new Exception("Not valid pass");
        } catch (Exception e) {
            PWrongPassword.setVisible(true);
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
                System.out.println("Insert into lina_pro_clinic.patient (P_SSN, P_Name, P_BirthDate,P_Gender,P_Password,P_Phone_Num) values('" + temp.getP_SSN() + "','" + temp.getP_Name() + "','" + temp.getP_Birth_Date() + "','" + temp.getP_Gender() + "'," + temp.getP_password() + "," + temp.getP_Phone_Num() + ");");
                String sql4 = "Insert into lina_pro_clinic.patient (P_SSN, P_Name, P_BirthDate,P_Gender,P_Password,P_Phone_Num) values('" + temp.getP_SSN() + "','" + temp.getP_Name() + "','" + temp.getP_Birth_Date() + "','" + temp.getP_Gender() + "'," + temp.getP_password() + "," + temp.getP_Phone_Num() + ");";
                DB.ExecuteUpdateStatement(sql4);
                // get a handle to the stage
                Stage MainStage = (Stage) btnPSignUp.getScene().getWindow(); // MainStage is the current stage
                // do what you have to do
                MainStage.close();
                final Stage AboutUsStage = new Stage();
                AboutUsStage.initModality(Modality.NONE);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
                AboutUsStage.initOwner(app_stage);
                System.out.println("YAY\n\n");
                Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/PatientView.fxml"));
                System.out.println("WTF");
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
            txfPName.clear();
            txfPUserName.clear();
            txfPPassword.clear();
            txfPBD.clear();
            txfPPhoneNumber.clear();

        }
    }



    //Therapist sign up for a new account
    @FXML
    void setTSignup(ActionEvent event) throws SQLException, ClassNotFoundException {
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
            flag = true ;
        }
        int T_SSN = -1;
        try {
            T_SSN = Integer.parseInt(txfTUserName.getText().trim());
        }
        catch (Exception e) {
            TWrongUserName.setVisible(true);
            flag = true ;
        }
        Date T_Birth_Date = null;
        try {
            T_Birth_Date = Date.valueOf(txfTBD.getText().trim());
            java.util.Date todayDate= Calendar.getInstance().getTime();
            if(T_Birth_Date.after(todayDate))
                throw new Exception("Date greater than today!!");
        }catch (Exception e){
            TWrongBirthDate.setVisible(true);
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
                T_Session_Rate = Integer.parseInt(rs1.getString(2));
            }
        } catch (Exception e) {
            TWrongYearOfExperince.setVisible(true);
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
            flag = true ;
        }

        String T_Password = "";
        try {
            T_Password = txfTPassword.getText().trim();
            if(T_Password.length() == 0 ) throw new Exception("Not valid pass");
        } catch (Exception e){
            TWrongPassword.setVisible(true);
            flag = true ;
        }

        int Therapy_ID  = -1 ;
        Statement stmt2 = null ;
        ResultSet rs2 = null ;
        try {
            String sql2="select therapy_ID from therapy_id t where t.Name="+"'" + Therapy_Type[0] +"'" + ";"; //this is the statement to be executed
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
                System.out.print(Therapy_ID);
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
            }
            else {//one tuple at a time
                TWrongSSN1.setVisible(true);
                TWrongSSN2.setVisible(true);
            }
            stmt3.close();
            con.close();
            rs3.close();

        }
        else {
            txfTName.clear();
            txfTUserName.clear();
            txfTPassword.clear();
            txfTBD.clear();
            txfTPhoneNumber.clear();
            txfyears.clear();
        }
    }

}

