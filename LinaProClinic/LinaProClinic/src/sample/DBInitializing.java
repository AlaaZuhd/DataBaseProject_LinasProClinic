package sample;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//This class is created so that each one of us only changes  the password once
public class DBInitializing {
    private static String dbUsername = "root"; //mysql user name
    private static String dbPassword = "alaa@123"; //mysql password
    private static String URL = "127.0.0.1"; // location of db server
    private static String port = "3306"; // constant
    private static String dbName = "lina_pro_clinic"; //most likely will not change
    private static Connection con;

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getURL() {
        return URL;
    }

    public String getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }

    public Connection getCon() {
        return con;
    }

    //this funstion only updates the table in the database, and does not return any value
    public void ExecuteUpdateStatement(String SQL) throws SQLException {

        try {
            DBConn a = new DBConn(URL, port, dbName, dbUsername, dbPassword);
            con = a.connectDB();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
            con.close();
        } catch (SQLException | ClassNotFoundException s) {
            s.printStackTrace();
            System.out.println("SQL statement is not executed!");

        }
    }

}
