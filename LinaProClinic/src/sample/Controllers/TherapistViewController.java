package sample.Controllers;


import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import static sample.Controllers.SingInController.*;
import static sample.Controllers.Controller.* ;



public class TherapistViewController implements Initializable {
    public static ArrayList<TherapistSessionSchedule> data = new ArrayList<TherapistSessionSchedule>(); // to store the resulted data from the query
    public static ArrayList<PendingSchedule> data1 = new ArrayList<PendingSchedule>();
    private ObservableList<TherapistSessionSchedule> dataList; // to add data into the table view
    private ObservableList<PendingSchedule> dataList2; // to add data into the table view
    public static ArrayList<PendingSchedule> PendingReservationList = new ArrayList<>();

    private Connection con;
    private String oldPhone;
    private String oldDate;
    private String oldPass;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;


    @FXML
    private VBox SidePanel;

    @FXML
    private Label name;

    @FXML
    private Label T_SSN;

    @FXML
    private Button btnLogOut;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab MyAccount;

    @FXML
    private AnchorPane PaneMyAccount;

    @FXML
    private Button btnDeleteSession;

    @FXML
    private Label TLabelName;

    @FXML
    private Label TLabelSSN;

    @FXML
    private TextField Tphone;

    @FXML
    private Label TLabelTherapyID;

    @FXML
    private Label TLabelSessionRate;

    @FXML
    private Button PIBtnSave;

    @FXML
    private Button PIBtnEdit;

    @FXML
    private VBox BoxPassword;

    @FXML
    private Label TLabelOldPass;

    @FXML
    private PasswordField TtxtOldPass;

    @FXML
    private Label TLabelNewPass;

    @FXML
    private PasswordField TtxtNewPass;

    @FXML
    private Tab MySchedule1;

    @FXML
    private AnchorPane PaneMySchedule;

    @FXML
    private AnchorPane MySchedule;

    @FXML
    private TableView<TherapistSessionSchedule> ScheduleTable;

    @FXML
    private TableColumn<TherapistSessionSchedule, Integer> SessionIDcol;

    @FXML
    private TableColumn<TherapistSessionSchedule, Date> SessionDatecol;

    @FXML
    private TableColumn<TherapistSessionSchedule, Time> SessionTimecol;

    @FXML
    private TableColumn<TherapistSessionSchedule, Integer> Patientcol;

    @FXML
    private TableColumn<TherapistSessionSchedule, String> PatientNamecol;

    @FXML
    private Tab Salary;

    @FXML
    private AnchorPane PaneSalary;

    @FXML
    private Tab PendingReservation;

    @FXML
    private AnchorPane PanePendingReservation;

    @FXML
    private TableView<?> MStableView1;

    @FXML
    private TableColumn<?, ?> MSSessionID1;

    @FXML
    private TableColumn<?, ?> MSSessionDate1;

    @FXML
    private TableColumn<?, ?> MSSessionTime1;

    @FXML
    private TableColumn<?, ?> MSTherapistID1;

    @FXML
    private TableColumn<?, ?> MSTherapistName1;
    @FXML
    private Label LabelLast;

    @FXML
    private Label LabelDate;

    @FXML
    private Label LabelCurrent;

    @FXML
    private Label LabelBalance;

    @FXML
    private Button btnCalculateSalary;

    @FXML
    private Label LabelTotal;

    @FXML
    private Label LabelSalary;

    @FXML
    private TableView<PendingSchedule> PendingTable;
    @FXML
    private Button btnWithdrawSalary;
    @FXML
    private TableColumn<PendingSchedule, Date> SessionDatecol1;
    @FXML
    private TableColumn<PendingSchedule, Time> SessionTimecol1;
    @FXML
    private TableColumn<PendingSchedule, String> PatientNamecol1;
    @FXML
    private TableColumn<PendingSchedule, Integer> PatientSSNcol1;


    @FXML
    private Button btnAccept;

    @FXML
    private Button btnDecline;

    @FXML
    private Label LabelSelect;

    @FXML
    private DatePicker birthDate;

