package pl.coderslab.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserGroup {


/*
+-------+--------------+------+-----+---------+----------------+
| Field | Type         | Null | Key | Default | Extra          |
+-------+--------------+------+-----+---------+----------------+
| id    | int(11)      | NO   | PRI | NULL    | auto_increment |
| name  | varchar(255) | NO   | UNI | NULL    |                |
+-------+--------------+------+-----+---------+----------------+

*/

private int id;
private String name;
    //KONSTRUKTORY--
    public UserGroup(String name) {
        this.name = name;
    }

    public UserGroup() {}

    //--KONSTRUKTORY

    //GETTERY--
    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }
    //--GETTERY

    //SETTERY--
    public void setName(String name) {
        this.name = name;
    }
    //--SETTERY

    //METHODS--
    public void saveToDB(Connection conn) throws SQLException {

            String sql = "INSERT INTO user_group(name) VALUES (?)";
            String generatedColumns[] = { "ID" };
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.name);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) { this.id = rs.getInt(1);}

        }

    public void updateGroupDB(Connection conn, int id) throws SQLException {

            String sql = "UPDATE user_group SET name=? where id = ?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

    }

    public void delete(Connection conn, int id) throws SQLException {

            String sql = "DELETE FROM user_group WHERE id=?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();

        }

    static public UserGroup loadGroupById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM user_group where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            UserGroup loadedGroup = new UserGroup();
            loadedGroup.id = resultSet.getInt("id");
            loadedGroup.name = resultSet.getString("name");
            return loadedGroup;}
        return null;}

    static public UserGroup[] loadAllGroups(Connection conn) throws SQLException {
        ArrayList<UserGroup> groups = new ArrayList<UserGroup>();
        String sql = "SELECT * FROM user_group";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserGroup loadedGroup = new UserGroup();
            loadedGroup.id = resultSet.getInt("id");
            loadedGroup.name = resultSet.getString("name");
            groups.add(loadedGroup);}
        UserGroup[] uArray = new UserGroup[groups.size()];
        uArray = groups.toArray(uArray);
        return uArray;}
    //--METHODS
}
