package uz.developers.dao;

import uz.developers.configuration.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UserDao {
    private final Connection connection = DBConnection.getInstance();
    private static UserDao userDao;

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    public void save(Long userId, String firstname, String lastname, String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users (id, firstname, lastname, username) values (?, ?, ?, ?)");
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, firstname);
            preparedStatement.setString(3, lastname);
            preparedStatement.setString(4, username);
            preparedStatement.execute();
        } catch (SQLException ex) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("update users set firstname = ?, lastname = ?, username = ? where id = ?");
                preparedStatement.setString(1, firstname);
                preparedStatement.setString(2, lastname);
                preparedStatement.setString(3, username);
                preparedStatement.setLong(4, userId);
                preparedStatement.execute();
            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<HashMap<String, Object>> getAll() {
        List<HashMap<String, Object>> data = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id, firstname, lastname, username from users");
            while (resultSet.next()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", resultSet.getObject("id"));
                hashMap.put("firstname", resultSet.getObject("firstname"));
                hashMap.put("lastname", resultSet.getObject("lastname"));
                hashMap.put("username", resultSet.getObject("username"));
                data.add(hashMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
