package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DBConn;
import sample.DBInitializing;
import sample.schedule;

import java.sql.*;
import java.util.ArrayList;
//import java.util.Date;
import java.sql.Date;
import java.util.Calendar;

import static sample.Controllers.SingInController.LoginP_SSN;

public class MyScheduleSubController extends PatientViewController {

    public static ArrayList<schedule> data = new ArrayList<>(); // to store the resulted data from the query

    private ObservableList<schedule> dataList; // to add data into the table view

    private Connection con;

    void setMyScheduleSubController() throws SQLException, SQLException {
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
