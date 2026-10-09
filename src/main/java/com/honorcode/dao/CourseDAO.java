package com.honorcode.dao;

// CourseDAO handles all database work related to courses.
// DAO means Data Access Object, and it keeps JDBC code separate from servlet logic.
import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Course;
import com.honorcode.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO implements CrudDAO<Course> {
    public boolean addCourse(Course course) throws DatabaseException {
        String sql = "INSERT INTO courses (title, description, teacher_id) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, course.getTitle());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getTeacherId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to add a new course.", e);
        }
    }

    public Course findById(int id) throws DatabaseException {
        String sql = "SELECT c.*, u.full_name AS teacher_name FROM courses c JOIN users u ON c.teacher_id = u.id WHERE c.id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCourse(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to find course by id.", e);
        }
    }

    public List<Course> findByTeacherId(int teacherId) throws DatabaseException {
        String sql = "SELECT c.*, u.full_name AS teacher_name FROM courses c JOIN users u ON c.teacher_id = u.id WHERE c.teacher_id = ? ORDER BY c.id DESC";
        List<Course> courseList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, teacherId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    courseList.add(mapCourse(resultSet));
                }
            }
            return courseList;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to load teacher courses.", e);
        }
    }

    public List<Course> findAvailableCoursesForStudent(int studentId) throws DatabaseException {
        String sql = "SELECT c.*, u.full_name AS teacher_name FROM courses c JOIN users u ON c.teacher_id = u.id WHERE c.id NOT IN (SELECT course_id FROM enrollments WHERE student_id = ?) ORDER BY c.id DESC";
        List<Course> courseList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    courseList.add(mapCourse(resultSet));
                }
            }
            return courseList;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to load available courses.", e);
        }
    }

    @Override
    public List<Course> findAll() throws DatabaseException {
        String sql = "SELECT c.*, u.full_name AS teacher_name FROM courses c JOIN users u ON c.teacher_id = u.id ORDER BY c.id DESC";
        List<Course> courseList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                courseList.add(mapCourse(resultSet));
            }
            return courseList;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to list all courses.", e);
        }
    }

    private Course mapCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setTitle(resultSet.getString("title"));
        course.setDescription(resultSet.getString("description"));
        course.setTeacherId(resultSet.getInt("teacher_id"));
        course.setTeacherName(resultSet.getString("teacher_name"));
        course.setCreatedAt(resultSet.getString("created_at"));
        return course;
    }
}
