package com.example.backend.repositories.news;

import com.example.backend.entities.*;
import com.example.backend.repositories.MySqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.sql.*;
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
            for (int i = 1; i <= 11 && resultSetNews.next(); i++) {
                News news = new News(resultSetNews.getInt("news_id"), resultSetNews.getString("news_title"), resultSetNews.getString("news_text"),
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
            if(statement != null) {
                this.closeStatement(statement);
            }
            if(resultSetNews != null) {
                this.closeResultSet(resultSetNews);
            }
            if(resultSetUser != null) {
                this.closeResultSet(resultSetUser);
            }
            if(resultSetCategory != null) {
                this.closeResultSet(resultSetCategory);
            }
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public List<News> allNewsByVisits() {
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
            resultSetNews = statement.executeQuery("SELECT * FROM News ORDER BY news_views DESC");
            for (int i = 1; i <= 11 && resultSetNews.next(); i++) {
                News news = new News(resultSetNews.getInt("news_id"), resultSetNews.getString("news_title"), resultSetNews.getString("news_text"),
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
            if(statement != null) {
                this.closeStatement(statement);
            }
            if(resultSetNews != null) {
                this.closeResultSet(resultSetNews);
            }
            if(resultSetUser != null) {
                this.closeResultSet(resultSetUser);
            }
            if(resultSetCategory != null) {
                this.closeResultSet(resultSetCategory);
            }
            this.closeConnection(connection);
        }

        return newsList;
    }

    @Override
    public News addNews(News news) {
        // TODO: Implementirati da se nekako cuvaju i prosledjeni tagovi
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
    public boolean updateNews(News news, int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE News AS news SET news.news_title = ?, news.news_text = ?, " +
                    "news.news_author = ?, news.news_category = ?  where news.news_id = ?");
            preparedStatement.setString(1, news.getNews_title());
            preparedStatement.setString(2, news.getNews_text());
            preparedStatement.setInt(3, news.getNews_author().getUser_id());
            preparedStatement.setInt(4, news.getNews_category().getCategory_id());
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeConnection(connection);
        }
        return true;
    }

    @Override
    public News findNews(Integer id) {
        News news = null;
        User user;
        Category category;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetNews = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        ResultSet resultSetTags = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM News where news_id = ?");
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
            resultSetNews.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSetNews);
            if (resultSetUser != null) {
                this.closeResultSet(resultSetUser);
            }
            if (resultSetTags != null){
                this.closeResultSet(resultSetTags);
            }
            this.closeResultSet(resultSetCategory);
            this.closeConnection(connection);
        }
        return news;
    }

    @Override
    public void deleteNews(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM TagNews WHERE news_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM Comment where comment_news = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM News where news_id = ?");
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
    }

    @Override
    public List<News> allNewsByAuthor(Integer authorId) {
        List<News> newsList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetNews = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM News where news_author = ?");
            preparedStatement.setInt(1, authorId);
            resultSetNews = preparedStatement.executeQuery();
            for(int i = 1; i <= 10 && resultSetNews.next(); i++) {
                News news = new News(resultSetNews.getInt("news_id"), resultSetNews.getString("news_title"), resultSetNews.getString("news_text"),
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
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return newsList;
    }

    @Override
    public List<News> allNewsByCategory(Integer categoryId) {
        List<News> newsList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetNews = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM News where news_category = ?");
            preparedStatement.setInt(1, categoryId);
            resultSetNews = preparedStatement.executeQuery();
            for(int i = 1; i <= 10 && resultSetNews.next(); i++) {
                News news = new News(resultSetNews.getInt("news_id"), resultSetNews.getString("news_title"), resultSetNews.getString("news_text"),
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
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return newsList;
    }

    @Override
    public void updateViews(Integer newsId) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        int views = 0;
        ResultSet resultSetNews = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM News WHERE news_id = ?");
            preparedStatement.setInt(1, newsId);
            resultSetNews = preparedStatement.executeQuery();
            while (resultSetNews.next()) {
                views = resultSetNews.getInt("news_views");
            }
            views++;
            preparedStatement = connection.prepareStatement("UPDATE News SET news_views = ? WHERE news_id = ?");
            preparedStatement.setInt(1, views);
            preparedStatement.setInt(2, newsId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(statement != null) {
                this.closeStatement(statement);
            }
            if(resultSetNews != null) {
                this.closeResultSet(resultSetNews);
            }
            this.closeConnection(connection);
        }
    }

    @Override
    public List<News> allNewsByTag(Integer tagId) {
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
