package com.honorcode.dao;

import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Result;
import com.honorcode.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {
    public boolean saveResult(Result result) throws DatabaseException {
        String sql = "INSERT INTO results (student_id, course_id, score, grade, remarks) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE score = ?, grade = ?, remarks = ?, updated_at = CURRENT_TIMESTAMP";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, result.getStudentId());
            statement.setInt(2, result.getCourseId());
            statement.setDouble(3, result.getScore());
            statement.setString(4, result.getGrade());
            statement.setString(5, result.getRemarks());
            statement.setDouble(6, result.getScore());
            statement.setString(7, result.getGrade());
            statement.setString(8, result.getRemarks());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to save result.", e);
        }
    }

    public List<Result> findByStudentId(int studentId) throws DatabaseException {
        String sql = "SELECT r.*, c.title AS course_title FROM results r " +
                "JOIN courses c ON r.course_id = c.id WHERE r.student_id = ? ORDER BY r.updated_at DESC";
        List<Result> results = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(mapResult(resultSet));
                }
            }
            return results;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to load student results.", e);
        }
    }

    private Result mapResult(ResultSet resultSet) throws SQLException {
        Result result = new Result();
        result.setId(resultSet.getInt("id"));
        result.setStudentId(resultSet.getInt("student_id"));
        result.setCourseId(resultSet.getInt("course_id"));
        result.setCourseTitle(resultSet.getString("course_title"));
        result.setScore(resultSet.getDouble("score"));
        result.setGrade(resultSet.getString("grade"));
        result.setRemarks(resultSet.getString("remarks"));
        result.setUpdatedAt(resultSet.getString("updated_at"));
        return result;
    }
}
