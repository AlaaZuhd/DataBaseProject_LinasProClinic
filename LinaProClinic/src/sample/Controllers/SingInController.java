package sample.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import sample.DBConn;
import sample.DBInitializing;
import sample.SimpleMD5Example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static sample.Controllers.Controller.* ;
public class SingInController {


    private Connection con;

    @FXML
    private Button btnBack;

    @FXML
    private Label Wrong;

    @FXML
    private Button btnLogIn;

    @FXML
    private PasswordField txfPassword;

    @FXML
    private TextField txfUserName;


    @FXML
    void setLogIn(ActionEvent event) throws SQLException, ClassNotFoundException {


        if(txfUserName.getText().trim().length() == 3)
            setTLogin(event);
        else if(txfUserName.getText().trim().length() == 5)
            setPLogIn(event);
        else if(txfUserName.getText().equals("12@admin")) //admin
            setALogIn(event);
        else
            Wrong.setVisible(true);

    }

    void setALogIn(ActionEvent event) throws SQLException {
        Wrong.setVisible(false);
        //if an empty username or password, print an error statment
        if(txfUserName.getText().equals("") || txfPassword.getText().equals("")){
            Wrong.setVisible(true);
        }
        else {
            DBInitializing DB=new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            try {
                String A_ID = txfUserName.getText().trim(); //We are getting the username
                String password = txfPassword.getText().trim(); //getting the entered password
                // hassing for the password
                SimpleMD5Example s=new SimpleMD5Example();
                password = s.hash(password);
                //-----//
                // make a quesry to find if there is a patient with the same user name provided by the user
                String sql = "select * from lina_pro_clinic.admin where admin_ID= '" + A_ID + "';"; //this is the statement to be executed
                con = a.connectDB();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next() == false) { // no such patient with the same user name
                    Wrong.setVisible(true);
                } else { //one tuple at a time
                    //rs.getString(6) is col 6 from the therapist table, which is the passwor
                    if (rs.getString(5).equals(password)) { //if the entered password 'after hashing' is the same as database, open next stage
                        //Open next stage
                        // get a handle to the stage
                        Stage MainStage = (Stage) btnLogIn.getScene().getWindow(); // MainStage is the current stage
                        // do what you have to do
                        MainStage.close();
                        final Stage AboutUsStage = new Stage();
                        AboutUsStage.initModality(Modality.NONE);
                        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
                        AboutUsStage.initOwner(app_stage);
                        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/AdminView.fxml"));
                        Scene scene11 = new Scene(nextSceneParent);
                        AboutUsStage.setScene(scene11);
                        AboutUsStage.show();
                        stmt.close();
                        con.close();
                        rs.close();
                    } else {
                        Wrong.setVisible(true);
                    }
                }
            } catch (Exception e){
                Wrong.setVisible(true);
            }

            //-----//
        }
    }


    @FXML
    void setBack(ActionEvent event) throws IOException { // back to the home page
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

    //This is the sign in for patient
    void setPLogIn(ActionEvent event) throws SQLException, ClassNotFoundException {

        Wrong.setVisible(false);

        //if an empty username or password, print an error statment
        if(txfUserName.getText().equals("") || txfPassword.getText().equals("")){
            Wrong.setVisible(true);
        }
        else {
            DBInitializing DB=new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null ;
            ResultSet rs = null ;
            try {
                int P_SSN = Integer.parseInt(txfUserName.getText().trim()); //We are getting the username
                String P_password = txfPassword.getText().trim(); //getting the entered password
                // hassing for the password
                SimpleMD5Example s=new SimpleMD5Example();
                P_password = s.hash(P_password);
                //-----//
                // make a quesry to find if there is a patient with the same user name provided by the user
                String sql = "select * from lina_pro_clinic.patient where P_SSN=" + P_SSN + ";"; //this is the statement to be executed
                con = a.connectDB();
                stmt = con.createStatement();
                rs = stmt.executeQuery(sql);
                if (rs.next() == false) { // no such patient with the same user name
                    Wrong.setVisible(true);
                } else { //one tuple at a time
                    //rs.getString(6) is col 6 from the therapist table, which is the passwor
                    if (rs.getString(5).equals(P_password)) { //if the entered password 'after hashing' is the same as database, open next stage
                        //Open next stage
                        LoginP_SSN=P_SSN;
                        LoginP_Name=rs.getString(2);
                        LoginP_Gender=rs.getString(4);
                        // get a handle to the stage
                        Stage MainStage = (Stage) btnLogIn.getScene().getWindow(); // MainStage is the current stage
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
                    } else {
                        Wrong.setVisible(true);
                    }
                }
            } catch (Exception e){
                Wrong.setVisible(true);
            }finally {
                if(stmt != null){
                    stmt.close();
                    con.close();
                    rs.close();
                }
            }

            //-----//
        }

    }

    //This is the sign in for therapist

    void setTLogin(ActionEvent event) throws SQLException, ClassNotFoundException {
        Wrong.setVisible(false);
        //if an empty username or password, print an error statment
        if (txfUserName.getText().equals("") || txfPassword.getText().equals("")) {
            Wrong.setVisible(true);
        } else {
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            try {

                int T_SSN = Integer.parseInt(txfUserName.getText().trim()); //We are getting the username
                String T_password = txfPassword.getText().trim(); //getting the entered password
                // hassing for the password
                SimpleMD5Example s=new SimpleMD5Example();
                T_password = s.hash(T_password);
                //-----//
                // get a therapist with the same entered user name
                String sql = "select * from therapist_provide where T_SSN=" + T_SSN + ";"; //this is the statement to be executed
                con = a.connectDB();
                stmt = con.createStatement();
                rs = stmt.executeQuery(sql);
                if (rs.next() == false) { // no such therapist
                    Wrong.setVisible(true);
                } else {//one tuple at a time
                    //rs.getString(6) is col 6 from the therapist table, which is the passwor
                    if (rs.getString(6).equals(T_password)) { //if the entered password 'after hashing' same as database, open next stage
                        LoginT_SSN = T_SSN;
                        Namethearpist = rs.getString(1);

                        // get a handle to the stage
                        Stage MainStage = (Stage) btnLogIn.getScene().getWindow(); // MainStage is the current stage
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


                    } else {
                        Wrong.setVisible(true);
                    }
                }


            } catch (Exception e) {
                Wrong.setVisible(true);

            } finally {
                if (stmt != null) {
                    stmt.close();
                    con.close();
                    rs.close();
                }
            }
            //-----//
        }
    }


}
