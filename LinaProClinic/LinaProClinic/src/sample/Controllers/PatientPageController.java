package sample.Controllers;


import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private TextField txfBirthDate;

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
        System.out.println("P_SSN" + LoginP_SSN);

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
            txfBirthDate.setText(""+rs.getString(3));
            //need to store these values to be used if editing fails
            oldName="" + rs.getString(2);
            oldGender="" + rs.getString(4);
            oldSSN="" + rs.getString(1);
            oldPhone="" + rs.getString(6);
            oldDate=""+rs.getString(3);
        }
        System.out.println("YESSSS");
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
        if(PITxfCampanionPhone.getText().trim().equals("") || !updatePhoneCampanion()){
            PITxfCampanionPhone.setText(oldCampPhone);
        }
        if(!updateBirthdate()){
            txfBirthDate.setText(oldDate);
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
        String newDate =txfBirthDate.getText().trim();
        Date newDatee = Date.valueOf(txfBirthDate.getText().trim());
        java.util.Date todayDate= Calendar.getInstance().getTime();
        // if the new date is in the past and valid then update the birth date
        if (isValid(newDate) && newDatee.before(todayDate))  {
            System.out.println(newDate + "Valid");
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
            System.out.println(newDate + "Not Valid");
            (new Alert(Alert.AlertType.ERROR, "Unaccepted Date")).show();
            return false;
        }
    }
    //To check the validity of the entered Date
    public static boolean isValid(final String date) {
        boolean valid = false;
        try {
            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            LocalDate.parse(date,
                    DateTimeFormatter.ofPattern("uuuu-M-d")
                            .withResolverStyle(ResolverStyle.STRICT)
            );
            valid = true;
        } catch (DateTimeParseException e) {
            valid = false;
            return valid;
        }
        return valid;
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
            System.out.println("HERE");
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
        System.out.println("Entered My Schedule!!!!!");
//        if(dataList.size()!=0) dataList.clear();
//        if(data.size()!=0) data.clear();

        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        try {
            java.util.Date todayDate = Calendar.getInstance().getTime();
            // query to get all the sessions assined for the patient from the current date, and order in ascending order by the date and the time of the sessions
            String sql2 = "select S.S_ID , S.S_Session_Date , S.S_Start_Time , A.T_SSN , T.T_Name" +
                    " from therapist_provide T , session S , attend A" +
                    " where A.P_SSN=" + LoginP_SSN + " and A.S_ID=S.S_ID and A.T_SSN=T.T_SSN order by S.S_Session_Date, S.S_Start_Time;"; //this is the statement to be executed
            System.out.println("before");
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql2);
            System.out.println("after");
            if (rs.next() == false) {
                System.out.println("No reserved session!");
            } else {//one tuple at a time
                do {
                    System.out.println("here");
//                    System.out.print(" " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
                    int SessionID = Integer.parseInt(rs.getString(1));
                    Date date = Date.valueOf(rs.getString(2));
                    Time StartTime = Time.valueOf((rs.getString(3)));
                    int TherapistSSN = Integer.parseInt(rs.getString(4));
                    String TherapistName = rs.getString(5);
                    if (!date.before(todayDate)) { // if the session date is equal or large than th current date then take it
                        schedule s = new schedule(SessionID, date, StartTime, TherapistSSN, TherapistName );
                        data.add(s); // add a tuple from the resulted query into data list
                        System.out.println(s.toString());
                    }
                } while (rs.next());

                // add data into the table view
                showTable(data);
            }
        } catch (Exception e) {
            System.out.println("Exception!!!!!!!!!!!!!11");
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
        System.out.println("sss\n");
        ObservableList<Integer> selectedIndices = MStableView.getSelectionModel().getSelectedIndices();
        if(selectedIndices.size() != 0 ) {
            schedule row = data.get(selectedIndices.get(0)); // get the selected row
            System.out.println(row.getSession_ID() + "\n");
            deleteRow(row);
            dataList.remove(selectedIndices.get(0));
            //  MStableView.refresh(); // update the tale view
            MStableView.getItems().removeAll(MStableView.getSelectionModel().getSelectedItem()); // remove all selected tables
        }
    }


    private void deleteRow(schedule row) {

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
            String SQL = "delete from attend where S_ID=" + row.getSession_ID() + ";"; // done
            Statement stmt1 = con.createStatement();
            stmt1.executeUpdate(SQL);
            stmt1.close();

            // update the available time for therapist
            SQL = "insert into available (A_Date,A_Start_Time,A_Request,A_Approve,T_SSN) values('"+row.getSession_Date() + "','" + row.getSession_Time() + "','0','0','" + row.getTherapist_ID() + "');";
            Statement stmt2 = con.createStatement();
            stmt2.executeUpdate(SQL);
            stmt2.close();

            // delete the session
            SQL = "delete from Session where S_ID="+row.getSession_ID() + ";"; // done
            Statement stmt3 = con.createStatement();
            stmt3.executeUpdate(SQL);
            stmt3.close();

            con.close();
            System.out.println("Connection closed");


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showTable(ArrayList<schedule> data) { // show the table of the MySchedule option
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


    @FXML
    void setMyPayment()throws SQLException{
        System.out.println("Entereed!!!!!1");
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // get all the sessions that were attend by thr patient in the past and display it's price, and wheather it's paid or not
            java.util.Date todayDate = Calendar.getInstance().getTime();
            String sql2 = "select S.S_ID ,S.S_Session_Date , S.S_Start_Time , A.T_SSN , T.T_Name,S_Paid, S_Price" +
                    " from therapist_provide T , session S , attend A" +
                    " where A.P_SSN=" + LoginP_SSN + " and A.S_ID=S.S_ID and A.T_SSN=T.T_SSN order by S.S_Session_Date , S.S_Start_Time ;"; //this is the statement to be executed
            System.out.println("before");
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql2);
            System.out.println("after");
            if (rs.next() == false) {
                System.out.println("No reserved session!");
            } else {//one tuple at a time
                do {
                    System.out.println("here");
                    int SessionID = Integer.parseInt(rs.getString(1));
                    Date date = Date.valueOf(rs.getString(2));
                    Time StartTime = Time.valueOf((rs.getString(3)));
                    int TherapistSSN = Integer.parseInt(rs.getString(4));
                    String TherapistName = rs.getString(5);
                    int paidOrNot=Integer.parseInt(rs.getString(6));
                    double Sprice=Double.parseDouble(rs.getString(7));
                    System.out.println("SessionPrice is  ------->"+Sprice);
                    if (date.before(todayDate)) { // if the session date is equal or large than th current date then take it
                        schedule s;
                        if(paidOrNot==1)
                           s = new schedule(SessionID, date, StartTime, TherapistSSN, TherapistName,true,Sprice);
                        else
                            s = new schedule(SessionID, date, StartTime, TherapistSSN, TherapistName,false,Sprice);
                        data.add(s); // add data into data list
                        System.out.println(s.toString());
                    }
                } while (rs.next());
                // add data into the table view
                System.out.println("Entereed222222");
                System.out.println(data.get(0).getSession_Price());
                showTablePayment(data);
                calculateForPayment();

            }
        } catch (Exception e) {
            System.out.println("Exception!!!!!!!!!!!!!11");
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
        System.out.println("1");
        PSessionID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Session_ID"));
        System.out.println("2");
        PSessionDate.setCellValueFactory(new PropertyValueFactory<schedule, Date>("Session_Date"));
        System.out.println("3");
        PSessionTime.setCellValueFactory(new PropertyValueFactory<schedule, Time>("Session_Time"));
        System.out.println("4");
        PTherapistID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Therapist_ID"));
        System.out.println("5");
        PTherapistName.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapist_Name"));
        System.out.println("6");
        PPaid.setCellValueFactory(new PropertyValueFactory<schedule, Boolean>("Paid"));
        PSessionPrice.setCellValueFactory(new PropertyValueFactory<schedule, Double>("Session_Price"));
        System.out.println("8");
        dataList = FXCollections.observableArrayList(data);
        System.out.println("9");
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
        System.out.println("YAY\n\n");
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/sample.fxml"));
        System.out.println("WTF");
        Scene scene11 = new Scene(nextSceneParent);
        AboutUsStage.setScene(scene11);
        AboutUsStage.show();
    }
    @FXML
    void setMyReservation() throws SQLException, ClassNotFoundException {
        lreserve1.setVisible(false);
        lreserve2.setVisible(false);
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        // get all the available times and date to reserve a session in .
        String sql = "select a.A_Date,a.A_Start_Time,t.Therapy_Type,a.T_SSN FROM lina_pro_clinic.Patient p,lina_pro_clinic.has h,lina_pro_clinic.available a,lina_pro_clinic.therapy t,lina_pro_clinic.therapist_provide th where P.P_SSN=h.P_SSN AND h.T_SSN=a.T_SSN AND h.T_SSN=th.T_SSN AND th.Therapy_ID=t.Therapy_ID AND a.A_Request=0 AND p.P_SSN="+LoginP_SSN+";";
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()!=false){
            Date date = Date.valueOf(rs.getString(1));
            Time StartTime = Time.valueOf((rs.getString(2)));
            String type = rs.getString(3);
            int T_id=Integer.parseInt(rs.getString(4));
            schedule temp=new schedule(date,StartTime,type,T_id);
            data.add(temp);
            //System.out.println(rs.getString(1)+rs.getString(2)+rs.getString(3));
            //Need to show data to user
        }

        if(data.size()!=0)
        showTableReserve(data);

    }

    //Used for tableview in the Reserve tab
    public void showTableReserve(ArrayList<schedule> data) {
        data.addAll(dataReserve);
        Session_Date.setCellValueFactory(new PropertyValueFactory<schedule, Date>("Session_Date"));
        Session_Time.setCellValueFactory(new PropertyValueFactory<schedule, Time>("Session_Time"));
        Therapy_Type.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapy_Type"));
        dataList = FXCollections.observableArrayList(data);
        System.out.println("------------------------------"+dataList.size());
        reserveTableView.setItems(dataList); // table name
    }

    @FXML
    void setReserve(ActionEvent event) throws SQLException, ClassNotFoundException {
        ObservableList<Integer> selectedIndices = reserveTableView.getSelectionModel().getSelectedIndices(); //get the index of the selected row
        if (selectedIndices.size() != 0) {
            System.out.println("ENTERED HEREEEEEE");
            int index = selectedIndices.get(0);
            Date checkDate = data.get(index).getSession_Date();
            Time checkTime = data.get(index).getSession_Time();
            int therapistSSN=data.get(index).getTherapist_ID();
            String therapyType=data.get(index).getTherapy_Type();
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            // check if there is a session that the patient assign in, at the same time as the selected avaialbe timeto reserve a session in
            String sql = "select S.S_ID , S.S_Session_Date , S.S_Start_Time , A.T_SSN , T.T_Name" +
                    " from therapist_provide T , session S , attend A" +
                    " where A.P_SSN=" + LoginP_SSN + " and A.T_SSN=T.T_SSN AND S_Start_Time='" + checkTime + "'AND S_Session_Date='" + checkDate + "'and A.S_ID=S.S_ID order by S.S_Session_Date , S.S_Start_Time ;"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next() == false) { // if there is no session that conflict with the selected time, then make a request for that time
                System.out.println("Can reserve");
                lreserve1.setVisible(true);
                lreserve2.setVisible(true);
                schedule temp=new schedule(checkDate,checkTime,therapyType,therapistSSN);
                dataReserve.add(temp);
                sql="update lina_pro_clinic.available a set a.A_Request= 1 where a.A_Date='"+checkDate+"'And a.A_Start_Time='"+checkTime +"' And a.T_SSN="+therapistSSN+";";
                DB.ExecuteUpdateStatement(sql);
            } else {
                (new Alert(Alert.AlertType.ERROR, "You have a Session at the same time")).show();
            }
            stmt.close();
            con.close();


        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // progressBar.setStyle("-fx-accent: green");
        name.setText(LoginP_Name);
        p_SSN.setText(""+LoginP_SSN);
        gender.setText(LoginP_Gender);
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(MySchedule);
        try {
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
                        System.out.println("Entereed PAYMENTTTTTTTTTTTTT");
                        setMyPayment();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else if("ReserveYourSession".equals(t1.getId())){
                    try {
                        if(dataList != null && dataList.size()!=0) dataList.clear();
                        if(data != null && data.size()!=0) data.clear();
                        setMyReservation();
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

