



/*

+-------------+------------+------+-----+---------+----------------+
| Field       | Type       | Null | Key | Default | Extra          |
+-------------+------------+------+-----+---------+----------------+
| id          | int(11)    | NO   | PRI | NULL    | auto_increment |
| created     | datetime   | NO   |     | NULL    |                |
| updated     | datetime   | NO   |     | NULL    |                |
| description | text       | NO   |     | NULL    |                |
| exercise_id | int(11)    | NO   | MUL | NULL    |                |
| users_id    | bigint(20) | NO   | MUL | NULL    |                |
+-------------+------------+------+-----+---------+----------------+
 */





package pl.coderslab.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;

    private int exerciseId;
    private int userId;
    private static final String INSERT =
            "INSERT INTO SOLUTIONS(created, updated, description, exercise_id, users_id) VALUES (?,?,?,?,?)";
    private static final String UPDATE =
            "UPDATE SOLUTIONS SET created = ?, updated = ?, description = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM SOLUTIONS WHERE id = ?";

    private static final String SELECT_SOLUTION_WITH_ID = "SELECT * FROM SOLUTIONS WHERE id = ?";
    private static final String SELECT_ALL_SOLUTIONS = "SELECT * FROM SOLUTIONS";
    private static final String SELECT_SOLUTIONS_WITH_LIMIT = "SELECT * FROM SOLUTIONS ORDER BY updated LIMIT ?";

    public void save(Connection connection) throws SQLException {
        if (this.id == 0) {
            String[] generatedColumns = { "ID" };
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, generatedColumns);
            preparedStatement.setDate(1, this.created);
            preparedStatement.setDate(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.exerciseId);
            preparedStatement.setInt(5, this.userId);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }

    public void update(Connection connection) throws SQLException {
        if (this.id != 0) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setDate(1, this.created);
            preparedStatement.setDate(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.id);
            preparedStatement.executeUpdate();
        }
    }

    public void delete(Connection connection) throws SQLException {
        if (this.id != 0) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    public static Solution loadById(Connection connection, int solutionId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SOLUTION_WITH_ID);
        preparedStatement.setInt(1, solutionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution solution = new Solution();
            solution.setCreated(resultSet.getDate("created"));
            solution.setUpdated(resultSet.getDate("updated"));
            solution.setDescription(resultSet.getString("description"));
            solution.setId(resultSet.getInt("id"));
            solution.setExerciseId(resultSet.getInt("exercise_id"));
            solution.setUserId(resultSet.getInt("users_id"));
            return solution;
        }
        return null;
    }

    public static List<Solution> loadAll(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SOLUTIONS);
        ResultSet resultSet = preparedStatement.executeQuery();
        return solutionsFrom(resultSet);
    }

    public static List<Solution> loadAll(Connection connection, int limit) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SOLUTIONS_WITH_LIMIT);
        preparedStatement.setInt(1, limit);
        ResultSet resultSet = preparedStatement.executeQuery();
        return solutionsFrom(resultSet);
    }

    private static List<Solution> solutionsFrom(ResultSet resultSet) throws SQLException {
        List<Solution> solutions = new ArrayList<>();
        while (resultSet.next()) {
            Solution solution = new Solution();
            solution.setCreated(resultSet.getDate("created"));
            solution.setUpdated(resultSet.getDate("updated"));
            solution.setDescription(resultSet.getString("description"));
            solution.setId(resultSet.getInt("id"));
            solution.setExerciseId(resultSet.getInt("exercise_id"));
            solution.setUserId(resultSet.getInt("users_id"));
            solutions.add(solution);
        }
        return solutions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}


