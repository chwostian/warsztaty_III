package pl.coderslab.database;

import pl.coderslab.model.DBConnector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbUtil {
    private static DataSource ds;
    public static Connection getConn() throws SQLException {

        return DBConnector.getConnection();
        //return getInstance().getConnection();
    }

    private static DataSource getInstance() {
        if (ds == null) {
            try {
                System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
                Context ctx = new InitialContext();
                ds = (DataSource)ctx.lookup("java:comp/env/jdbc/warsztaty2");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return ds;
    }
}
