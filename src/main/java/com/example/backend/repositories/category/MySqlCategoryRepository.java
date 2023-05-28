package com.example.backend.repositories.category;

import com.example.backend.entities.Category;
import com.example.backend.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {
    @Override
    public List<Category> allCategories() {
        List<Category> categoryList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Category");
            while (resultSet.next()) {
                Category category = new Category(resultSet.getInt("category_id"), resultSet.getString("category_name"),
                        resultSet.getString("category_description"));
                categoryList.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return categoryList;
    }

    @Override
    public Category addCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet initialResultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Category WHERE category_name = ?");
            preparedStatement.setString(1, category.getCategory_name());
            initialResultSet = preparedStatement.executeQuery();
            if (!initialResultSet.next()) {
                String[] generatedColumns = {"category_id"};
                preparedStatement = connection.prepareStatement("INSERT INTO Category (category_name, category_description) VALUES (?, ?)", generatedColumns);
                preparedStatement.setString(1, category.getCategory_name());
                preparedStatement.setString(2, category.getCategory_description());
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    category.setCategory_id(resultSet.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public Category updateCategory(int id, Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("UPDATE Category AS cat SET cat.category_name = ?, cat.category_description = ?  where cat.category_id = ?");
            preparedStatement.setString(1, category.getCategory_name());
            preparedStatement.setString(2, category.getCategory_description());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        category.setCategory_id(id);
        return category;
    }

    @Override
    public Category findCategory(String name) {
        Category category = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Category WHERE category_name = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                category = new Category(resultSet.getInt("category_id"), resultSet.getString("category_name"),
                        resultSet.getString("category_description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return category;
    }

    @Override
    public boolean deleteCategory(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Category where category_name = ?");
            preparedStatement.setString(1, name);
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
