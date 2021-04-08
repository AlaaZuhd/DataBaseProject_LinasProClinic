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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


import static sample.Controllers.Controller.LoginP_SSN;

public class AdminViewController implements Initializable {

    private Connection con;
    ArrayList<schedule> data = new ArrayList<>();
    private ObservableList<schedule> dataList; // to add data into the table view

    ArrayList<schedule> subData = new ArrayList<>();
    private ObservableList<schedule> subDataList; // to add data into the table view


    ArrayList<patientReport> dataforPatientReport = new ArrayList<>();
    private ObservableList<patientReport> dataListForPatientReport; // to add data into the table view

    ArrayList<patientReport> subDataForPatientReport = new ArrayList<>();
    private ObservableList<patientReport> subDataListForPatientReport; // to add data into the table view

    @FXML
    private VBox SidePanel;

    @FXML
    private Label name;

    @FXML
    private Button btnLogOut;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab therapistsReport;

    @FXML
    private AnchorPane PaneTherapistsReport;

    //Table to show the therapists
    @FXML
    private TableView<schedule> therapistsTableView;

    @FXML
    private TableColumn<schedule, String> Therapist_Name;

    @FXML
    private TableColumn<schedule, Integer> Therapist_ID;

    @FXML
    private TableColumn<schedule, String> Therapy_Type;

    @FXML
    private TableColumn<schedule, Double> Session_Rate;

    @FXML
    private TableColumn<schedule, String> Phone_Number;

    //table for sessions for the therapist
    @FXML
    private TableView<schedule> therapistsSessionsTableView;

    @FXML
    private TableColumn<schedule, Integer> Session_ID;

    @FXML
    private TableColumn<schedule, Date> Session_Date;

    @FXML
    private TableColumn<schedule, Time> Session_Time;

    @FXML
    private TableColumn<schedule, String> Patient_Name;

    @FXML
    private TableColumn<schedule, Integer> Patient_ID;

    @FXML
    private Button btnTReport;

    @FXML
    private DatePicker Tfrom;

    @FXML
    private DatePicker Tto;

    @FXML
    private Tab patientsReport;

    @FXML
    private AnchorPane PanePatientsReport;

    @FXML
    private Label PILabelName;

    @FXML
    private Label PILabelSSN;

    @FXML
    private TextField PITxfPhone;

    @FXML
    private TextField PITxfGender;

    @FXML
    private TextField txfBirthDate;

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
    private Button btnEdit;

    @FXML
    private Tab financialReport;


    //For Financial Report
    @FXML
    private DatePicker fromDateFinancial;

    @FXML
    private DatePicker toDateFinancial;

    @FXML
    private BarChart<String, Number> financialBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Button btnFReport;

    // patient Report tab
    @FXML
    private  Label labelPatientName;

    @FXML
    private  Label labelPatientID;

    @FXML
    private  Label labelCompanionName;

    @FXML
    private  Label labelCompanionPhoneNumber;


    @FXML
    private TableColumn<patientReport, Integer> Therapist_IDP;

    @FXML
    private TableColumn<patientReport, String> Therapist_NameP;

    @FXML
    private TableColumn<patientReport, Time> Session_TimeP;

    @FXML
    private TableColumn<patientReport, String> Therapy_TypeP;

    @FXML
    private TableColumn<patientReport, Date> Session_DateP;




    @FXML
    private TableColumn<patientReport, Integer> Patient_ID11;


    @FXML
    private TableColumn<patientReport, String> Patient_Name11;


    @FXML
    private TableView<patientReport> patientTableView11;

    @FXML
    private TableView<patientReport> patientTableView1;

    @FXML
    private DatePicker Pfrom1;


    @FXML
    private TableColumn<patientReport, String> Patient_Name1;

    @FXML
    private TableColumn<patientReport, String> Companion_Phone_Num11;

    @FXML
    private TableColumn<patientReport, String> Companion_Phone_Num1;

    @FXML
    private TableColumn<patientReport, String> Patient_Gender1;



    @FXML
    private Button btnPBack;


    @FXML
    private Label labelPTo;