    //MyAccountPane Functions
    @FXML
    void SetMyAccount() throws SQLException, ClassNotFoundException {
        if (dataList != null && dataList.size() != 0) dataList.clear();
        if (data != null && data.size() != 0) data.clear();
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM lina_pro_clinic.therapist_provide where T_SSN=" + LoginT_SSN + ";"; //this is the statement to be executed
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        //set the values obtained from the patient table
        if (rs.next() != false) {
            TLabelName.setText("" + rs.getString(1));  //name is the first column
            TLabelTherapyID.setText("" + rs.getString(3));  //therapyID is the third column
            TLabelSSN.setText("" + rs.getString(2));  //SSN is the second column
            Tphone.setText("" + rs.getString(5));
            LocalDate t=LocalDate.parse(rs.getString(7));
            birthDate.setValue(t);
            oldPhone = "" + rs.getString(5);
            oldDate = "" + rs.getString(7);
            TLabelSessionRate.setText("" + rs.getString(4));
        }

        //Set Editiable back to false
        BoxPassword.setVisible(false);
        TLabelOldPass.setVisible(false);
        TLabelNewPass.setVisible(false);
        TtxtOldPass.setVisible(false);
        TtxtNewPass.setVisible(false);
        Tphone.setEditable(false);
        birthDate.setDisable(true);
        stmt.close();
        con.close();
        rs.close();

    }

    @FXML
    void SetSave(ActionEvent event) throws SQLException, ClassNotFoundException {
        //check updates
        if (!updatePhone()) {
            Tphone.setText(oldPhone);
        }

        if (!TtxtOldPass.getText().trim().equals("")) {
            if (!TtxtNewPass.getText().trim().equals(""))
                updatePassword();
        }
        if (!updateBirthdate()) {
            LocalDate t=LocalDate.parse(oldDate);
            birthDate.setValue(t);
        }

        //Set Editiable back to false
        BoxPassword.setVisible(false);
        TLabelOldPass.setVisible(false);
        TLabelNewPass.setVisible(false);
        TtxtOldPass.setVisible(false);
        TtxtNewPass.setVisible(false);
        Tphone.setEditable(false);
        birthDate.setDisable(true);

    }


    @FXML
    void SetEdit(ActionEvent event) {
        //--//
        BoxPassword.setVisible(true);
        TLabelNewPass.setVisible(true);
        TLabelOldPass.setVisible(true);
        TtxtOldPass.setVisible(true);
        TtxtNewPass.setVisible(true);
        Tphone.setEditable(true);
        birthDate.setDisable(false);
        Tphone.setStyle("-fx-background-color : #fdfbf9;");

        //--//
    }

