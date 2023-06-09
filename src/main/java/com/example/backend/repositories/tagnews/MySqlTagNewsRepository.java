package com.example.backend.repositories.tagnews;

import com.example.backend.entities.Category;
import com.example.backend.entities.News;
import com.example.backend.entities.Tag;
import com.example.backend.entities.User;
import com.example.backend.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MySqlTagNewsRepository extends MySqlAbstractRepository implements TagNewsRepository {
    @Override
    public void addTagToNews(Integer news_id, Integer tag_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO TagNews (tag_id, news_id) VALUES (?, ?)");
            preparedStatement.setInt(1, tag_id);
            preparedStatement.setInt(2, news_id);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public void removeTagFromNews(Integer news_id, Integer tag_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM TagNews WHERE news_id = ? AND tag_id = ?");
            preparedStatement.setInt(1, news_id);
            preparedStatement.setInt(2, tag_id);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public List<News> findNewsWithTag(Integer tag_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        ResultSet resultSetNews = null;
        ResultSet resultSetTags = null;


        List<News> newsList = new ArrayList<>();
        List<Integer> newsIDS = new ArrayList<>();
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM TagNews WHERE tag_id = ?");
            preparedStatement.setInt(1, tag_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                newsIDS.add( resultSet.getInt("news_id"));
            }
            if (!newsIDS.isEmpty()){
                for (Integer id : newsIDS){
                    News news = null;
                    User user = null;
                    Category category = null;
                    preparedStatement = connection.prepareStatement("SELECT * FROM News WHERE news_id = ?");
                    preparedStatement.setInt(1, id);
                    resultSetNews = preparedStatement.executeQuery();
                    if (resultSetNews.next()) {
                        news = new News(resultSetNews.getInt("news_id"), resultSetNews.getString("news_title"), resultSetNews.getString("news_text"),
                                resultSetNews.getLong("news_date_created"), resultSetNews.getInt("news_views"));
                        preparedStatement = connection.prepareStatement("SELECT * FROM User where user_id = ?");
                        preparedStatement.setInt(1, resultSetNews.getInt("news_author"));
                        resultSetUser = preparedStatement.executeQuery();
                        if(resultSetUser.next()) {
                            user = new User(resultSetUser.getString("user_name"), resultSetUser.getString("user_last_name"),
                                    resultSetUser.getString("user_email"), resultSetUser.getString("user_type"),
                                    resultSetUser.getBoolean("user_status"));
                            synchronized (this) {
                                news.setNews_author(user);
                            }
                        }
                        preparedStatement = connection.prepareStatement("SELECT * FROM Category where category_id = ?");
                        preparedStatement.setInt(1, resultSetNews.getInt("news_category"));
                        resultSetCategory = preparedStatement.executeQuery();
                        if(resultSetCategory.next()) {
                            category = new Category(resultSetCategory.getString("category_name"), resultSetCategory.getString("category_description"));
                            synchronized (this) {
                                news.setNews_category(category);
                            }
                        }
                        preparedStatement = connection.prepareStatement("SELECT * FROM TagNews WHERE news_id = ?");
                        preparedStatement.setInt(1, news.getNews_id());
                        resultSetUser = preparedStatement.executeQuery();
                        List<Integer> tagids = new ArrayList<>();
                        while(resultSetUser.next()){
                            tagids.add(resultSetUser.getInt(1));
                        }
                        for (Integer tId : tagids){
                            preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE tag_id = ?");
                            preparedStatement.setInt(1, tId);
                            resultSetTags = preparedStatement.executeQuery();
                            if (resultSetTags.next()){
                                news.getTags().add( new Tag(resultSetTags.getInt(1), resultSetTags.getString("tag_keyword")));
                            }
                        }
                    }
                    newsList.add( news);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            if (resultSetNews != null) {
                this.closeResultSet(resultSetNews);
            }
            if (resultSetTags != null){
                this.closeResultSet(resultSetTags);
            }
            if (resultSetCategory != null){
                this.closeResultSet( resultSetCategory);
            }
            if (resultSetUser != null){
                this.closeResultSet(resultSetUser);
            }
            this.closeConnection(connection);
        }
        return newsList;
    }

    @Override
    public List<Tag> allTagsForNews(Integer newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetNew = null;

        List<Tag> tagList = new ArrayList<>();
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM TagNews WHERE news_id = ?");
            preparedStatement.setInt(1, newsId);
            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            while (resultSet.next()) {
                preparedStatement = connection.prepareStatement("SELECT * FROM Tag WHERE tag_id = ?");
                preparedStatement.setInt(1, resultSet.getInt("tag_id"));
                resultSetNew = preparedStatement.executeQuery();
                while (resultSetNew.next()) {
                    Tag tag = new Tag(resultSetNew.getInt("tag_id"), resultSetNew.getString("tag_keyword"));
                    tagList.add(tag);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeConnection(connection);
        }
        return tagList;
    }

    @Override
    public void update(Integer newsId) {

    }

    @Override
    public void delete(Integer newsId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM TagNews WHERE news_id = ?");
            preparedStatement.setInt(1, newsId);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeConnection(connection);
        }

    }
}