    @FXML
    private TableColumn<patientReport, String> Companion_Name1;


    @FXML
    private TableColumn<patientReport, Integer> Patient_ID1;


    @FXML
    private DatePicker Pto1;

    @FXML
    private Label labelPFrom;


    @FXML
    private TableColumn<patientReport, String> Patient_Gender11;

    @FXML
    private Button btnPReport1;

    @FXML
    private TableColumn<patientReport, String> Companion_Name11;


    // for statistics pane
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
    private Label totalNumberOfTHerapists;

    @FXML
    private Pane patientStatisticsPane;

    @FXML
    private Pane companionStatisticsPane;

    @FXML
    private Label totalnumberOfPatients ;

    @FXML
    private Label totalNumberOfCompanions ;

    @FXML
    private NumberAxis companionY;

    @FXML
    private CategoryAxis companionX;

    @FXML
    private BarChart<?, ?> companionChart;

    @FXML
    private Button  btntherapistsStatistics;

    @FXML
    private Button  btncompanionStatistics;

    @FXML
    private Button  btnpatientsStatistics;

    @FXML
    private Button btnBackT;



    // for pendeing therapist pane





    public void setPatientsReport(){
        // get Therapist table ready
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        String sql = "SELECT p.P_SSN,p.P_Name,p.P_Gender " +
                "FROM lina_pro_clinic.patient p order by p.P_SSN ;";

        try {
            con = a.connectDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Statement stmt = null;
        try {
            stmt = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
//              patientReport temp = new patientReport(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(4),rs.getString(5),rs.getString(3));
                patientReport temp = new patientReport(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3));
                dataforPatientReport.add(temp);
            }
            sql = "SELECT p.P_SSN , c.C_Name,c.C_Phone_Num " +
                    "FROM lina_pro_clinic.companion_accompany c , lina_pro_clinic.patient p " +
                    "where c.P_SSN = c.P_SSN order by p.P_SSN;  ";
            rs = stmt.executeQuery(sql);
            int i =0 ;
            while(rs.next()){
                for(;i<dataforPatientReport.size();i++){
                    if(dataforPatientReport.get(i).getPatient_ID1() == Integer.parseInt(rs.getString(1))){
                        dataforPatientReport.get(i).setCompanion_Name1(rs.getString(2));
                        dataforPatientReport.get(i).setCompanion_Phone_Num1(rs.getString(3));
                        break  ;
                    }
                }
            }
            showTableForPatientReport(dataforPatientReport);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void showTableForPatientReport(ArrayList<patientReport> dataforPatientReport){
        Patient_Name1.setCellValueFactory(new PropertyValueFactory<patientReport, String>("Patient_Name1"));
        Patient_ID1.setCellValueFactory(new PropertyValueFactory<patientReport, Integer>("Patient_ID1"));
        Companion_Name1.setCellValueFactory(new PropertyValueFactory<patientReport, String>("Companion_Name1"));
        Companion_Phone_Num1.setCellValueFactory(new PropertyValueFactory<patientReport, String>("Companion_Phone_Num1"));
        Patient_Gender1.setCellValueFactory(new PropertyValueFactory<patientReport, String>("Patient_Gender1"));
        dataListForPatientReport = FXCollections.observableArrayList(dataforPatientReport);
        patientTableView1.setItems(dataListForPatientReport); // table name
    }


    @FXML
    void setPBack(ActionEvent event) {
        patientTableView1.setVisible(true);
        Pto1.setVisible(true);
        Pfrom1.setVisible(true);
        labelPFrom.setVisible(true);
        labelPTo.setVisible(true);
        btnPReport1.setVisible(true);
        //////////////////////////////////
        patientTableView11.setVisible(false);
        btnPBack.setVisible(false);
        labelCompanionName.setVisible(false);
        labelCompanionPhoneNumber.setVisible(false);
        labelPatientID.setVisible(false);
        labelPatientName.setVisible(false);

        labelPatientName.setText("Patient Name : ");
        labelPatientID.setText("Patient ID : ");
        labelCompanionName.setText("Companion Name : ");
        labelCompanionPhoneNumber.setText("Companion ID : ");
    }

    @FXML
    void setPReport(ActionEvent event) throws SQLException, ClassNotFoundException {
        Boolean fflag= false ;
        try{
            LocalDate valueFrom = Pfrom1.getValue();
            LocalDate valueTo = Pto1.getValue();
            if(valueFrom == null || valueTo == null) throw new Exception("Problem with dates");
            labelCompanionName.setVisible(true);
            labelCompanionPhoneNumber.setVisible(true);
            labelPatientID.setVisible(true);
            labelPatientName.setVisible(true);
            patientTableView1.setVisible(false);
            Pto1.setVisible(false);
            Pfrom1.setVisible(false);
            labelPFrom.setVisible(false);
            labelPTo.setVisible(false);
            btnPReport1.setVisible(false);
            //////////////////////////////////
            patientTableView11.setVisible(true);
            btnPBack.setVisible(true);
            ObservableList<Integer> selectedIndices = patientTableView1.getSelectionModel().getSelectedIndices(); //get the index of the selected row
            if (selectedIndices.size() != 0) {
                //Getting the dates
                fflag = true;
                //Getting the selected row
                int P_SSN = dataListForPatientReport.get(selectedIndices.get(0)).getPatient_ID1();
                labelPatientName.setText(labelPatientName.getText() + dataListForPatientReport.get(selectedIndices.get(0)).getPatient_Name1());
                labelPatientID.setText(labelPatientID.getText() + dataListForPatientReport.get(selectedIndices.get(0)).getPatient_ID1());
                labelCompanionName.setText(labelCompanionName.getText() + dataListForPatientReport.get(selectedIndices.get(0)).getCompanion_Name1());
                labelCompanionPhoneNumber.setText(labelCompanionPhoneNumber.getText() + dataListForPatientReport.get(selectedIndices.get(0)).getCompanion_Phone_Num1());
                DBInitializing DB = new DBInitializing();  //connecting to database
                DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
                Statement stmt = null;

                // change from here

                String sql = "select t.T_SSN,t.T_Name,th.Therapy_Type,s.S_Session_Date, s.S_Start_Time " +
                        "from lina_pro_clinic.therapist_provide t, lina_pro_clinic.attend a, lina_pro_clinic.session s, lina_pro_clinic.therapy th " +
                        "where t.T_SSN = a.T_SSN and a.S_ID = s.S_ID and t.Therapy_ID = th.Therapy_ID and a.P_SSN ='" + P_SSN + "' AND s.S_Session_Date >='"+valueFrom+"' AND s.S_Session_Date <='"+valueTo+"';";
                con = a.connectDB();
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next() == false) {
                    (new Alert(Alert.AlertType.ERROR, "No Sessions for this patient")).show();
                } else {
                    do {
                        Date date = Date.valueOf(rs.getString(4));
                        Time time = Time.valueOf(rs.getString(5));
                        patientReport temp = new patientReport(rs.getString(2), Integer.parseInt(rs.getString(1)), rs.getString(3), date, time);
                        subDataForPatientReport.add(temp);
                    } while (rs.next() != false);
                    showTable2ForPatientReport(subDataForPatientReport);
                }


                stmt.close();
                con.close();
                rs.close();
            } else {
                (new Alert(Alert.AlertType.ERROR, "You have to select a patient to get it's report!")).show();
            }
        }catch (Exception e){
            if(fflag)
                (new Alert(Alert.AlertType.ERROR, "Missing data for report")).show();
            else
                (new Alert(Alert.AlertType.ERROR, "You have to choose the dates")).show();
        }

        /////////////////
    }

