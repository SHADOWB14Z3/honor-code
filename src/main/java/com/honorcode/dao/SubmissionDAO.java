package com.honorcode.dao;

import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Submission;
import com.honorcode.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubmissionDAO {
    public boolean submitAssignment(Submission submission) throws DatabaseException {
        String sql = "INSERT INTO submissions (assignment_id, student_id, content) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, submission.getAssignmentId());
            statement.setInt(2, submission.getStudentId());
            statement.setString(3, submission.getContent());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to submit assignment.", e);
        }
    }

    public List<Submission> findByAssignmentId(int assignmentId) throws DatabaseException {
        String sql = "SELECT s.*, u.full_name AS student_name FROM submissions s JOIN users u ON s.student_id = u.id WHERE s.assignment_id = ? ORDER BY s.id DESC";
        List<Submission> submissions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, assignmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    submissions.add(mapSubmission(resultSet));
                }
            }
            return submissions;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to load assignment submissions.", e);
        }
    }

    public Submission findByAssignmentAndStudent(int assignmentId, int studentId) throws DatabaseException {
        String sql = "SELECT s.*, u.full_name AS student_name FROM submissions s JOIN users u ON s.student_id = u.id WHERE s.assignment_id = ? AND s.student_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, assignmentId);
            statement.setInt(2, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapSubmission(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to find assignment submission.", e);
        }
    }

    private Submission mapSubmission(ResultSet resultSet) throws SQLException {
        Submission submission = new Submission();
        submission.setId(resultSet.getInt("id"));
        submission.setAssignmentId(resultSet.getInt("assignment_id"));
        submission.setStudentId(resultSet.getInt("student_id"));
        submission.setStudentName(resultSet.getString("student_name"));
        submission.setContent(resultSet.getString("content"));
        submission.setSubmittedAt(resultSet.getString("submitted_at"));
        return submission;
    }
}
