package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.DBConn;
import sample.DBInitializing;
import sample.schedule;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import static sample.Controllers.SingInController.LoginP_SSN;

public class PatientViewController implements Initializable {

    public static ArrayList<schedule> data = new ArrayList<>(); // to store the resulted data from the query

    private ObservableList<schedule> dataList; // to add data into the table view

    @FXML
    protected Label LabelName;

    private Connection con;

    @FXML
    protected TableView<schedule> MStableView;

    @FXML
    protected VBox SidePanel;

    @FXML
    protected Button BtnPersonalInformarion;

    @FXML
    protected Button BtnMySchedule;

    @FXML
    protected Button BtnPersonalInformarion1;

    @FXML
    protected AnchorPane PanePersonalInformation;

    @FXML
    protected Label PILabelName;

    @FXML
    protected Label PILabelSSN;

    @FXML
    protected TextField PITxfPhone;

    @FXML
    protected Label PILabelOldPass;

    @FXML
    protected PasswordField PIOldPass;

    @FXML
    protected Label PILabelNewPass;

    @FXML
    protected PasswordField PINewPass;

    @FXML
    protected TextField PITxfGender;

    @FXML
    protected DatePicker PIDate;

    @FXML
    protected TextField PITxfCamoanionName;

    @FXML
    protected TextField PITxfCampanionPhone;

    @FXML
    protected ImageView PIBtnEdit;

    @FXML
    protected Button PIBtnSave;

    @FXML
    protected AnchorPane PaneMySchedule;

    @FXML
    protected AnchorPane PaneHome;

    @FXML
    protected VBox BoxPassword;


    @FXML
    protected TableColumn<schedule, Date> MSSessionDate;


    @FXML
    protected VBox VBox1;

    @FXML
    protected TableColumn<schedule, Time> MSSessionTime;


    @FXML
    protected TableColumn<schedule, String> MSTherapistName;

    @FXML
    protected TableColumn<schedule, Integer> MSSessionID;


    @FXML
    protected TableColumn<schedule, Integer> MSTherapistID;


    //For personal Information choice//
    @FXML
    void setPersonalInformation(ActionEvent event) throws SQLException, ClassNotFoundException {
        //set all other panes to false
        PaneMySchedule.setVisible(false);
        PaneHome.setVisible(false);
        //set the needed pane to true
        PanePersonalInformation.setVisible(true);

        //get the information
        DBInitializing DB=new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null ;
        ResultSet rs = null ;
        String sql = "SELECT * FROM lina_pro_clinic.patient where P_SSN=" + LoginP_SSN + ";"; //this is the statement to be executed
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        //set the values obtained from the patient table
        if(rs.next()!=false) {
            BoxPassword.setVisible(false);
            PILabelNewPass.setVisible(false);
            PILabelOldPass.setVisible(false);
            PIOldPass.setVisible(false);
            PINewPass.setVisible(false);
            PILabelName.setText(""+rs.getString(2));  //name is the second column
            PITxfGender.setText(""+rs.getString(4));  //gender is the fourth column
            PILabelSSN.setText(""+rs.getString(1));  //SSN is the first column
            PITxfPhone.setText(""+rs.getString(6));

            PITxfGender.setEditable(false);
            PITxfPhone.setEditable(false);
            //BirthDate
        }
        stmt.close();
        con.close();
        rs.close();
        sql = "SELECT * FROM lina_pro_clinic.companion_accompany where P_SSN=" + LoginP_SSN + ";"; //this is the statement to be executed
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        //set the values obtained from the companion_accompany table
        if(rs.next()!=false){
            PITxfCamoanionName.setText(""+rs.getString(1));
            PITxfCampanionPhone.setText(""+rs.getString(2));
            PITxfCampanionPhone.setEditable(false);
            PITxfCamoanionName.setEditable(false);
        }
        stmt.close();
        con.close();
        rs.close();
    }

