package com.honorcode.dao;

import com.honorcode.exception.DatabaseException;
import com.honorcode.model.Material;
import com.honorcode.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    public boolean addMaterial(Material material) throws DatabaseException {
        String sql = "INSERT INTO materials (course_id, title, content) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, material.getCourseId());
            statement.setString(2, material.getTitle());
            statement.setString(3, material.getContent());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to add study material.", e);
        }
    }

    public List<Material> findByCourseId(int courseId) throws DatabaseException {
        String sql = "SELECT * FROM materials WHERE course_id = ? ORDER BY id DESC";
        List<Material> materials = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    materials.add(mapMaterial(resultSet));
                }
            }
            return materials;
        } catch (SQLException e) {
            throw new DatabaseException("Unable to load course materials.", e);
        }
    }

    private Material mapMaterial(ResultSet resultSet) throws SQLException {
        Material material = new Material();
        material.setId(resultSet.getInt("id"));
        material.setCourseId(resultSet.getInt("course_id"));
        material.setTitle(resultSet.getString("title"));
        material.setContent(resultSet.getString("content"));
        material.setCreatedAt(resultSet.getString("created_at"));
        return material;
    }
}
