package com.example.backend.repositories.comment;

import com.example.backend.entities.Category;
import com.example.backend.entities.Comment;
import com.example.backend.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {
    @Override
    public List<Comment> allComments(Integer news_id) {
        List<Comment> commentList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.prepareStatement("SELECT * FROM Comment WHERE comment_news = ? ORDER BY comment_date_created DESC");
            statement.setInt(1, news_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment(resultSet.getInt("comment_id"), resultSet.getString("comment_author"),
                        resultSet.getString("comment_text"), resultSet.getLong("comment_date_created"), news_id);
                commentList.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return commentList;
    }

    @Override
    public Comment addComment(Comment comment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"category_id"};
            preparedStatement = connection.prepareStatement("INSERT INTO Comment (comment_author, comment_text, comment_date_created, comment_news) VALUES (?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getComment_author());
            preparedStatement.setString(2, comment.getComment_text());
            preparedStatement.setLong(3, comment.getComment_date_created());
            preparedStatement.setInt(4, comment.getComment_news());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                comment.setComment_id(resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public Comment findComment(Integer id) {
        Comment comment = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Comment WHERE comment_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comment = new Comment(resultSet.getInt("comment_id"), resultSet.getString("comment_author"),
                        resultSet.getString("comment_text"), resultSet.getLong("comment_date_created"), resultSet.getInt("comment_news"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return comment;
    }

    @Override
    public boolean deleteComment(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Category where comment_id = ?");
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

    @Override
    public Comment updateComment(Comment comment, Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE Comment AS comm SET comm.comment_author = ?, comm.comment_text = ? where comm.comment_id = ?");
            preparedStatement.setString(1, comment.getComment_author());
            preparedStatement.setString(2, comment.getComment_text());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        comment.setComment_id(id);
        comment.setComment_news(comment.getComment_news());

        return comment;
    }
}
