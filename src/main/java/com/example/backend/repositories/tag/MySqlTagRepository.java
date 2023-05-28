package com.example.backend.repositories.tag;

import com.example.backend.entities.Tag;
import com.example.backend.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlTagRepository extends MySqlAbstractRepository implements TagRepository {
    @Override
    public List<Tag> allTags() {
        List<Tag> tagList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Tag");
            while (resultSet.next()) {
                Tag tag = new Tag(resultSet.getInt("tag_id"), resultSet.getString("tag_keyword"));
                tagList.add(tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tagList;
    }

    @Override
    public Tag addTag(Tag tag) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet initialResultSet = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE tag_keyword = ?");
            preparedStatement.setString(1, tag.getTag_keyword());
            initialResultSet = preparedStatement.executeQuery();
            if (!initialResultSet.next()) {
                String[] generatedColumns = {"id"};
//            preparedStatement = connection.prepareStatement("ALTER TABLE tag AUTO_INCREMENT = 9;");
//            preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement("INSERT INTO Tag (tag_keyword) VALUES(?)", generatedColumns);
                preparedStatement.setString(1, tag.getTag_keyword());
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    tag.setTag_id(resultSet.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tag;
    }

    @Override
    public Tag findTag(Integer id) {
        Tag tag = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE tag_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer idTag = resultSet.getInt("id");
                tag = new Tag(idTag, tag.getTag_keyword());
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return tag;
    }

    @Override
    public boolean deleteTag(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Tag where tag_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return true;
    }
}
