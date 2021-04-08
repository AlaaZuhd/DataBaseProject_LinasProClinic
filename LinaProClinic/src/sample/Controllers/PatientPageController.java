package sample.Controllers;


import java.awt.*;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import com.sun.org.apache.regexp.internal.RE;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.DBConn;
import sample.DBInitializing;
import sample.SimpleMD5Example;
import sample.schedule;

import java.net.URL;
import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

import static sample.Controllers.Controller.* ;

public class PatientPageController implements Initializable {

    public static ArrayList<schedule> data = new ArrayList<>(); // to store the resulted data from the query
    public static ArrayList<schedule> dataReserve = new ArrayList<>(); // to store the resulted data from the query
    private ObservableList<schedule> dataList; // to add data into the table view


    private Connection con;

    @FXML
    private VBox SidePanel;

    @FXML
    private Label name;

    @FXML
    private Label p_SSN;

    @FXML
    private Label gender;

    @FXML
    private Button btnLogOut;

    @FXML
    private TabPane tabPane;

    //PaneMyAccount
    @FXML
    private Tab Payment;

    @FXML
    private Tab MyAccount;

    @FXML
    private AnchorPane PanePersonalInformation;

    @FXML
    private Label PILabelName;

    @FXML
    private Label PILabelSSN;

    @FXML
    private TextField PITxfPhone;

    @FXML
    private TextField PITxfGender;

    @FXML
    private TextField PITxfCamoanionName;

    @FXML
    private TextField PITxfCampanionPhone;

    @FXML
    private Button PIBtnSave;

    @FXML
    private VBox BoxPassword;

    @FXML
    private Label PILabelOldPass;

    @FXML
    private PasswordField PIOldPass;

    @FXML
    private Label PILabelNewPass;

    @FXML
    private PasswordField PINewPass;

    @FXML
    private Tab MySchedule;

    @FXML
    private Button btnEdit;

    //PaneMySchedule
    @FXML
    private AnchorPane PaneMySchedule;

    @FXML
    protected TableColumn<schedule, Date> MSSessionDate;

    @FXML
    protected TableColumn<schedule, Time> MSSessionTime;


    @FXML
    protected TableColumn<schedule, String> MSTherapistName;

    @FXML
    protected TableColumn<schedule, Integer> MSSessionID;


    @FXML
    protected TableColumn<schedule, Integer> MSTherapistID;

    @FXML
    private TableView<schedule> MStableView;

    @FXML
    private Button btndeleteSelectedRow;

    //PanePayment
    @FXML
    private AnchorPane PanePayment;

    @FXML
    private TableView<schedule> paymenttableView;

    @FXML
    private TableColumn<schedule, Integer> PSessionID;

    @FXML
    private TableColumn<schedule, Date> PSessionDate;

    @FXML
    private TableColumn<schedule, Time> PSessionTime;

    @FXML
    private TableColumn<schedule, Integer> PTherapistID;

    @FXML
    private TableColumn<schedule, String> PTherapistName;

    @FXML
    private TableColumn<schedule, Boolean> PPaid;

    @FXML
    private TableColumn<schedule, Double> PSessionPrice;

    @FXML
    private Button btnPay;

    @FXML
    private Label lAmount;

    @FXML
    private Label lPaid;

    @FXML
    private Label lBalance;

    @FXML
    private ProgressBar progressBar;

    //PaneReserveYourSession
    @FXML
    private Tab ReserveYourSession;


    @FXML
    private Button btnReserve;

    @FXML
    private TableView<schedule> reserveTableView;

    @FXML
    private TableColumn<schedule, Date> Session_Date;

    @FXML
    private TableColumn<schedule, Time> Session_Time;

    @FXML
    private TableColumn<schedule, String> Therapy_Type;

    @FXML
    private Label lreserve1;

    @FXML
    private Label lreserve2;
    //Pending Session
    @FXML
    private Tab PendingSession;

    @FXML
    private TableView<schedule> pendingTableView;

    @FXML
    private TableColumn<schedule, Date> Session_Date2;

    @FXML
    private TableColumn<schedule, Time> Session_Time2;

    @FXML
    private TableColumn<schedule, String> Therapy_Type2;

