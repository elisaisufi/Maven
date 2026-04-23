package org.example;

import java.sql.*;
import java.util.Map;

// 1. Krijoni nje klase Connection qe merr te gjitha parametrat e lidhjes me databazen.
public class DBConnection {

    private final String url;
    private final String user;
    private final String password;

    public DBConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // 2. Krijoni nje metode connect e cila perdor variablat e klases dhe krijon Connection.
    public Connection connect() {
        try {
            return DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e) {
            System.out.println("Could not connect to database: " + e.getMessage());
            return null;
        }
    }

    /* 3. Krijoni nje metode qe merr ne input nje Map<String,String> koloneTip,
          nje String tableName dhe krijon tabele ne databaze */
    public void createTable(Map<String, String> columnType, String tableName) {

        if (columnType == null || columnType.isEmpty() || tableName == null) {
            return;
        }

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName;

        int count = 0;
        int size = columnType.size();

        for (String column : columnType.keySet()) {
            sql = sql + column + " " + columnType.get(column);
            count++;
        }

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
        catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    // 4. Krijoni nje metode qe merr ne input emrin e tabeles e fshin tabelen.
    public void deleteTable(String tableName) {

        if (tableName == null) return;

        String sql = "DROP TABLE IF EXISTS " + tableName;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        }
        catch (SQLException e) {
            System.out.println("Error deleting table: " + e.getMessage());
        }
    }

    // 5. Krijoni nje metode qe merr ne input te dhenat e studentit dhe i fut ne tabelen student.*
    public void addStudent(Student s) {

        if (s == null) return;
        String sql = "INSERT INTO student (student_name, email, birth_date, phone_number, points, course_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getStudent_name());
            ps.setString(2, s.getEmail());
            ps.setDate(3, Date.valueOf(s.getBirth_date()));
            ps.setString(4, s.getPhone_number());
            ps.setInt(5, s.getPoints());
            ps.setInt(6, s.getCourse_id());

            ps.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("Error inserting student: " + e.getMessage());
        }
    }

    // 6. Krijoni nje metode qe merr te dhenat e studentit nga input id.
    public Student getStudentById(int student_id) {

        String sql = "SELECT * FROM student WHERE student_id = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, student_id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Student s = new Student();
                    s.setStudent_id(rs.getInt("student_id"));
                    s.setStudent_name(rs.getString("student_name"));
                    s.setEmail(rs.getString("email"));
                    s.setBirth_date(rs.getDate("birth_date").toLocalDate());
                    s.setPhone_number(rs.getString("phone_number"));
                    s.setPoints(rs.getInt("points"));
                    s.setCourse_id(rs.getInt("course_id"));
                    return s;
                }
            }

        }
        catch (SQLException e) {
            System.out.println("Could not find student: " + e.getMessage());
        }

        return null;
    }

    // 7. Krijoni nje metode qe merr ne input id, te dhena studenti dhe modifikon te dhenat e studentit.
    public void updateStudent(Student s) {

        if (s == null || s.getStudent_id() == null) return;
        String sql = "UPDATE student SET student_name=?, email=?, birth_date=?, phone_number=?, points=?, course_id=? " +
                "WHERE student_id=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getStudent_name());
            ps.setString(2, s.getEmail());
            ps.setDate(3, Date.valueOf(s.getBirth_date()));
            ps.setString(4, s.getPhone_number());
            ps.setInt(5, s.getPoints());
            ps.setInt(6, s.getCourse_id());
            ps.setInt(7, s.getStudent_id());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not update student: " + e.getMessage());
        }
    }

    // 8. Krijoni nje metode qe merr ne input id dhe e fshin studentin.
    public void deleteStudent(Integer id) {

        if (id == null) return;
        String sql = "DELETE FROM student WHERE student_id=?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("Could not delete student: " + e.getMessage());
        }
    }
}
