package com.example.backend.repositories.user;

import com.example.backend.entities.User;
import com.example.backend.repositories.MySqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository {
    @Override
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM User");
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_last_name"),
                        resultSet.getString("user_email"), resultSet.getString("user_type"),
                        resultSet.getBoolean("user_status"), resultSet.getString("user_password"));
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String[] generatedColumns = {"user_id"};
            preparedStatement = connection.prepareStatement("INSERT INTO User (user_name, user_last_name, user_email, user_type, user_status, user_password) VALUES (?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, user.getUser_name());
            preparedStatement.setString(2, user.getUser_last_name());
            preparedStatement.setString(3, user.getUser_email());
            preparedStatement.setString(4, user.getUser_type());
            preparedStatement.setBoolean(5, true);
            preparedStatement.setString(6, DigestUtils.sha256Hex(user.getUser_password()));
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                user.setUser_id(resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(preparedStatement != null) {
                this.closeStatement(preparedStatement);
            }
            if(resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User findUser(String email) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM User where user_email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("user_id");
                String firstName = resultSet.getString("user_name");
                String lastName = resultSet.getString("user_last_name");
                String password = resultSet.getString("user_password");
                boolean status = resultSet.getBoolean("user_status");
                String type = resultSet.getString("user_type");
                user = new User(id, firstName, lastName, email, type, status, password);
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

        return user;
    }

    @Override
    public void userStatus(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean status = false;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM User WHERE user_email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                status = resultSet.getBoolean("user_status");
            }
            preparedStatement = connection.prepareStatement("UPDATE User SET User.user_status = ? WHERE user_email = ?");
            preparedStatement.setBoolean(1, !status);
            preparedStatement.setString(2, email);
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
    }

    @Override
    public User updateUser(User user, String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            if (!(email.equals(user.getUser_email()))) {
                preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE user_email = ? ");
                preparedStatement.setString(1, user.getUser_email());
                resultSet = preparedStatement.executeQuery();
            }
            // TODO: Namestiti da se lepo azurira korisnik
            if (resultSet == null || !resultSet.next() || email.equals(user.getUser_email())) {
                preparedStatement = connection.prepareStatement("UPDATE User SET User.user_name = ?, User.user_last_name = ?" +
                        ", User.user_email = ?, User.user_type = ? WHERE user_email = ?");
                preparedStatement.setString(1, user.getUser_name());
                preparedStatement.setString(2, user.getUser_last_name());
                preparedStatement.setString(3, user.getUser_email());
                preparedStatement.setString(4, user.getUser_type());
                preparedStatement.setString(5, email);
                preparedStatement.executeUpdate();
            }
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

        return user;
    }

    @Override
    public void deleteUser(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM User where user_email = ?");
            preparedStatement.setString(1, email);
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
}