    @FXML
    private TableColumn<schedule, String> Therapist_Name2;

    @FXML
    private TableColumn<schedule, Integer> Therapist_ID2;

    @FXML
    private TableColumn<schedule, String> Status2;

    @FXML
    private DatePicker birthdate;

    //Old values for my account
    String oldName;
    String oldGender;
    String oldSSN;
    String oldPhone;
    String oldDate;
    String oldCampName;
    String oldCampPhone;

    @FXML
    void SetMyAccount() throws SQLException, ClassNotFoundException { // open a tab for patient information
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        // get patient information using the following query
        String sql = "SELECT * FROM lina_pro_clinic.patient where P_SSN=" + LoginP_SSN + ";";
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        //set the values obtained from the patient table
        if (rs.next() != false) {
            PILabelName.setText("" + rs.getString(2));  //name is the second column
            PITxfGender.setText("" + rs.getString(4));  //gender is the fourth column
            PILabelSSN.setText("" + rs.getString(1));  //SSN is the first column
            PITxfPhone.setText("" + rs.getString(6));
            LocalDate t=LocalDate.parse(rs.getString(3));
            birthdate.setValue(t);
            //birthdate.setText(""+rs.getString(3));
            //need to store these values to be used if editing fails
            oldName="" + rs.getString(2);
            oldGender="" + rs.getString(4);
            oldSSN="" + rs.getString(1);
            oldPhone="" + rs.getString(6);
            oldDate=""+rs.getString(3);
        }
        stmt.close();
        con.close();
        rs.close();
        // get companion information that's associated with the patient
        sql = "SELECT * FROM lina_pro_clinic.companion_accompany where P_SSN=" + LoginP_SSN + ";"; //this is the statement to be executed
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        //set the values obtained from the companion_accompany table
        if (rs.next() != false) {
            PITxfCamoanionName.setText("" + rs.getString(1));
            PITxfCampanionPhone.setText("" + rs.getString(2));
            oldCampName="" + rs.getString(1);
            oldCampPhone="" + rs.getString(2);
        }
        stmt.close();
        con.close();
        rs.close();
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
        birthdate.setDisable(true);


    }

    @FXML
    void setEdit(ActionEvent event) {
        //Set them to Editiable
        BoxPassword.setVisible(true);
        PILabelNewPass.setVisible(true);
        PILabelOldPass.setVisible(true);
        PIOldPass.setVisible(true);
        PINewPass.setVisible(true);
        PITxfPhone.setEditable(true);
        PITxfGender.setEditable(true);
        PITxfCamoanionName.setEditable(true);
        PITxfCampanionPhone.setEditable(true);
        birthdate.setDisable(false);
        PITxfGender.setStyle("-fx-background-color : #fdfbf9;");
        PITxfPhone.setStyle("-fx-background-color : #fdfbf9;");
        PITxfCamoanionName.setStyle("-fx-background-color : #fdfbf9;");
        PITxfCampanionPhone.setStyle("-fx-background-color : #fdfbf9;");
    }

    @FXML
    void SetSave(ActionEvent event) throws Exception {
        //check updates
        if(!updatePhone()){
            PITxfPhone.setText(oldPhone);
        }
        if(!updateGender()){
            PITxfGender.setText(oldGender);
        }
        if (!PIOldPass.getText().trim().equals("")){
            if(!PINewPass.getText().trim().equals(""))
               updatePassword();
        }
        if(!updateNameCampanion()){
            PITxfCamoanionName.setText(oldCampName);
        }
        if(!PITxfCampanionPhone.getText().trim().equals("") ){
            if(!updatePhoneCampanion())
                PITxfCampanionPhone.setText(oldCampPhone);
        }
        if(!updateBirthdate()){
            LocalDate t=LocalDate.parse(oldDate);
            birthdate.setValue(t);
        }

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
        birthdate.setDisable(true);


    }