    public Boolean updatePhone() throws SQLException, ClassNotFoundException {
        String newPhone = Tphone.getText().trim();
        if (newPhone.matches("[0-9]+")) {
            Integer newPhonee = Integer.parseInt(newPhone);
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "update lina_pro_clinic.therapist_provide t set T_Phone_Num = '" + newPhonee + "' where T_SSN = " + LoginT_SSN + ";"; //this is the statement to be executed
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

    public Boolean updateBirthdate() throws SQLException, ClassNotFoundException {
        LocalDate valueDate = birthDate.getValue();
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
        if (B_date.before(todayDate)) {
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "update lina_pro_clinic.therapist_provide t set T_Birth_Date = '" + newDate + "' where T_SSN = " + LoginT_SSN + ";"; //this is the statement to be executed
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

    public Boolean updatePassword() throws SQLException, ClassNotFoundException {
        String oldPass = TtxtOldPass.getText().trim();
        String newPass = TtxtNewPass.getText().trim();
        SimpleMD5Example s=new SimpleMD5Example();
        oldPass = s.hash(oldPass);
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
        String sql = "select * from lina_pro_clinic.therapist_provide where T_SSN=" + LoginT_SSN + ";"; //this is the statement to be executed
        con = a.connectDB();
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        if (rs.next() == false) {
            (new Alert(Alert.AlertType.ERROR, "Wrong Old Password")).show();
            return false;
        } else {
            if (rs.getString(6).equals(oldPass)) { //if same old password, update the database with the new password
                sql = "update lina_pro_clinic.therapist_provide t set T_Password = '" + newPass + "' where T_SSN = " + LoginT_SSN + ";";
                DB.ExecuteUpdateStatement(sql);
            }
        }
        stmt.close();
        rs.close();
        return true;
    }
    //Here Personal Information Choice ends//

    @FXML
    void setTLogOut(ActionEvent event) {
        Stage MainStage = (Stage) btnLogOut.getScene().getWindow(); // MainStage is the current stage
        // do what you have to do
        MainStage.close();
        final Stage AboutUsStage = new Stage();
        AboutUsStage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        AboutUsStage.initOwner(app_stage);
        Parent nextSceneParent = null;
        try {
            nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLFiles/sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene11 = new Scene(nextSceneParent);
        AboutUsStage.setScene(scene11);
        AboutUsStage.show();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        name.setText(Namethearpist);
        T_SSN.setText(String.valueOf(LoginT_SSN));
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(MySchedule1);
        try {
            if(dataList != null && dataList.size()!=0) dataList.clear();
            if(data != null && data.size()!=0) data.clear();
            setMySchedule();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                if ("MyAccount".equals(t1.getId())) {
                    try {
                        SetMyAccount();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if ("MySchedule1".equals(t1.getId())) {
                    try {
                        if(dataList != null && dataList.size()!=0) dataList.clear();
                        if(data != null && data.size()!=0) data.clear();
                        setMySchedule();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else if ("Salary".equals(t1.getId())) {

                    setSalary();

                }else if("PendingReservation".equals(t1.getId())){
                    if(dataList2 != null && dataList2.size()!=0) dataList2.clear();
                    if(data1 != null && data1.size()!=0) data1.clear();
                    setPendingReservation();
                }

            }


        });
    }

    //MySchedulePane Functions
    @FXML
    void setMySchedule() throws SQLException {

        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1); // number represents number of days
            java.util.Date yesterday = cal.getTime();
            String sql2 = "select S.S_ID , S.S_Session_Date , S.S_Start_Time , A.P_SSN , P.P_Name" +
                    " from patient P , session S , attend A" +
                    " where A.T_SSN=" + LoginT_SSN + " and A.S_ID=S.S_ID and A.P_SSN=P.P_SSN order by S.S_Session_Date , S.S_Start_Time  ;"; //this is the statement to be executed
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql2);

            if (rs.next() == false) {
              //  System.out.println("No reserved session!");
            } else {//one tuple at a time
                do {
                    int SessionID = Integer.parseInt(rs.getString(1));
                    Date date = Date.valueOf(rs.getString(2));
                    Time StartTime = Time.valueOf((rs.getString(3)));
                    int patientSSN = Integer.parseInt(rs.getString(4));
                    String patientName = rs.getString(5);
                    if (date.after(yesterday)) { // if the session date is equal or large than th current date then take it
                        TherapistSessionSchedule sh = new TherapistSessionSchedule(SessionID, patientSSN, patientName, date, StartTime);
                        data.add(sh); // add data into data list
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

    public void showTable(ArrayList<TherapistSessionSchedule> data) {
        SessionIDcol.setCellValueFactory(new PropertyValueFactory<TherapistSessionSchedule, Integer>("SessionIDcol"));
        SessionDatecol.setCellValueFactory(new PropertyValueFactory<TherapistSessionSchedule, Date>("SessionDatecol"));
        SessionTimecol.setCellValueFactory(new PropertyValueFactory<TherapistSessionSchedule, Time>("SessionTimecol"));
        Patientcol.setCellValueFactory(new PropertyValueFactory<TherapistSessionSchedule, Integer>("Patientcol"));
        PatientNamecol.setCellValueFactory(new PropertyValueFactory<TherapistSessionSchedule, String>("PatientNamecol"));
        dataList = FXCollections.observableArrayList(data);
        ScheduleTable.setItems(dataList); // table name
    }

    @FXML
    void setDeleteSession(ActionEvent event) {
        ObservableList<Integer> selectedIndices = ScheduleTable.getSelectionModel().getSelectedIndices();
        if (selectedIndices.size() != 0) {
            TherapistSessionSchedule row = data.get(selectedIndices.get(0));
            deleteRow(row);
            dataList.remove(selectedIndices.get(0));
            //  MStableView.refresh(); // update the tale view
            ScheduleTable.getItems().removeAll(ScheduleTable.getSelectionModel().getSelectedItem());
        }
    }

    private void deleteRow(TherapistSessionSchedule row) { // we have to update the available for the therapist
        try {
            if (row == null) return;
            // delete the session from attend table
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            con = a.connectDB();
            String SQL = "delete from attend where S_ID=" + row.getSessionIDcol() + ";"; // done
            Statement stmt1 = con.createStatement();
            stmt1.executeUpdate(SQL);
            stmt1.close();

            // update the available time for therapist
            //SQL = "insert into available values('" + row.getSessionDatecol() + "','" + row.getSessionTimecol() + "','0','0','" + LoginT_SSN + "');";
            SQL= "update lina_pro_clinic.available a set a.A_Request=0,a.A_Approve=0,a.P_SSN=null where a.A_Request=1 AND a.A_Approve=1 AND a.A_Date='"+row.getSessionDatecol()+"' AND a.A_Start_Time='"+row.getSessionTimecol()+"' AND a.T_SSN='"+LoginT_SSN+"';";
//            System.out.println(SQL);
            Statement stmt2 = con.createStatement();
            stmt2.executeUpdate(SQL);
            stmt2.close();

            // delete the session
            SQL = "delete from Session where S_ID=" + row.getSessionIDcol() + ";"; // done
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


    @FXML
    void setCalculateSalary(ActionEvent event) throws SQLException {
        try {

            Boolean fflag = false;
            LocalDate valueFrom = fromDate.getValue();
            LocalDate valueTo = toDate.getValue();
            java.util.Date dateFrom = java.sql.Date.valueOf(valueFrom);
            java.util.Date dateTo = java.sql.Date.valueOf(valueTo);
            java.util.Date todayDate = Calendar.getInstance().getTime(); //Today's Date
            if (dateFrom.after(dateTo)) {
                throw new Exception("To date must be larger than from date");
            }
            if(dateTo.after(todayDate)){
                throw new Exception("To date can't be larger than today date");
            }
            fflag = true;

            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            ArrayList<BalnceSalary> data = new ArrayList<>();
            try {
                String sql2 = "select count(a.S_ID) from lina_pro_clinic.attend a, lina_pro_clinic.session s where a.T_SSN = '"+ LoginT_SSN +"' and a.S_ID = s.S_ID and s.S_Session_Date >= '" + valueFrom + "' and s.S_Session_Date <= '" + valueTo +"';"; // return the count
                try {
                    con = a.connectDB();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                stmt = con.createStatement();
                rs = stmt.executeQuery(sql2);


                if (rs.next() == false) {
                   // System.out.println("No Session have been done by you!");
                } else {//one tuple at a time


                    int count = Integer.parseInt(rs.getString(1));

                    String sql3 = "select t.T_Session_Rate from lina_pro_clinic.therapist_provide t where t.T_SSN ='" + LoginT_SSN + "'; ";

                    try {
                        con = a.connectDB();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(sql3);

                    if (rs.next() == false) {
                       // System.out.print("Error");
                    } else {

                        double salary = count * Double.parseDouble(rs.getString(1));
                        LabelTotal.setVisible(true);
                        LabelSalary.setVisible(true);
                        LabelSalary.setText(String.valueOf(salary));
                    }
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
        }catch (Exception e){
            (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
        }
    }

    public void setSalary() {
        toDate.getEditor().clear();
        fromDate.getEditor().clear();
        LabelSalary.setText("");
        LabelSalary.setVisible(false);
        LabelTotal.setVisible(false);

    }


    void setPendingReservation(){
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null;
        //Need to check that approve=0 (Not pended yet) and that requested is 1
        String sql = "select av.A_Date,av.A_Start_Time,p.P_Name,p.P_SSN " +
                "FROM lina_pro_clinic.Patient p,lina_pro_clinic.available av " +
                "where p.P_SSN=av.P_SSN AND av.A_Request=1 AND av.A_Approve=0 AND av.T_SSN='"+ LoginT_SSN + "' order by av.A_Date,av.A_Start_Time;";
        try {
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next() != false) {
                Date date = Date.valueOf(rs.getString(1));
                Time StartTime = Time.valueOf((rs.getString(2)));
                String Name = rs.getString(3);
                int P_ID = Integer.parseInt(rs.getString(4));
                PendingSchedule temp = new PendingSchedule(date, StartTime, Name, P_ID);
                data1.add(temp);
            }

            if (data1.size() != 0)
                showTableReserve(data1);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Used for tableview in the Reserve tab
    public void showTableReserve(ArrayList<PendingSchedule> data1) {
        // PendingReservationList.addAll(data);
        SessionDatecol1.setCellValueFactory(new PropertyValueFactory<PendingSchedule, Date>("SessionDatecol1"));
        SessionTimecol1.setCellValueFactory(new PropertyValueFactory<PendingSchedule, Time>("SessionTimecol1"));
        PatientNamecol1.setCellValueFactory(new PropertyValueFactory<PendingSchedule, String>("PatientNamecol1"));
        PatientSSNcol1.setCellValueFactory(new PropertyValueFactory<PendingSchedule, Integer>("PatientSSNcol1"));
        dataList2 = FXCollections.observableArrayList(data1);
        PendingTable.setItems(dataList2); // table name
    }
    @FXML
    void setAccept(ActionEvent event) {
        ObservableList<Integer> selectedIndices = PendingTable.getSelectionModel().getSelectedIndices();
        if (selectedIndices.size() != 0) {
            int index = selectedIndices.get(0);
            Date checkDate = dataList2.get(index).getSessionDatecol1();
            Time checkTime = dataList2.get(index).getSessionTimecol1();
            int PatientSSN = dataList2.get(index).getPatientSSNcol1();
            String PatientName = dataList2.get(index).getPatientNamecol1();
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            //No need to delete from available, we need to keep all records in availabe table, just set approve to 1
            //delete from available
            String sql = "update lina_pro_clinic.available av set av.A_Approve=1 " +
                    "where av.T_SSN=" + LoginT_SSN + " AND  av.P_SSN=" + PatientSSN + " AND av.A_Start_Time='" + checkTime + "' AND av.A_Date='" + checkDate + "';";
//            .out.println("First thing, update the availcabe --- We are in therapist");
//            System.out.println(sql);
            try {
                con = a.connectDB();
                stmt = con.createStatement();
               DB.ExecuteUpdateStatement(sql);
                //insert into session
                String sql1 = "insert into lina_pro_clinic.session ( S_Start_Time , S_Price, S_Paid, S_Session_Date ) values ('" + checkTime + "','" + 50 + "','" + 0 + "','" + checkDate + "');";
                con = a.connectDB();
                stmt = con.createStatement();
                DB.ExecuteUpdateStatement(sql1);
                //Get the session_ID to store in attend which is the max in auto increment
                String sql2 = "select Max(S_ID) from lina_pro_clinic.session;";
                con = a.connectDB();
                stmt = con.createStatement();
                rs = stmt.executeQuery(sql2);
                int S_ID = 0;
                if (rs.next() != false) {
                    S_ID = Integer.parseInt(rs.getString(1));
                } else {
                    //System.out.println("Could not find S_ID");
                }
                //inseret into attened
                String sql3 = "insert into lina_pro_clinic.attend values ('" + LoginT_SSN + "','" + PatientSSN + "','" + S_ID + "');";
                con = a.connectDB();
                stmt = con.createStatement();
                DB.ExecuteUpdateStatement(sql3);
                dataList2.remove(selectedIndices.get(0));


                //  MStableView.refresh(); // update the tale view
                PendingTable.getItems().removeAll(PendingTable.getSelectionModel().getSelectedItem());
                stmt.close();
                rs.close();
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void setDecline (ActionEvent event) throws SQLException, ClassNotFoundException {
        ObservableList<Integer> selectedIndices = PendingTable.getSelectionModel().getSelectedIndices();
        if (selectedIndices.size() != 0) {
            int index = selectedIndices.get(0);
            Date checkDate = dataList2.get(index).getSessionDatecol1();
            Time checkTime = dataList2.get(index).getSessionTimecol1();
            int PatientSSN = dataList2.get(index).getPatientSSNcol1();
            String PatientName = dataList2.get(index).getPatientNamecol1();
            DBInitializing DB = new DBInitializing();  //connecting to database
            DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
            Statement stmt = null;
            ResultSet rs = null;
            //We would need two steps:
            //1) We would update the approve as -1
            String sql = "update lina_pro_clinic.available av set av.A_Approve=-1 " +
                    "where av.T_SSN=" + LoginT_SSN + " AND  av.P_SSN=" + PatientSSN + " AND av.A_Start_Time='" + checkTime + "' AND av.A_Date='" + checkDate + "';";
            con = a.connectDB();
            stmt = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
            //2) We would need to duplicate that row
            sql= "insert into lina_pro_clinic.available (A_Date,A_Start_Time,A_Request,A_Approve,T_SSN,P_SSN) values ('"+checkDate+"' , '"+checkTime+"',0,0,'"+LoginT_SSN+"','"+PatientSSN+"');";
            con = a.connectDB();
            stmt = con.createStatement();
            DB.ExecuteUpdateStatement(sql);
            PendingTable.getItems().removeAll(PendingTable.getSelectionModel().getSelectedItem());

        }
    }



}