    @FXML
    void SetEdit(ActionEvent event) {
        BoxPassword.setVisible(true);
        PILabelNewPass.setVisible(true);
        PILabelOldPass.setVisible(true);
        PIOldPass.setVisible(true);
        PINewPass.setVisible(true);
        PITxfGender.setEditable(true);
        PITxfPhone.setEditable(true);
        PITxfCampanionPhone.setEditable(true);
        PITxfCamoanionName.setEditable(true);
        PITxfGender.setStyle("-fx-background-color : #fdfbf9;");
        PITxfPhone.setStyle("-fx-background-color : #fdfbf9;");
        PITxfCamoanionName.setStyle("-fx-background-color : #fdfbf9;");
        PITxfCampanionPhone.setStyle("-fx-background-color : #fdfbf9;");
    }

    @FXML
    void SetSave(ActionEvent event) throws SQLException, ClassNotFoundException {
        //check updates
        updatePhone();
        if(!PIOldPass.getText().trim().equals(""))
            updatePassword();
        updateNameCampanion();
        updatePhoneCampanion();


        //Set Editiable back to false
        BoxPassword.setVisible(false);
        PILabelNewPass.setVisible(false);
        PILabelOldPass.setVisible(false);
        PIOldPass.setVisible(false);
        PINewPass.setVisible(false);
        PITxfCampanionPhone.setEditable(false);
        PITxfCamoanionName.setEditable(false);
        PITxfGender.setEditable(false);
        PITxfPhone.setEditable(false);

    }
    public Boolean updateGender() throws SQLException, ClassNotFoundException {
        String newGender=PITxfGender.getText().trim();
        if(newGender.equalsIgnoreCase("Female")||newGender.equalsIgnoreCase("Male")||newGender.equalsIgnoreCase("Other")){
            DBInitializing DB=new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null ;
            ResultSet rs = null ;
            String sql = "update  lina_pro_clinic.patient p set P_Gender = '"+ newGender + "' where P_SSN = " + LoginP_SSN + ";"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
            stmt.close();
            con.close();
            return true;
        }
        else {
            (new Alert(Alert.AlertType.ERROR, "Wrong Gender")).show();
            return false;
        }
    }
    public Boolean updatePhone()  throws SQLException, ClassNotFoundException{
        String newPhone=PITxfPhone.getText().trim();
        if (newPhone.matches("[0-9]+")) {
            Integer newPhonee=Integer.parseInt(newPhone);
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "update lina_pro_clinic.patient p set P_Phone_Num = '" + newPhonee+"' where P_SSN = " + LoginP_SSN + ";"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
            stmt.close();
            con.close();
            return true;
        }
        else{
            (new Alert(Alert.AlertType.ERROR, "Wrong Phone Number")).show();
            return false;
        }
    }
    public Boolean updatePhoneCampanion()  throws SQLException, ClassNotFoundException{
        String newPhone=PITxfCampanionPhone.getText().trim();
        if (newPhone.matches("[0-9]+")) {
            Integer newPhonee=Integer.parseInt(newPhone);
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "update lina_pro_clinic.companion_accompany set C_Phone_Num = " + newPhonee+" where P_SSN = " + LoginP_SSN + ";"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
            System.out.println("HERE");
            stmt.close();
            con.close();
            return true;
        }
        else{
            (new Alert(Alert.AlertType.ERROR, "Wrong Campanion Phone Number")).show();
            return false;
        }
    }
    public Boolean updateNameCampanion()  throws SQLException, ClassNotFoundException{
        String newName=PITxfCamoanionName.getText().trim();

        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "update lina_pro_clinic.companion_accompany set C_Name= '" + newName+"' where P_SSN = " + LoginP_SSN + ";"; //this is the statement to be executed
        con = a.connectDB();
        stmt = con.createStatement();
        DB.ExecuteUpdateStatement(sql);
        stmt.close();
        con.close();
        return true;
    }
    public Boolean updatePassword()  throws SQLException, ClassNotFoundException{
        String oldPass=PIOldPass.getText().trim();
        String newPass=PINewPass.getText().trim();
        if(newPass.equals("")){
            (new Alert(Alert.AlertType.ERROR, "Wrong New Password")).show();
            return false;
        }
        //check if old password matches the one in database
        DBInitializing DB=new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null ;
        ResultSet rs = null ;
        String sql = "select * from lina_pro_clinic.patient where P_SSN=" + LoginP_SSN + ";"; //this is the statement to be executed
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        if (rs.next() == false) {
            (new Alert(Alert.AlertType.ERROR, "Wrong Old Password")).show();
            return false;
        } else {
            if (rs.getString(5).equals(oldPass)) { //if same old password, update the database with the new password
                sql = "update lina_pro_clinic.patient p set P_Password = '" + newPass + "' where P_SSN = " + LoginP_SSN + ";";
                DB.ExecuteUpdateStatement(sql);
            }
        }
        stmt.close();
        rs.close();
        return true;
    }





