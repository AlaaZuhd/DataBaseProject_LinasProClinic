package sample.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;
import sample.schedule;
import sample.therapy_therapist_patient;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

import static sample.Controllers.Controller.LoginP_SSN;
import static sample.Controllers.SingInController.*;

public class AboutUsController implements Initializable {

    private Connection con;


    // for the nw pane
    @FXML
    private Button btnGetStatistics;

    @FXML
    private BarChart<?, ?> therapistChart;

    @FXML
    private BarChart<?, ?> patientChart;

    @FXML
    private Pane PaneStatistics;

    @FXML
    private NumberAxis therapistY;


    @FXML
    private CategoryAxis therapistX;


    @FXML
    private CategoryAxis patientX;

    @FXML
    private NumberAxis patientY;



    @FXML
    private Button btnBack;

    @FXML
    private Label label2;

    @FXML
    private TextArea textArea1;

    @FXML
    private TextArea textArea2;

    @FXML
    private TextArea textArea3;

    @FXML
    private TextArea textArea4;

    @FXML
    private Label label1;

    @FXML
    private Label label3;

    @FXML
    private Label label4;


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


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // getting data ready to display in the initialize
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM lina_pro_clinic.therapy;";
        try {
            con = a.connectDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            stmt = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            rs = stmt.executeQuery(sql); // get information associated with each therapy type
            if(rs.next() == false) {
                System.out.print("Error!!\n");
            }
            else {
                int i = 0 ;
                do {
                    String therapyType = rs.getString(3);
                    String therapyDescription = rs.getString(4);
                    if(i==0){ // to display information of the first therapy
                        label1.setText(therapyType + " Therapy");
                        textArea1.setText(therapyDescription);
                    }else if(i==1){ // to display information of the second therapy
                        label2.setText(therapyType + " Therapy");
                        textArea2.setText(therapyDescription);
                    }else if(i==2){ // to display information of the third therapy
                        label3.setText(therapyType  + " Therapy");
                        textArea3.setText(therapyDescription);
                    }else if(i==3){ // to display information of the fourth therapy
                        label4.setText(therapyType  + " Therapy");
                        textArea4.setText(therapyDescription);
                    }
                    i++ ;
                }while(rs.next()==true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
