package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDB {

    private String user = "hdag_test";
    private String pass = "bredniak4";
    private String dbClass = "com.mysql.jdbc.Driver";
    private String dbDriver = "jdbc:mysql://db4free.net:3306/hdag_test_db";
    private Connection conn = null;

    public boolean connect() {
        boolean done = false;
        //load driver
        try {
            Class.forName(dbClass).newInstance();
            System.out.println("driver loaded"); // THIS IS BEING RETURNED
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.err.println(ex);
        }
        // Connection
        try {
            conn = DriverManager.getConnection(dbDriver, user, pass);
            System.out.println("connected"); // THIS IS NOT BEING RETURNED
            done = true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return done;
    }

    public static void main(String[] args) {
        TestDB db = new TestDB();
        if (db.connect()){
            System.out.println("connected");
        } else {
            System.out.println("disconnected");
        };
    }

}
