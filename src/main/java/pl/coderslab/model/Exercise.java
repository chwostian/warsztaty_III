package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {


    /*
    +-------------+--------------+------+-----+---------+----------------+
    | Field       | Type         | Null | Key | Default | Extra          |
    +-------------+--------------+------+-----+---------+----------------+
    | id          | int(11)      | NO   | PRI | NULL    | auto_increment |
    | title       | varchar(255) | NO   | UNI | NULL    |                |
    | description | text         | YES  |     | NULL    |                |
    +-------------+--------------+------+-----+---------+----------------+
     */
    /* Zmienne-- */
    private int id;
    private String title;
    private String description;
    /*--Zmienne*/

    /*Konstruktory--*/
    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Exercise() {
    }

    /*--Konstruktory*/

    /*Gettery--*/
    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTitle() {
        return this.title;
    }

    /*--Gettery*/

    /*Settery--*/

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*--Settery*/

    /*Methods--*/
    public void saveToDB(Connection conn) throws SQLException {


        String sql = "INSERT INTO exercise(title, description) VALUES (?, ?, ?)";
        String generatedColumns[] = {"ID"};
        PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
        preparedStatement.setString(1, this.title);
        preparedStatement.setString(2, this.description);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
            System.out.println("Do bazy dopisano nowe zadania: \n" + "Zadanie: " + this.getTitle() + "\nID: " + this.getId());
        }

    }

    public void updateUserDB(Connection conn) throws SQLException {

        if (this.id != 0) {
            String sql = "UPDATE exercise SET title=?, description=? where id = ?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.executeUpdate();
            System.out.println("Zaktualizowano wpis dla zadania nr " + this.getId());
        } else {
            System.out.println("Aktualizacja treści zadania się nie powiodła.");
        }

    }

        static public Exercise loadExerciseById(Connection conn, int id) throws SQLException {
            String sql = "SELECT * FROM exercise where id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = resultSet.getInt("id");
                loadedExercise.title = resultSet.getString("title");
                loadedExercise.description = resultSet.getString("description");
                return loadedExercise;} else {
            return null;}
    }

        static public Exercise loadExerciseByTitle(Connection conn, String title) throws SQLException {
            String sql = "SELECT * FROM exercise where title=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = resultSet.getInt("id");
                loadedExercise.title = resultSet.getString("title");
                loadedExercise.description = resultSet.getString("description");
                return loadedExercise;} else {
            return null;}
    }

        static public Exercise[] loadAllExercises(Connection conn) throws SQLException {
            ArrayList<Exercise> exercises = new ArrayList<Exercise>();
            String sql = "SELECT * FROM exercise"; PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Exercise loadedExercise = new Exercise();
                loadedExercise.id = resultSet.getInt("id");
                loadedExercise.title = resultSet.getString("title");
                loadedExercise.description = resultSet.getString("description");
                exercises.add(loadedExercise);}
            Exercise[] uArray = new Exercise[exercises.size()]; uArray = exercises.toArray(uArray);
            return uArray;}

        /*--Methods*/

}