    public void showTable2ForPatientReport(ArrayList<patientReport> subDataForPatientReport){

        Therapist_NameP.setCellValueFactory(new PropertyValueFactory<patientReport, String>("Therapist_NameP"));
        Therapist_IDP.setCellValueFactory(new PropertyValueFactory<patientReport, Integer>("Therapist_IDP"));
        Session_TimeP.setCellValueFactory(new PropertyValueFactory<patientReport, Time>("Session_TimeP"));
        Session_DateP.setCellValueFactory(new PropertyValueFactory<patientReport, Date>("Session_DateP"));
        Therapy_TypeP.setCellValueFactory(new PropertyValueFactory<patientReport, String>("Therapy_TypeP"));
        subDataListForPatientReport = FXCollections.observableArrayList(subDataForPatientReport);
        patientTableView11.setItems(subDataListForPatientReport); // table name
    }


    //The first Tab
    public void setTherapistsReport(){
        // get Therapist table ready
        btnTReport.setVisible(true);
        btnBackT.setVisible(false);
        therapistsSessionsTableView.setVisible(false);
        therapistsTableView.setVisible(true);
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        String sql = "select t.T_Name,t.T_SSN,th.Therapy_Type,t.T_Session_Rate,t.T_Phone_Num " +
                "from lina_pro_clinic.therapist_provide t,lina_pro_clinic.therapy th " +
                "where t.Therapy_ID=th.Therapy_ID;"; //this is the statement to be executed
        try {
            con = a.connectDB();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Statement stmt = null;
        try {
            stmt = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                schedule temp = new schedule(rs.getString(1), Integer.parseInt(rs.getString(2)), rs.getString(3), Double.parseDouble(rs.getString(4)), rs.getString(5));
                data.add(temp);
            }
            showTable(data);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //printing the table of all therapists
    public void showTable(ArrayList<schedule> data) { // show the table of the MySchedule option
        Therapist_Name.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapist_Name"));
        Therapist_ID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Therapist_ID"));
        Therapy_Type.setCellValueFactory(new PropertyValueFactory<schedule, String>("Therapy_Type"));
        Session_Rate.setCellValueFactory(new PropertyValueFactory<schedule, Double>("Session_Rate"));
        Phone_Number.setCellValueFactory(new PropertyValueFactory<schedule, String>("Phone_Number"));
        dataList = FXCollections.observableArrayList(data);
        therapistsTableView.setItems(dataList); // table name
    }

    //Getting the sessions for a specific therapist
    @FXML
    void setTReport(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Integer> selectedIndices = therapistsTableView.getSelectionModel().getSelectedIndices(); //get the index of the selected row
            if (selectedIndices.size() != 0) {
                //Getting the dates
                LocalDate valueFrom = Tfrom.getValue();
                LocalDate valueTo = Tto.getValue();
                if (valueFrom == null || valueTo == null) throw new Exception("You have to choose the dates");
                //Getting the selected row
                int T_SSN = data.get(selectedIndices.get(0)).getTherapist_ID();
                DBInitializing DB = new DBInitializing();  //connecting to database
                DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
                Statement stmt = null;
                String sql = "select S.S_ID , S.S_Session_Date , S.S_Start_Time , P.P_Name ,P.P_SSN from lina_pro_clinic.patient P , lina_pro_clinic.session S , lina_pro_clinic.attend A where A.T_SSN='" + T_SSN + "' and A.S_ID=S.S_ID and A.P_SSN=P.P_SSN and S.S_Session_Date>= '" + valueFrom + "' and S.S_Session_Date<= '" + valueTo + "' order by S.S_Session_Date , S.S_Start_Time  ;";
                con = a.connectDB();
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next() == false) {
                    (new Alert(Alert.AlertType.ERROR, "No Sessions for this therapist")).show();
                } else {
                    if(subData!=null && subData.size()!=0) subData.clear();
                    do {
                        int S_ID = Integer.parseInt(rs.getString(1));
                        Date date = Date.valueOf(rs.getString(2));
                        Time time = Time.valueOf(rs.getString(3));
                        String name = rs.getString(4);
                        int P_ID = Integer.parseInt(rs.getString(5));
                        schedule temp = new schedule(S_ID, date, time, name, P_ID);
                        subData.add(temp);
                    } while (rs.next() != false);
                    showTable2(subData);
                }


                stmt.close();
                con.close();
                rs.close();
                btnBackT.setVisible(true);
                btnTReport.setVisible(false);
            } else {
                (new Alert(Alert.AlertType.ERROR, "You have to select a therapist to get a report for it")).show();
            }
        } catch (Exception e){
            (new Alert(Alert.AlertType.ERROR, e.toString())).show();
        }
    }

