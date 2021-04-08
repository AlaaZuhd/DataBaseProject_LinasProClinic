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
import sample.DBConn;
import sample.DBInitializing;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SingInController {

    public static int LoginP_SSN=0;

    private Connection con;

    @FXML
    private Label Twrong;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnTLogin;

    @FXML
    private TextField txfPUserNameLogin;

    @FXML
    private TextField txfTUserNameLogin;

    @FXML
    private PasswordField txfPPasswordLogin;

    @FXML
    private PasswordField txfTPasswordLogin;

    @FXML
    private Button btnPLogin;

    @FXML
    private Label Pwrong;

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

    //This is the sign in for patient
    @FXML
    void setPLogIn(ActionEvent event) throws SQLException, ClassNotFoundException {

        Pwrong.setVisible(false);
        //if an empty username or password, print an error statment
        if(txfPUserNameLogin.getText().equals("") || txfPPasswordLogin.getText().equals("")){
            System.out.print("Should not print\n\n");
            Pwrong.setVisible(true);
        }
        else {
            System.out.print("Should not print1\n\n");
            DBInitializing DB=new DBInitializing();  //connecting to database
            System.out.print("Should not print2\n\n");
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            System.out.print("Should not print222\n\n");
            Statement stmt = null ;
            ResultSet rs = null ;
            try {
                int P_SSN = Integer.parseInt(txfPUserNameLogin.getText().trim()); //We are getting the username
                String P_password = txfPPasswordLogin.getText().trim(); //getting the entered password
                //-----//
                String sql = "select * from lina_pro_clinic.patient where P_SSN=" + P_SSN + ";"; //this is the statement to be executed
                con = a.connectDB();
                stmt = con.createStatement();
                System.out.print("Should not print333333333333333\n\n");
                rs = stmt.executeQuery(sql);
                System.out.print("Should not print3\n\n");
                if (rs.next() == false) {
                    Pwrong.setVisible(true);
                } else {//one tuple at a time
                    //rs.getString(6) is col 6 from the therapist table, which is the passwor
                    if (rs.getString(5).equals(P_password)) { //if the entered password same as database, open next stage
                        System.out.println("TAY 444");
                        //Open next stage
                        LoginP_SSN=P_SSN;
                        // get a handle to the stage
                        Stage MainStage = (Stage) btnPLogin.getScene().getWindow(); // MainStage is the current stage
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
                    } else {
                        Pwrong.setVisible(true);
                    }
                }
            } catch (Exception e){
                Pwrong.setVisible(true);
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
    @FXML
    void setTLogin(ActionEvent event) throws SQLException, ClassNotFoundException {
        Twrong.setVisible(false);
        //if an empty username or password, print an error statment
        if(txfTUserNameLogin.getText().equals("") || txfTPasswordLogin.getText().equals("")){
            Twrong.setVisible(true);
        }
        else {
            DBInitializing DB=new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null ;
            ResultSet rs = null ;
            try{

                int T_SSN = Integer.parseInt(txfTUserNameLogin.getText().trim()); //We are getting the username
                String T_password=txfTPasswordLogin.getText().trim(); //getting the entered password
                //-----//
                String sql="select * from therapist_provide where T_SSN="+T_SSN+";"; //this is the statement to be executed
                con = a.connectDB();
                stmt = con.createStatement();
                rs = stmt.executeQuery(sql);
                if (rs.next() == false){
                    Twrong.setVisible(true);
                }
                else {//one tuple at a time
                    //rs.getString(6) is col 6 from the therapist table, which is the passwor
                    System.out.print(rs.getString(6) + " " + T_password + "\n");
                    if(rs.getString(6).equals(T_password)){ //if the entered password same as database, open next stage
                        System.out.println("TAY");
                    }
                    else {
                        Twrong.setVisible(true);
                    }
                }


            }catch (Exception e){
                Twrong.setVisible(true);

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



}
