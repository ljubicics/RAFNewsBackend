package com.example.backend.repositories.news;

import com.example.backend.entities.*;
import com.example.backend.repositories.MySqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlNewsRepository extends MySqlAbstractRepository implements NewsRepository{

    @Override
    public List<News> allNews() {
        List<News> newsList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetNews = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSetNews = statement.executeQuery("SELECT * FROM News ORDER BY news_date_created DESC");
            for (int i = 0; i < 10 && resultSetNews.next(); i++) {
                News news = new News(resultSetNews.getString("news_title"), resultSetNews.getString("news_text"),
                        resultSetNews.getLong("news_date_created"), resultSetNews.getInt("news_views"));
                preparedStatement = connection.prepareStatement("SELECT * FROM User WHERE user_id = ?");
                preparedStatement.setInt(1, resultSetNews.getInt("news_author"));
                resultSetUser = preparedStatement.executeQuery();
                while (resultSetUser.next()) {
                    User user = new User(resultSetUser.getString("user_name"), resultSetUser.getString("user_last_name"),
                            resultSetUser.getString("user_email"), resultSetUser.getString("user_type"),
                            resultSetUser.getBoolean("user_status"));
                    synchronized (this) {
                        news.setNews_author(user);
                    }
                }
                preparedStatement = connection.prepareStatement("SELECT * FROM Category WHERE category_id = ?");
                preparedStatement.setString(1, resultSetNews.getString("news_category"));
                resultSetCategory = preparedStatement.executeQuery();
                while (resultSetCategory.next()) {
                    Category category = new Category(resultSetCategory.getString("category_name"), resultSetCategory.getString("category_description"));
                    synchronized (this) {
                        news.setNews_category(category);
                    }
                }
                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSetNews);
            this.closeResultSet(resultSetUser);
            this.closeResultSet(resultSetCategory);
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public List<News> allNewsByVisits() {
        return null;
    }

    @Override
    public News addNews(News news) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"news_id"};
            preparedStatement = connection.prepareStatement("INSERT INTO News (news_title, news_text, news_date_created, news_views, news_author, news_category) VALUES (?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, news.getNews_title());
            preparedStatement.setString(2, news.getNews_text());
            preparedStatement.setLong(3, new Date().getTime());
            preparedStatement.setInt(4, 0);
            // Kako prosledjivati usera kada se pravi nova vest da li kao User ili kao int user_id pa onda svaki put zvati bazu???
            preparedStatement.setInt(5, news.getNews_author().getUser_id());
            preparedStatement.setInt(6, news.getNews_category().getCategory_id());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                news.setNews_id(resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public News updateNews(News news) {
        return null;
    }

    @Override
    public News findNews(Integer id) {
        return null;
    }

    @Override
    public void deleteNews(Integer id) {

    }

    @Override
    public List<News> allByAuthor(Integer authorId) {
        return null;
    }

    @Override
    public List<News> allByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<News> allByTag(Integer tagId) {
        return null;
    }

    @Override
    public List<Tag> allTagByNews(Integer newsId) {
        return null;
    }

    @Override
    public List<Comment> allCommentsByNews(Integer newsId) {
        return null;
    }
}
