package pl.coderslab.model;

import pl.coderslab.database.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main1 {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("Kaczmarek Anna");
        user.setEmail("a.kaczmarek@fibar.com");
        String password = "Anna3";
        user.setPassword(password);
        user.setNewEmail("anna.kaczmarek@fibar.com");

        user.setNewPassword(password);



        try {
            //Zapis do bazy danych
            Connection conn = DBConnector.getConnection();
            //Connection conn = DbUtil.getConn();
            //Sprawdzam czy podany email i nowy email wystepuje w bazie

            if (null != User.loadUserByEmail(conn, user.getEmail())) {
                System.out.println("Podany adres mailowy występuje już w bazie. Sprawdzam możliwość aktualizacji danych");
                if (null != User.loadUserByEmail(conn,user.getNewEmail())) {
                    System.out.println("Nowy adres mailowy występuje już w bazie i nie może być użyty. Aktualizacja nie powiodła się. Spróbuje jeszcze raz");
                } else {
                    //W wywołaniu metody będzie potrzebne sprawdzenia hasła użytkownika.
                    user.updateUserDB(conn,password, User.loadUserByEmail(conn, user.getEmail()).getId());
                }
            } else {
                user.saveToDB(conn);
            }


            //conn.close();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("Select * from users");

            while (rs.next()) {
                if (BCrypt.checkpw("Anna3",rs.getString("password"))) {
                    System.out.println(rs.getString("username") + " " + "hasło poprawne");
                } else {
                    System.out.println(rs.getString("username") + " " + "hasło niepoprawne");
                }


            }


            User[] users = User.loadAllUsers(conn);
            for (User usr: users) {
                System.out.println(usr.getUsername()+"(" + usr.getId()+")");
            }


            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
