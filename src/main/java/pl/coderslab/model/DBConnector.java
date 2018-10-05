package pl.coderslab.model;

import com.sun.istack.internal.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public DBConnector() {
    }

    @Nullable
    public static Connection getConnection() throws SQLException {

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztaty2?useSSL=false",
                "root","root");
                return conn;
    }
}