    public Boolean updateGender() throws SQLException, ClassNotFoundException {
        String newGender = PITxfGender.getText().trim();
        // update the gender if the new gender is female or male or other
        if (newGender.equalsIgnoreCase("Female") || newGender.equalsIgnoreCase("Male") || newGender.equalsIgnoreCase("Other")) {
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "update  lina_pro_clinic.patient p set P_Gender = '" + newGender + "' where P_SSN = " + LoginP_SSN + ";"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
            stmt.close();
            con.close();
            return true;
        } else {
            (new Alert(Alert.AlertType.ERROR, "Wrong Gender")).show();
            return false;
        }
    }

    public Boolean updatePhone() throws SQLException, ClassNotFoundException {
        String newPhone = PITxfPhone.getText().trim();
        // update phone number if the new number consisting of only digits
        if (newPhone.matches("[0-9]+")) {
            Integer newPhonee = Integer.parseInt(newPhone);
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "update lina_pro_clinic.patient p set P_Phone_Num = '" + newPhonee + "' where P_SSN = " + LoginP_SSN + ";"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
            stmt.close();
            con.close();
            return true;
        } else {
            (new Alert(Alert.AlertType.ERROR, "Wrong Phone Number")).show();
            return false;
        }
    }
    public Boolean updateBirthdate() throws Exception {
        LocalDate valueDate = birthdate.getValue();
        java.util.Date B_date= java.sql.Date.valueOf(valueDate);
        java.util.Date todayDate = Calendar.getInstance().getTime(); //Today's Date
        try {
            if (B_date.after(todayDate)) {
                throw new Exception("Date greater than today!!");
            }
        }catch (Exception e){
            (new Alert(Alert.AlertType.ERROR, "Wrong Date")).show();
        }

        String newDate=""+valueDate;
        if ( B_date.before(todayDate))  {
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "update lina_pro_clinic.patient p set P_BirthDate = '" + newDate + "' where P_SSN = " + LoginP_SSN + ";"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
            stmt.close();
            con.close();
            return true;
        } else {
            return false;
        }
    }


    public Boolean updatePhoneCampanion() throws SQLException, ClassNotFoundException {
        String newPhone = PITxfCampanionPhone.getText().trim();
        // check if the entered phone number of the companion is valid or not
        // if it's valid then we wil update it, else it will stay as the previous
        if (newPhone.matches("[0-9]+")) {
            Integer newPhonee = Integer.parseInt(newPhone);
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "update lina_pro_clinic.companion_accompany set C_Phone_Num = " + newPhonee + " where P_SSN = " + LoginP_SSN + ";"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
            stmt.close();
            con.close();
            return true;
        } else {
            (new Alert(Alert.AlertType.ERROR, "Wrong Campanion Phone Number")).show();
            return false;
        }
    }

    public Boolean updateNameCampanion() throws SQLException, ClassNotFoundException {
        String newName = PITxfCamoanionName.getText().trim();
        // update the name of the companion directly
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "update lina_pro_clinic.companion_accompany set C_Name= '" + newName + "' where P_SSN = " + LoginP_SSN + ";"; //this is the statement to be executed
        con = a.connectDB();
        stmt = con.createStatement();
        DB.ExecuteUpdateStatement(sql);
        stmt.close();
        con.close();
        return true;
    }

