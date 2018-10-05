package pl.coderslab.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



/*

+---------------+--------------+------+-----+---------+----------------+
| Field         | Type         | Null | Key | Default | Extra          |
+---------------+--------------+------+-----+---------+----------------+
| id            | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| username      | varchar(255) | NO   |     | NULL    |                |
| email         | varchar(255) | NO   | UNI | NULL    |                |
| user_group_id | int(11)      | YES  | MUL | NULL    |                |
| password      | varchar(255) | NO   | UNI | NULL    |                |
+---------------+--------------+------+-----+---------+----------------+


 */

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String newEmail;
    private String newPassword;

    //--KONSTRUKTORY
    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.email = email;
    }
    //KONSTRUKTORY--

    //--SETTERY
    public void setPassword(String password) {this.password = BCrypt.hashpw(password, BCrypt.gensalt());}
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNewEmail(String nEmail){this.newEmail = nEmail;}
    public void setNewPassword(String nPassword) {this.newPassword = BCrypt.hashpw(nPassword, BCrypt.gensalt());}
    //SETTERY--

    //--GETTERY
    public int getId() {
        return this.id;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }
    public String getNewPassword() {return this.newPassword;}
    public String getNewEmail() {return this.newEmail;}
    //GETTERY--

    //-METHODS
    public void saveToDB(Connection conn) throws SQLException {


            String sql = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
            String generatedColumns[] = { "ID" };
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) { this.id = rs.getInt(1);
                System.out.println("Do bazy dopisano nowego użytkownika: \n" + "Nazwa użytkownika: " + this.getUsername() + "\n Email: " + this.getEmail());}

        }
    public void updateUserDB(Connection conn, String password, int id) throws SQLException {

        if (BCrypt.checkpw(password,this.password)) {
        String sql = "UPDATE users SET email=?, password=? where id = ?";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, this.newEmail);
        preparedStatement.setString(2, this.newPassword);
        preparedStatement.setInt(3,  id);
        preparedStatement.executeUpdate();
        System.out.println("Zaktualizowano wpis dla użytkownika " + this.getUsername());
        } else {
            System.out.println("Aktualizacja się nie powiodła. Aktualne hasło jest nieprawidłowe. Brak możliwości zweryfikowania użytkowniaka");
        }

    }
    static public User loadUserById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM users where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            return loadedUser;}
        return null;}

    static public User loadUserByEmail(Connection conn, String email) throws SQLException {
        String sql = "SELECT * FROM users where email=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            return loadedUser;}
        return null;}

    static public User[] loadAllUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users"; PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.password = resultSet.getString("password");
            loadedUser.email = resultSet.getString("email");
            users.add(loadedUser);}
        User[] uArray = new User[users.size()]; uArray = users.toArray(uArray);
        return uArray;}

    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }




}