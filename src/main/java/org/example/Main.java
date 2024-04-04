package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


//        String url = "jdbc:mysql://localhost:3306/gridDynamics";

        String url = "jdbc:mysql://localhost:3305/jdbcDemo";

        String tableSql = "CREATE TABLE IF NOT EXISTS employees"
                + "(emp_id int PRIMARY KEY AUTO_INCREMENT, name varchar(30),"
                + "position varchar(30), salary double)";

        try {
            Connection con = DriverManager.getConnection(url, "root", "password");
            Statement statement = con.createStatement();
//            viewTable(statement);
            updateTable(con);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void storeEmployeeDetail(Statement statement){
        String selectSql = "SELECT * FROM employees";
        try (ResultSet resultSet = statement.executeQuery(selectSql)) {
            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                Employee emp = new Employee();
                emp.setId(resultSet.getInt("emp_id"));
                emp.setName(resultSet.getString("name"));
                emp.setPosition(resultSet.getString("position"));
                emp.setSalary(resultSet.getDouble("salary"));
                employees.add(emp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateTable(Connection connection){
        String updatePositionSql = "UPDATE employees SET position=? WHERE emp_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(updatePositionSql)) {
            pstmt.setString(1, "lead developer");
            pstmt.setInt(2, 1);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected);

            pstmt.setString(2, "HR");
            pstmt.setInt(2, 1);
            rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void batchUpdateTable(Statement statement, Connection connection){
        try{
            statement = connection.createStatement();

            statement.addBatch("update people set firstname='John' where id=123");
            statement.addBatch("update people set firstname='Eric' where id=456");
            statement.addBatch("update people set firstname='May'  where id=789");

            int[] recordsAffected = statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void batchUpdateTablePreparedStatement(Statement statement, Connection connection){
        String sql = "update people set firstname=? , lastname=? where id=?";


        PreparedStatement preparedStatement = null;
        try{
            preparedStatement =
                    connection.prepareStatement(sql);

            preparedStatement.setString(1, "Gary");
            preparedStatement.setString(2, "Larson");
            preparedStatement.setLong  (3, 123);

            preparedStatement.addBatch();

            preparedStatement.setString(1, "Stan");
            preparedStatement.setString(2, "Lee");
            preparedStatement.setLong  (3, 456);

            preparedStatement.addBatch();

            int[] affectedRecords = preparedStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void createTable(Statement statement, String query){
        try {
            ResultSet rs=statement.executeQuery(query);

            while(rs.next())
                System.out.println(rs.getString(1));
//            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void viewTable(Statement statement){
        String selectSql = "SELECT * FROM employees";
        try (ResultSet resultSet = statement.executeQuery(selectSql)) {
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}