    //Here Personal Information Choice ends//

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LabelName.setText(""+LoginP_SSN);
        //Set all panes to visible false
        PanePersonalInformation.setVisible(false);
        PaneMySchedule.setVisible(false);
        PaneHome.setVisible(true);

    }

    @FXML
    void setMySchedule(ActionEvent event) throws SQLException {
        // mark all sub-pans as not visible
        PanePersonalInformation.setVisible(false);
        PaneHome.setVisible(false);
        // mark PaneMySchedule as visible
        PaneMySchedule.setVisible(true);
//        MyScheduleSubController obj1 = new MyScheduleSubController();
//        obj1.setMyScheduleSubController();
        //        // mark all sub-pans as not visible
//        PanePersonalInformation.setVisible(false);
//        PaneHome.setVisible(false);
//        // mark PaneMySchedule as visible
//        PaneMySchedule.setVisible(true);
        // execute the needed query

        DBInitializing DB=new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null ;
        ResultSet rs = null ;
        try {

            java.util.Date todayDate= Calendar.getInstance().getTime();

            String sql2 = "select S.S_ID , S.S_Session_Date , S.S_Start_Time , A.T_SSN , T.T_Name" +
                    " from therapist_provide T , session S , attend A" +
                    " where A.P_SSN=" + LoginP_SSN + " and A.S_ID=S.S_ID and A.T_SSN=T.T_SSN;"; //this is the statement to be executed
            System.out.println("before");
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql2);
            System.out.println("after");
            if (rs.next() == false) {
                System.out.println("No reserved session!");
            }
            else {//one tuple at a time
                do{
//                    System.out.println(rs.getString(1) );
//                    System.out.print(" " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
                    int SessionID = Integer.parseInt(rs.getString(1));
                    Date date = Date.valueOf(rs.getString(2));
                    Time StartTime = Time.valueOf((rs.getString(3)));
                    int TherapistSSN = Integer.parseInt(rs.getString(4));
                    String TherapistName = rs.getString(5);
                    if(!date.before(todayDate)){ // if the session date is equal or large than th current date then take it
                        schedule s = new schedule(SessionID,date,StartTime,TherapistSSN,TherapistName);
                        data.add(s); // add data into data list
                        System.out.println(s.toString());
                    }
                }while(rs.next());
                // add data into the table view
                showTable(data);
            }
        } catch (Exception e) {
            System.out.println("Exception!!!!!!!!!!!!!11");
            System.out.println(e.getMessage());
        } finally {
            if(stmt != null){
                stmt.close();
                con.close();
                rs.close();
            }
        }

    }

    public void showTable(ArrayList<schedule> temp){
        System.out.println("1");
        MSSessionID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Session_ID"));
        System.out.println("2");
        MSSessionDate.setCellValueFactory(new PropertyValueFactory<schedule, Date>("Session_Date"));
        System.out.println("3");
        MSSessionTime.setCellValueFactory(new PropertyValueFactory<schedule, Time>("Session_Time"));
        System.out.println("4");
        MSTherapistID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Therapist_ID"));
        System.out.println("5");
        MSTherapistName.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapist_Name"));
        System.out.println("6");
        dataList = FXCollections.observableArrayList(data);
        System.out.println("7");
        MStableView.setItems(dataList); // table name
    }


}

