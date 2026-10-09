package com.honorcode.dao;

import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Enrollment;
import com.honorcode.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    public boolean enrollStudent(int studentId, int courseId) throws DatabaseException {
        if (isEnrolled(studentId, courseId)) {
            return false;
        }

        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to enroll student.", e);
        }
    }

    public boolean isEnrolled(int studentId, int courseId) throws DatabaseException {
        String sql = "SELECT 1 FROM enrollments WHERE student_id = ? AND course_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Unable to check enrollment.", e);
        }
    }

    public List<Enrollment> findByStudentId(int studentId) throws DatabaseException {
        String sql = "SELECT e.*, u.full_name AS student_name, c.title AS course_title FROM enrollments e " +
                "JOIN users u ON e.student_id = u.id " +
                "JOIN courses c ON e.course_id = c.id " +
                "WHERE e.student_id = ? ORDER BY e.id DESC";

        List<Enrollment> enrollmentList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    enrollmentList.add(mapEnrollment(resultSet));
                }
            }
            return enrollmentList;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to load student enrollments.", e);
        }
    }

    public List<Enrollment> findByCourseId(int courseId) throws DatabaseException {
        String sql = "SELECT e.*, u.full_name AS student_name, c.title AS course_title FROM enrollments e " +
                "JOIN users u ON e.student_id = u.id " +
                "JOIN courses c ON e.course_id = c.id " +
                "WHERE e.course_id = ? ORDER BY e.id DESC";

        List<Enrollment> enrollmentList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    enrollmentList.add(mapEnrollment(resultSet));
                }
            }
            return enrollmentList;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to load course enrollments.", e);
        }
    }

    private Enrollment mapEnrollment(ResultSet resultSet) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(resultSet.getInt("id"));
        enrollment.setStudentId(resultSet.getInt("student_id"));
        enrollment.setCourseId(resultSet.getInt("course_id"));
        enrollment.setStudentName(resultSet.getString("student_name"));
        enrollment.setCourseTitle(resultSet.getString("course_title"));
        enrollment.setEnrolledAt(resultSet.getString("enrolled_at"));
        return enrollment;
    }
}
