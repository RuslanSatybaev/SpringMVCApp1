package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    public static final String URL = "jdbc:postgresql://localhost:5432/alishev_first_db";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "1234";
    private static int PEOPLE_COUNT;

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        Person person = null;
        try (PreparedStatement prepareStatement = connection.prepareStatement(
                "SELECT * FROM Person WHERE id=?")) {
            prepareStatement.setInt(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            resultSet.next();
            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return person;
    }

    public void save(Person person) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(
                "INSERT INTO Person VALUES (1, ?, ?, ?)")) {
            prepareStatement.setString(1, person.getName());
            prepareStatement.setInt(2, person.getAge());
            prepareStatement.setString(3, person.getEmail());
            prepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(
                "UPDATE Person SET name=?, age=?, email=? WHERE id=?")) {
            prepareStatement.setString(1, updatedPerson.getName());
            prepareStatement.setInt(2, updatedPerson.getAge());
            prepareStatement.setString(3, updatedPerson.getEmail());
            prepareStatement.setInt(4, id);
            prepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(
                "DELETE FROM Person WHERE id=?")) {
            prepareStatement.setInt(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
