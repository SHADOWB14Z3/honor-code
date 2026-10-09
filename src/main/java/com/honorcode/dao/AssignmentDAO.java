package com.honorcode.dao;

import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Assignment;
import com.honorcode.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {
    public boolean addAssignment(Assignment assignment) throws DatabaseException {
        String sql = "INSERT INTO assignments (course_id, title, description, due_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, assignment.getCourseId());
            statement.setString(2, assignment.getTitle());
            statement.setString(3, assignment.getDescription());
            statement.setString(4, assignment.getDueDate());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to add assignment.", e);
        }
    }

    public List<Assignment> findByCourseId(int courseId) throws DatabaseException {
        String sql = "SELECT * FROM assignments WHERE course_id = ? ORDER BY due_date ASC";
        List<Assignment> assignments = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    assignments.add(mapAssignment(resultSet));
                }
            }
            return assignments;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to load assignments.", e);
        }
    }

    public Assignment findById(int assignmentId) throws DatabaseException {
        String sql = "SELECT * FROM assignments WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, assignmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAssignment(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to find assignment.", e);
        }
    }

    private Assignment mapAssignment(ResultSet resultSet) throws SQLException {
        Assignment assignment = new Assignment();
        assignment.setId(resultSet.getInt("id"));
        assignment.setCourseId(resultSet.getInt("course_id"));
        assignment.setTitle(resultSet.getString("title"));
        assignment.setDescription(resultSet.getString("description"));
        assignment.setDueDate(resultSet.getString("due_date"));
        assignment.setCreatedAt(resultSet.getString("created_at"));
        return assignment;
    }
}