    public Boolean updatePassword() throws SQLException, ClassNotFoundException {
        String oldPass = PIOldPass.getText().trim();
        // getting the hashing of th old password
        SimpleMD5Example s=new SimpleMD5Example();
        oldPass = s.hash(oldPass);
        // getting the hashing of the new password
        String newPass = PINewPass.getText().trim();
        s=new SimpleMD5Example();
        newPass = s.hash(newPass);
        if (newPass.equals("")) {
            (new Alert(Alert.AlertType.ERROR, "Wrong New Password")).show();
            return false;
        }
        //check if old password matches the one in database
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
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

    @FXML
    void setMySchedule() throws SQLException { // get the schedule of the patient's sessions  for the upcoming days
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        try {
            ///------------//
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1); // number represents number of days
            java.util.Date yesterday = cal.getTime();
            //------------//
            // query to get all the sessions assined for the patient from the current date, and order in ascending order by the date and the time of the sessions
            String sql2 = "select S.S_ID , S.S_Session_Date , S.S_Start_Time , A.T_SSN , T.T_Name" +
                    " from lina_pro_clinic.therapist_provide T , lina_pro_clinic.session S , lina_pro_clinic.attend A" +
                    " where A.P_SSN=" + LoginP_SSN + " and A.S_ID=S.S_ID and A.T_SSN=T.T_SSN order by S.S_Session_Date, S.S_Start_Time;"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql2);
            if (rs.next() == false) {
            } else {//one tuple at a time
                do {
                    int SessionID = Integer.parseInt(rs.getString(1));
                    Date date = Date.valueOf(rs.getString(2));
                    Time StartTime = Time.valueOf((rs.getString(3)));
                    int TherapistSSN = Integer.parseInt(rs.getString(4));
                    String TherapistName = rs.getString(5);
                    // if the session date is equal or large than th current date then take it
                    if(date.after(yesterday))    {
                        schedule s = new schedule(SessionID, date, StartTime, TherapistSSN, TherapistName );
                        data.add(s); // add a tuple from the resulted query into data list
                    }
                } while (rs.next());

                // add data into the table view
                showTable(data);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
                con.close();
                rs.close();
            }
        }
    }


    @FXML
    void setDeleteSession(ActionEvent event) { // to delete the selected session
        // delete some session means
        // 1- delete it from the session table
        // 2- update the attend table
        // 3- update the available table by adding that session as new instance in thr available table
        ObservableList<Integer> selectedIndices = MStableView.getSelectionModel().getSelectedIndices();
        if(selectedIndices.size() != 0 ) {
            schedule row = data.get(selectedIndices.get(0)); // get the selected row
            deleteRow(row);
            dataList.remove(selectedIndices.get(0));
            //  MStableView.refresh(); // update the tale view
            MStableView.getItems().removeAll(MStableView.getSelectionModel().getSelectedItem()); // remove all selected tables
        }
    }


    private void deleteRow(schedule row) {
       // System.out.println("Enter Deeltieng session from patient");

        // delete some session means
        // 1- delete it from the session table
        // 2- update the attend table
        // 3- update the available table by adding that session as new instance in thr available table

        try {
            if(row==null) return ;
            // delete the session from attend table
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            con=a.connectDB();
            String SQL = "delete from lina_pro_clinic.attend where S_ID=" + row.getSession_ID() + ";"; // done
//            System.out.println("First thing, delete attend");
//            System.out.println(SQL);
            Statement stmt1 = con.createStatement();
            stmt1.executeUpdate(SQL);
            stmt1.close();


            // update the available time for therapist
           // SQL = "insert into available (A_Date,A_Start_Time,A_Request,A_Approve,T_SSN) values('"+row.getSession_Date() + "','" + row.getSession_Time() + "','0','0','" + row.getTherapist_ID() + "');";
            SQL = "update lina_pro_clinic.available a set a.A_Request=0,a.A_Approve=0,a.P_SSN=null where a.A_Request=1 AND a.A_Approve=1 AND a.A_Date='"+row.getSession_Date()+"' AND a.A_Start_Time='"+row.getSession_Time()+"' AND a.T_SSN='"+row.getTherapist_ID()+"';";
//            System.out.println("Second thing,  update availabe");
//            System.out.println(SQL);
            Statement stmt2 = con.createStatement();
            stmt2.executeUpdate(SQL);
            stmt2.close();

            // delete the session
            SQL = "delete from lina_pro_clinic.Session where S_ID="+row.getSession_ID() + ";"; // done
//            System.out.println("3 thing, delete session");
//            System.out.println(SQL);
            Statement stmt3 = con.createStatement();
            stmt3.executeUpdate(SQL);
            stmt3.close();
            con.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showTable(ArrayList<schedule> data) { // show the table of the MySchedule option
        MSSessionID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Session_ID"));
        MSSessionDate.setCellValueFactory(new PropertyValueFactory<schedule, Date>("Session_Date"));
        MSSessionTime.setCellValueFactory(new PropertyValueFactory<schedule, Time>("Session_Time"));
        MSTherapistID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Therapist_ID"));
        MSTherapistName.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapist_Name"));
        dataList = FXCollections.observableArrayList(data);
        MStableView.setItems(dataList); // table name
    }


    @FXML
    void setMyPayment()throws SQLException{
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // get all the sessions that were attend by the patient in the past and display it's price, and wheather it's paid or not
            java.util.Date todayDate = Calendar.getInstance().getTime();
            String sql2 = "select S.S_ID ,S.S_Session_Date , S.S_Start_Time , A.T_SSN , T.T_Name,S_Paid, S_Price" +
                    " from therapist_provide T , session S , attend A" +
                    " where A.P_SSN=" + LoginP_SSN + " and A.S_ID=S.S_ID and A.T_SSN=T.T_SSN order by S.S_Session_Date , S.S_Start_Time ;"; //this is the statement to be executed
//            System.out.println("testin this pay");
//            System.out.println(sql2);
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql2);
            if (rs.next() == false) {
            } else {//one tuple at a time
                do {
                    int SessionID = Integer.parseInt(rs.getString(1));
                    Date date = Date.valueOf(rs.getString(2));
                    Time StartTime = Time.valueOf((rs.getString(3)));
                    int TherapistSSN = Integer.parseInt(rs.getString(4));
                    String TherapistName = rs.getString(5);
                    int paidOrNot=Integer.parseInt(rs.getString(6));
                    double Sprice=Double.parseDouble(rs.getString(7));
                    if (date.before(todayDate)) { // if the session date is equal or large than th current date then take it
                        schedule s;
                        if(paidOrNot==1)
                           s = new schedule(SessionID, date, StartTime, TherapistSSN, TherapistName,true,Sprice);
                        else
                            s = new schedule(SessionID, date, StartTime, TherapistSSN, TherapistName,false,Sprice);
                        data.add(s); // add data into data list
                    }
                } while (rs.next());
                // add data into the table view
                showTablePayment(data);
                calculateForPayment();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
                con.close();
                rs.close();
            }
        }
    }

    public void calculateForPayment(){
        // balance is the amount of money that the patient have to pay
        // paid is the total money paid by the patient
        // amount = balance + paid
        if(data.size()!=0){
            double amount=0;
            double paid=0;
            double Balance=0;
            for(int i=0;i<data.size();i++){
                amount+=data.get(i).getSession_Price();
                if(data.get(i).getPaid()==true)
                    paid+=data.get(i).getSession_Price();
                else
                    Balance+=data.get(i).getSession_Price();
            }
            lAmount.setText(""+amount);
            lPaid.setText(""+paid);
            lBalance.setText("-"+Balance);
            if(amount!=0)
            progressBar.setProgress((paid/amount));
        }
    }
    public void showTablePayment(ArrayList<schedule> data) {
        PSessionID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Session_ID"));
        PSessionDate.setCellValueFactory(new PropertyValueFactory<schedule, Date>("Session_Date"));
        PSessionTime.setCellValueFactory(new PropertyValueFactory<schedule, Time>("Session_Time"));
        PTherapistID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Therapist_ID"));
        PTherapistName.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapist_Name"));
        PPaid.setCellValueFactory(new PropertyValueFactory<schedule, Boolean>("Paid"));
        PSessionPrice.setCellValueFactory(new PropertyValueFactory<schedule, Double>("Session_Price"));
        dataList = FXCollections.observableArrayList(data);
        paymenttableView.setItems(dataList); // table name
    }

    @FXML
    void setBtnPay(ActionEvent event) throws SQLException, ClassNotFoundException {
        // get the selected row to pay it
        // get the confirmation from the user for this action
        // re-calculate the payment of this patient
        ObservableList<Integer> selectedIndices = paymenttableView.getSelectionModel().getSelectedIndices(); //get the index of the selected row
        if(selectedIndices.size()!=0) {
            //alert for confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Payment");
            String s = "Confirm to pay the amount";
            alert.setContentText(s);

            Optional<ButtonType> result = alert.showAndWait();

            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
                //Confirmed//
                int index = selectedIndices.get(0);
                int Session_ID_to_Pay = data.get(index).getSession_ID();
                data.get(index).setPaid(true);
                DBInitializing DB = new DBInitializing();  //connecting to database
                DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
                Statement stmt = null;
                ResultSet rs = null;
                String sql = "update lina_pro_clinic.session set S_Paid= '1' where S_ID = " + Session_ID_to_Pay + ";"; //this is the statement to be executed
                con = a.connectDB();
                stmt = con.createStatement();
                DB.ExecuteUpdateStatement(sql);
                stmt.close();
                con.close();
                paymenttableView.refresh();
                calculateForPayment();
            }
        }
    }
    @FXML
    void setLogOut(ActionEvent event) throws IOException { // in log out we go back to the home page
        // get a handle to the stage
        Stage MainStage = (Stage) btnLogOut.getScene().getWindow(); // MainStage is the current stage
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
    void setMyReservation() throws SQLException, ClassNotFoundException, ParseException, IOException {
        if(dataList != null && dataList.size()!=0) dataList.clear();
        if(data != null && data.size()!=0) data.clear();
        // reading the last modification date
        String lastModificationDate11 ;
        //File file = new File("C:\\Users\\yassi\\OneDrive\\LinaProClinic\\src\\sample\\lastModificationDate.txt");
        File file = new File("src/sample/lastModificationDate.txt");
        Scanner scan = new Scanner(file);
        lastModificationDate11 = scan.nextLine();
        lreserve1.setVisible(false);
        lreserve2.setVisible(false);
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        // we need to check if the available times for all therapists are up to date, if yes then go to step 2, else go to step 1
        // update the available times
        // first delete all available times that is now in the past
        // get the date of the last modification 'update'

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

        //java.util.Date lastModificationDate1 = new java.util.Date("2020-12-31");
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


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(todayDate, dtf);
        LocalDate date2 = LocalDate.parse(lastModificationDate, dtf);
        //long daysBetween = Duration.between(date1, date2).toDays();
        long daysBetween = ChronoUnit.DAYS.between(date2, date1);


        if(daysBetween >= 1){
            // delete all previous available
            //String sql = "delete from lina_pro_clinic.available a where a.A_Date <'" + todayDate + "';";

            String sql = "delete from lina_pro_clinic.available a where a.A_Date <'"+ todayDate +"';";

            con = a.connectDB();
            DB.ExecuteUpdateStatement(sql);
            // add all new available times
            LocalDate date3 ;
            if(daysBetween > 30)
                date3 = date1;
            else
                date3 = date2 ;
            int NumberOfTHerapists = 0 ;
            ArrayList <Integer> arrT_SSN = new ArrayList<>();
            //sql = "select h.T_SSN from lina_pro_clinic.has h where h.P_SSN ='"+ LoginP_SSN +"' ; ";
            sql = "select t.T_SSN from lina_pro_clinic.therapist_provide t ; ";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                arrT_SSN.add(Integer.parseInt(rs.getString(1)));
                NumberOfTHerapists ++ ;
            }
            for(int k=0;k<NumberOfTHerapists;k++) {
                int i = 0;
                // increment days by 7
                if(daysBetween >  30)
                    date2 = date3 ;
                else
                    date2 = date3.plusDays(30); // to start modification after one month from the last modification

                sql =  "insert into lina_pro_clinic.available  (A_Date,A_Start_Time,A_Request,A_Approve,T_SSN)" +
                        " values ";
                while (i < Math.min(daysBetween,30)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    date2 = date2.plusDays(1);
                    // add available time from 8:00Am to 2:00P<
                    int Time = 8;
                    for (int j = 0; j < 8; j++) {
                        if (Time > 12) Time = 1;
                        String timeFormat = "";
                        if (Time / 10 == 0) timeFormat += "0" + Time + ":";
                        else timeFormat = Time + ":";
                        timeFormat += "00:00";
                        if(j == 7 && i == Math.min(daysBetween-1,30-1))
                            sql += "  ('" + date2 + "','" + timeFormat + "','0','0','" + arrT_SSN.get(k) + "') ;";
                        else
                            sql += "  ('" + date2 + "','" + timeFormat + "','0','0','" + arrT_SSN.get(k) + "') ,";
                        Time++;
                    }
                    i++;
                }

                con = a.connectDB();
                DB.ExecuteUpdateStatement(sql);
            }
        }

        FileWriter out = new FileWriter(file);
        out.write(todayDate);
        out.close();
        // step 2 : get therapist ids for this patient
        String sql = "select h.T_SSN from lina_pro_clinic.has h where h.P_SSN = '"+ LoginP_SSN +"';";
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        ArrayList<String> therapistSSN = new ArrayList<>();
        while(rs.next()){
            therapistSSN.add(rs.getString(1));
        }
        for(int i=0; i<therapistSSN.size(); i++) {
            // Step 3 : get all the available times and date to reserve a session in .
            sql = "select distinct a.A_Date,a.A_Start_Time,t.Therapy_Type,a.T_SSN FROM lina_pro_clinic.Patient p, lina_pro_clinic.available a,lina_pro_clinic.therapy t,lina_pro_clinic.therapist_provide th Where th.Therapy_ID=t.Therapy_ID AND th.T_SSN= a.T_SSN AND a.A_Request=0 AND th.T_SSN = '"+therapistSSN.get(i)+"' AND (a.P_SSN is null or a.P_SSN !='"+LoginP_SSN+"')";
//            System.out.println("This now");
//            System.out.println(sql);
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next() != false) {
                Date date = Date.valueOf(rs.getString(1));
                Time StartTime = Time.valueOf((rs.getString(2)));
                String type = rs.getString(3);
                int T_id = Integer.parseInt(rs.getString(4));
                schedule temp = new schedule(date, StartTime, type, T_id);
                data.add(temp);
            }
        }
        if (data.size() != 0)
            showTableReserve(data);
        else {
            // no available times for your therapists
        }

    }
    //Used for tableview in the Reserve tab
    public void showTableReserve(ArrayList<schedule> data) {
       // data.addAll(dataReserve);
        Session_Date.setCellValueFactory(new PropertyValueFactory<schedule, Date>("Session_Date"));
        Session_Time.setCellValueFactory(new PropertyValueFactory<schedule, Time>("Session_Time"));
        Therapy_Type.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapy_Type"));
        dataList = FXCollections.observableArrayList(data);
        reserveTableView.setItems(dataList); // table name
    }

    @FXML
    void setReserve(ActionEvent event) throws SQLException, ClassNotFoundException {
        Boolean flag=false;
        ObservableList<Integer> selectedIndices = reserveTableView.getSelectionModel().getSelectedIndices(); //get the index of the selected row
        if (selectedIndices.size() != 0) {
            int index = selectedIndices.get(0);
            Date checkDate = dataList.get(index).getSession_Date();
            Time checkTime = dataList.get(index).getSession_Time();
            int therapistSSN=dataList.get(index).getTherapist_ID();
            String therapyType=dataList.get(index).getTherapy_Type();
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            // check if there is a session that the patient assign in, at the same time as the selected avaialbe time to reserve a session in
            String sql = "select S.S_ID , S.S_Session_Date , S.S_Start_Time , A.T_SSN , T.T_Name" +
                    " from therapist_provide T , session S , attend A" +
                    " where A.P_SSN=" + LoginP_SSN + " and A.T_SSN=T.T_SSN AND S_Start_Time='" + checkTime + "'AND S_Session_Date='" + checkDate + "'and A.S_ID=S.S_ID order by S.S_Session_Date , S.S_Start_Time ;"; //this is the statement to be executed

            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next() == false) { // if there is no session that conflict with the selected time, then make a request for that time
                    flag=true; ///Check the second condition
            } else {
                (new Alert(Alert.AlertType.ERROR, "You have a Session at the same time")).show();
            }
            stmt.close();
            con.close();
            //check the pending as well to check the availabilty --> Need from available Request=1, and approved!=-1
            if(flag){

                sql="select A_ID from lina_pro_clinic.available a where a.P_SSN= '"+LoginP_SSN+"' AND a.A_Date='"+checkDate+"' AND a.A_Start_Time='"+checkTime+"' AND a.A_Request=1 AND a.A_Approve!=-1;";
                con = a.connectDB();
                stmt = con.createStatement();
                rs = stmt.executeQuery(sql);
                if(rs.next()==false){
                  lreserve1.setVisible(true);
                lreserve2.setVisible(true);
                schedule temp=new schedule(checkDate,checkTime,therapyType,therapistSSN);
                dataReserve.add(temp);
                sql="update lina_pro_clinic.available a set a.A_Request='1',a.A_Approve='0',a.P_SSN='"+LoginP_SSN+"' where a.A_Date='"+checkDate+"'And a.A_Start_Time='"+checkTime +"' And a.T_SSN="+therapistSSN+";";
                DB.ExecuteUpdateStatement(sql);
                reserveTableView.getItems().remove(reserveTableView.getSelectionModel().getSelectedItem());
                //dataList.remove(index);


                }
                else {
                    (new Alert(Alert.AlertType.ERROR, "You have a Pending Session at the same time")).show();
                }
                stmt.close();
                con.close();
            }
        }
    }

    //About Reservation Detials
    void setMyPendingSession() throws SQLException, ClassNotFoundException {
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        // check if there is a session that the patient assign in, at the same time as the selected avaialbe timeto reserve a session in
        String sql = "select distinct a.A_Date,a.A_Start_Time,t.Therapy_Type,a.T_SSN,th.T_Name,a.A_Request,a.A_Approve FROM lina_pro_clinic.Patient p, lina_pro_clinic.available a,lina_pro_clinic.therapy t,lina_pro_clinic.therapist_provide th Where th.Therapy_ID=t.Therapy_ID AND th.T_SSN= a.T_SSN AND a.A_Request=1 AND  a.P_SSN = '"+LoginP_SSN+"' ;";
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        if(rs.next()==false){

        }
        else{
            do{
                Date date = Date.valueOf(rs.getString(1));
                Time StartTime = Time.valueOf((rs.getString(2)));
                String type = rs.getString(3);
                int Tid = Integer.parseInt(rs.getString(4));
                String Tname = rs.getString(5);
                int request=Integer.parseInt(rs.getString(6));
                int approve=Integer.parseInt(rs.getString(7));
                String status="";
                if(request==1 && approve==0) status="Not Checked YET!";
                else if(request==1 && approve==1) status="Approved";
                else if(request==1 && approve ==-1) status="Declined";
                else status="No WAYS";

                schedule temp=new schedule(Tid,Tname,date,StartTime,type,status);
                data.add(temp);
            }while(rs.next()!=false);
            showTableReservationDetails(data);
        }


    }
    public void showTableReservationDetails(ArrayList<schedule>data){
        Session_Date2.setCellValueFactory(new PropertyValueFactory<schedule, Date>("Session_Date"));
        Session_Time2.setCellValueFactory(new PropertyValueFactory<schedule, Time>("Session_Time"));
        Therapy_Type2.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapy_Type"));
        Therapist_Name2.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapist_Name"));
        Therapist_ID2.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Therapist_ID"));
        Status2.setCellValueFactory(new PropertyValueFactory<schedule, String>("Status"));
        dataList = FXCollections.observableArrayList(data);
        pendingTableView.setItems(dataList); // table name
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // progressBar.setStyle("-fx-accent: green");
        birthdate.setEditable(false);
        name.setText(LoginP_Name);
        p_SSN.setText(""+LoginP_SSN);
        //gender.setText(LoginP_Gender);
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(MySchedule);
        try {
            if(dataList != null && dataList.size()!=0) dataList.clear();
            if(data != null && data.size()!=0) data.clear();
            setMySchedule();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>()
        {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1)
            {
                if ("MyAccount".equals(t1.getId()))
                {
                    try {
                        SetMyAccount();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if("MySchedule".equals(t1.getId())){
                    try {
                        if(dataList != null && dataList.size()!=0) dataList.clear();
                        if(data != null && data.size()!=0) data.clear();
                        setMySchedule();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else if("Payment".equals(t1.getId())){
                    try {
                        if(dataList != null && dataList.size()!=0) dataList.clear();
                        if(data != null && data.size()!=0) data.clear();
                        setMyPayment();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else if("ReserveYourSession".equals(t1.getId())){
                    try {
                        setMyReservation();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if("PendingSession".equals(t1.getId())) {
                    try {
                        if(dataList != null && dataList.size()!=0) dataList.clear();
                        if(data != null && data.size()!=0) data.clear();
                        setMyPendingSession();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}