    //Printing the sessions of a specific therapist in a table
    public void showTable2(ArrayList<schedule> subData) { // show the table of the MySchedule option
        therapistsTableView.setVisible(false);
        therapistsSessionsTableView.setVisible(true);
        Session_ID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Session_ID"));
        Session_Date.setCellValueFactory(new PropertyValueFactory<schedule, Date>("Session_Date"));
        Session_Time.setCellValueFactory(new PropertyValueFactory<schedule, Time>("Session_Time"));
        Patient_Name.setCellValueFactory(new PropertyValueFactory<schedule, String>("Patient_Name"));
        Patient_ID.setCellValueFactory(new PropertyValueFactory<schedule, Integer>("Patient_ID"));
        subDataList = FXCollections.observableArrayList(subData);
        therapistsSessionsTableView.setItems(subDataList); // table name
    }
    //This method to return to the original table view
    @FXML
    void setBackT(ActionEvent event) {
        therapistsSessionsTableView.setVisible(false);
        therapistsTableView.setVisible(true);
        btnBackT.setVisible(false);
        btnTReport.setVisible(true);
    }

    //Financial Report Tab
    public void setFinancialReport() {

    }

    @FXML
    void setFReport(ActionEvent event) throws SQLException, ClassNotFoundException {
        financialBarChart.getData().clear();
        Boolean flag = true;
        Boolean fflag= false ;
        //Getting the dates
        try {

            LocalDate valueFrom = fromDateFinancial.getValue();
            LocalDate valueTo = toDateFinancial.getValue();
            java.util.Date dateFrom = java.sql.Date.valueOf(valueFrom);
            java.util.Date todayDate = Calendar.getInstance().getTime(); //Today's Date
            fflag = true;
            if (dateFrom.after(todayDate)) {
                throw new Exception("To date must be larger than from date");
            }
            //-------------//
            // Need to ristrict the Date Picker
            //-----------//
            double sessionPrices = 0;
            double therapistsSalaries = 0;
            if (flag) {
                //Gettin gained money from sessions prices
                DBInitializing DB = new DBInitializing();  //connecting to database
                DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
                Statement stmt = null;
                String sql = "select sum(s.S_Price)" +
                        "from lina_pro_clinic.session s " +
                        "where s.S_Paid=1 AND s.S_Session_Date>= '" + valueFrom + "' And s.S_Session_Date<= '" + valueTo + "' ;";
                con = a.connectDB();
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next() == false) {

                } else {
                    if (rs.getString(1) != null) {
                        sessionPrices = Double.parseDouble(rs.getString(1));
                    }
                }

                //Getting the all the therpaist in the database, store their T_SSN, Session_rate in array to loop throug
                a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
                sql = "select T_SSN,T_Session_Rate from lina_pro_clinic.therapist_provide;";
                con = a.connectDB();
                stmt = con.createStatement();
                rs = stmt.executeQuery(sql);
                if (rs.next() == false) {

                } else {
                    do {
                        int T_SSN = Integer.parseInt(rs.getString(1));
                        double session_rate = Double.parseDouble(rs.getString(2));
                        schedule temp = new schedule(T_SSN, session_rate);
                        data.add(temp);
                    } while (rs.next() != false);
                }
                if (data != null && data.size() != 0) {
                    //Looping through all therpaists and get their number of session and multiply it by theri session rate
                    for (int i = 0; i < data.size(); i++) {
                        int T_SSN = data.get(i).getTherapist_ID();
                        a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
                        sql = "select count(S.S_ID) from lina_pro_clinic.patient P , lina_pro_clinic.session S , lina_pro_clinic.attend A where A.T_SSN='" + T_SSN + "' and A.S_ID=S.S_ID and A.P_SSN=P.P_SSN and S.S_Session_Date>= '" + valueFrom + "' and S.S_Session_Date<= '" + valueTo + "' order by S.S_Session_Date , S.S_Start_Time  ;";
                        con = a.connectDB();
                        stmt = con.createStatement();
                        rs = stmt.executeQuery(sql);
                        if (rs.next() == false)
                            continue;
                        else {
                            int numberOfSessions = Integer.parseInt(rs.getString(1));
                            double sessionRate = data.get(i).getSession_Rate();
                            therapistsSalaries += (numberOfSessions * sessionRate);
                        }
                    }
                }

                XYChart.Series series1 = new XYChart.Series();
                series1.setName("Earned From Session");
                series1.getData().add(new XYChart.Data("", sessionPrices));

                XYChart.Series series2 = new XYChart.Series();
                series2.setName("Spent For Salaries");
                series2.getData().add(new XYChart.Data("", therapistsSalaries));
                financialBarChart.getData().addAll(series1, series2);
            }
        } catch (Exception e){
            if(fflag)
                (new Alert(Alert.AlertType.ERROR, e.getMessage())).show();
            else
                (new Alert(Alert.AlertType.ERROR,"You have to choose the dates")).show();
        }

    }

    @FXML
    void setLogOut(ActionEvent event) throws IOException {
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


    void setStatistics() throws SQLException {
        therapistChart.getData().clear();
        patientChart.getData().clear();
        companionChart.getData().clear();

        therapistChart.getData().clear();

        int therapistsCount = 0 ;

        PaneStatistics.setVisible(true);
        companionStatisticsPane.setVisible(false);
        patientStatisticsPane.setVisible(false);

        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs = null ;
        try {
            java.util.Date todayDate = Calendar.getInstance().getTime();
            // query to get the therapy type with the number of therapist given that type
            String sql = "select th.Therapy_Type " +
                    "from lina_pro_clinic.therapy th " +
                    "order by th.Therapy_Type ;";
            String sql1 = "select th.Therapy_Type, count(t.T_SSN) " +
                    "from lina_pro_clinic.therapy th, lina_pro_clinic.therapist_provide t " +
                    "where t.Therapy_ID = th.therapy_id Group by th.therapy_id order by th.Therapy_Type;";
            String sql3 = "select count(t.T_SSN) from lina_pro_clinic.therapist_provide t;";

            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next() == false){
            }else {
                Map map = new HashMap();
                do{
                    String therapy_type = rs.getString(1);
                    therapy_therapist_patient obj = new therapy_therapist_patient(therapy_type);
                    map.put(therapy_type,obj);
                }while(rs.next());
                // now get the count of therapist
                rs1 = stmt.executeQuery(sql1);
                if(rs1.next() == false ){
                }else{
                    do{
                        therapy_therapist_patient obj ;
                        obj = (therapy_therapist_patient)map.get(rs1.getString(1));
                        obj.setNumber_Of_Therapist(Integer.parseInt(rs1.getString(2)));
                    }while(rs1.next());
                }

                con = a.connectDB();
                stmt = con.createStatement();
                rs2 = stmt.executeQuery(sql3);
                if(rs2.next()==false){

                }else {
                    therapistsCount = Integer.parseInt(rs2.getString(1));
                }

                totalNumberOfTHerapists.setText("Total number of Therapists : " + therapistsCount);

                XYChart.Series set1 = new XYChart.Series<>();
                Iterator mapIterator = map.entrySet().iterator();
                while(mapIterator.hasNext()){
                    Map.Entry mapElement = (Map.Entry)mapIterator.next();
                    therapy_therapist_patient obj = (therapy_therapist_patient)map.get(mapElement.getKey());
                    set1.getData().add(new XYChart.Data(""+mapElement.getKey(),new Integer (obj.getNumber_Of_Therapist())));
                }
                therapistChart.getData().addAll(set1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
                con.close();
                rs1.close();
                rs.close();
                rs2.close();
            }
        }



    }

    @FXML
    void setStatCompanion(ActionEvent event){

        companionChart.getData().clear();

        PaneStatistics.setVisible(false);
        companionStatisticsPane.setVisible(true);
        patientStatisticsPane.setVisible(false);

        int compaionsCount = 0 ;

        String sql = "select c.C_Name, count(*) from lina_pro_clinic.companion_accompany c group by c.C_Name;";

        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs = null ;

        try{
            Map map = new HashMap();

            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next() == false){
            }else {
                do{
                    compaionsCount ++ ;
                    int countOfPatientsPerCompanion = Integer.parseInt(rs.getString(2));
                    // check if this count is at the map as key or not, if it's then increment it, else set it to one '
                    if(map.get(countOfPatientsPerCompanion) == null )
                        map.put(countOfPatientsPerCompanion,1);
                    else {
                        int count = new Integer((Integer) map.get(countOfPatientsPerCompanion));
                        count ++ ;
                        map.put(countOfPatientsPerCompanion,count);
                    }
                }while(rs.next());

            }
            XYChart.Series set2 = new XYChart.Series<>();
            Iterator mapIterator = map.entrySet().iterator();
            int i=1;
            while(mapIterator.hasNext()){
                Map.Entry mapElement = (Map.Entry)mapIterator.next();
                int count = new Integer((Integer) map.get(mapElement.getKey()));
                set2.getData().add(new XYChart.Data("Category" + i + " "+map.get(mapElement.getKey())+" Companion",mapElement.getKey()));
                i++;
            }
            companionChart.getData().addAll(set2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        totalNumberOfCompanions.setText("Total Number of Companions : " + compaionsCount);

    }

    @FXML
    void setStatPatient(ActionEvent event) throws SQLException {

        patientChart.getData().clear();

        int patientCount = 0 ;

        PaneStatistics.setVisible(false);
        companionStatisticsPane.setVisible(false);
        patientStatisticsPane.setVisible(true);

        // get therapy types and number of patients for each therapy
        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs = null ;
        try {
            java.util.Date todayDate = Calendar.getInstance().getTime();
            // query to get the therapy type with the number of therapist given that type
            String sql = "select th.Therapy_Type " +
                    "from lina_pro_clinic.therapy th " +
                    "order by th.Therapy_Type ;";
            String sql2 = "select th.Therapy_Type, count(t.P_SSN) " +
                    "from lina_pro_clinic.therapy th, lina_pro_clinic.takes t " +
                    "where t.Therapy_ID = th.therapy_id Group by th.therapy_id order by th.Therapy_Type ;";
            String sql3 = "select count(p.P_SSN) from lina_pro_clinic.patient p;";
            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next() == false){
            }else {
                Map map = new HashMap();
                do{
                    String therapy_type = rs.getString(1);
                    therapy_therapist_patient obj = new therapy_therapist_patient(therapy_type);
                    map.put(therapy_type,obj);
                }while(rs.next());

                // get the count of patients
                rs2 = stmt.executeQuery(sql2);
                if(rs2.next() == false ){
                }else{
                    do{
                        therapy_therapist_patient obj ;
                        obj = (therapy_therapist_patient)map.get(rs2.getString(1));
                        obj.setNumber_Of_Patient(Integer.parseInt(rs2.getString(2)));

                    }while(rs2.next());
                }

                con = a.connectDB();
                stmt = con.createStatement();
                rs1 = stmt.executeQuery(sql3);
                if(rs1.next()==false){
                }else {
                    patientCount = Integer.parseInt(rs1.getString(1));
                }

                totalnumberOfPatients.setText("Total Number of Patients : " + patientCount);

                XYChart.Series set2 = new XYChart.Series<>();
                Iterator mapIterator = map.entrySet().iterator();
                while(mapIterator.hasNext()){
                    Map.Entry mapElement = (Map.Entry)mapIterator.next();
                    therapy_therapist_patient obj = (therapy_therapist_patient)map.get(mapElement.getKey());
                    set2.getData().add(new XYChart.Data(""+mapElement.getKey(),new Integer(obj.getNumber_Of_Patient())));
                }
                patientChart.getData().addAll(set2);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
                con.close();
                rs.close();
                rs2.close();
                rs1.close();
            }
        }

    }

    @FXML
    void setStatTherapist(ActionEvent event) throws SQLException {

        therapistChart.getData().clear();

        int therapistsCount = 0 ;

        PaneStatistics.setVisible(true);
        companionStatisticsPane.setVisible(false);
        patientStatisticsPane.setVisible(false);

        DBInitializing DB = new DBInitializing();  //connecting to database
        DBConn a = new DBConn(DB.getURL(), DB.getPort(), DB.getDbName(), DB.getDbUsername(), DB.getDbPassword());
        Statement stmt = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs = null ;
        try {
            java.util.Date todayDate = Calendar.getInstance().getTime();
            // query to get the therapy type with the number of therapist given that type
            String sql = "select th.Therapy_Type " +
                    "from lina_pro_clinic.therapy th " +
                    "order by th.Therapy_Type ;";
            String sql1 = "select th.Therapy_Type, count(t.T_SSN) " +
                    "from lina_pro_clinic.therapy th, lina_pro_clinic.therapist_provide t " +
                    "where t.Therapy_ID = th.therapy_id Group by th.therapy_id order by th.Therapy_Type;";
            String sql3 = "select count(t.T_SSN) from lina_pro_clinic.therapist_provide t;";

            con = a.connectDB();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            if(rs.next() == false){

            }else {
                Map map = new HashMap();
                do{
                    String therapy_type = rs.getString(1);
                    therapy_therapist_patient obj = new therapy_therapist_patient(therapy_type);
                    map.put(therapy_type,obj);
                }while(rs.next());
                // now get the count of therapist
                rs1 = stmt.executeQuery(sql1);
                if(rs1.next() == false ){
                }else{
                    do{
                        therapy_therapist_patient obj ;
                        obj = (therapy_therapist_patient)map.get(rs1.getString(1));
                        obj.setNumber_Of_Therapist(Integer.parseInt(rs1.getString(2)));
                    }while(rs1.next());
                }

                con = a.connectDB();
                stmt = con.createStatement();
                rs2 = stmt.executeQuery(sql3);
                if(rs2.next()==false){
                }else {
                    therapistsCount = Integer.parseInt(rs2.getString(1));
                }

                totalNumberOfTHerapists.setText("Total Number of Therapists : " + therapistsCount);

                XYChart.Series set1 = new XYChart.Series<>();
                Iterator mapIterator = map.entrySet().iterator();
                while(mapIterator.hasNext()){
                    Map.Entry mapElement = (Map.Entry)mapIterator.next();
                    therapy_therapist_patient obj = (therapy_therapist_patient)map.get(mapElement.getKey());
                    set1.getData().add(new XYChart.Data(""+mapElement.getKey(),new Integer (obj.getNumber_Of_Therapist())));
                }
                therapistChart.getData().addAll(set1);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (stmt != null) {
                stmt.close();
                con.close();
                rs1.close();
                rs.close();
                rs2.close();
            }
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTherapistsReport();
        // TODO
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>()
        {
            @Override
            public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                if ("therapistsReport".equals(t1.getId())) {
                    if(data!=null &&data.size()!=0)data.clear();
                    if(subData!=null &&subData.size()!=0)subData.clear();
                    if(dataList!=null &&dataList.size()!=0)dataList.clear();
                    if(subDataList!=null &&subDataList.size()!=0)subDataList.clear();
                    setTherapistsReport();

                }
                if ("financialReport".equals(t1.getId())) {
                    if(data!=null &&data.size()!=0)data.clear();

                    setFinancialReport();
                }
                if("patientsReport".equals(t1.getId())){
                    if(dataforPatientReport !=null && dataforPatientReport.size()!=0) dataforPatientReport.clear();
                    if(dataListForPatientReport != null && dataListForPatientReport.size()!=0) dataListForPatientReport.clear();
                    if(subDataForPatientReport !=null && subDataForPatientReport.size()!=0) subDataForPatientReport.clear();
                    if(subDataListForPatientReport != null && subDataListForPatientReport.size()!=0) subDataListForPatientReport.clear();
                    setPatientsReport();
                }
                if("StatisticsAboutTheClinic".equals(t1.getId())){
                    try {
                        setStatistics();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

    }
}
