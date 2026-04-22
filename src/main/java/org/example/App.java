package org.example;

import java.sql.*;

public class App {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/internship";
        String user = "postgres";
        String password = "elisa010106";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // 1. Printoni te gjithe tabelat ne db tuaj
            // COURSE TABLE
            System.out.println("\nCOURSE");
            ResultSet courseRs = stmt.executeQuery("SELECT * FROM course");

            while (courseRs.next()) {
                System.out.println(
                        courseRs.getInt("id") + " | " +
                                courseRs.getString("course_name") + " | " +
                                courseRs.getInt("duration") + " | " +
                                courseRs.getString("programming_language")
                );
            }

            // STUDENT TABLE
            System.out.println("\nSTUDENT");
            ResultSet studentRs = stmt.executeQuery("SELECT * FROM student");

            while (studentRs.next()) {
                System.out.println(
                        studentRs.getInt("student_id") + " | " +
                                studentRs.getString("student_name") + " | " +
                                studentRs.getInt("points") + " | " +
                                studentRs.getInt("course_id")
                );
            }

            // ENROLLMENT TABLE
            System.out.println("\nENROLLMENT");
            ResultSet enrollRs = stmt.executeQuery("SELECT * FROM enrollment");

            while (enrollRs.next()) {
                System.out.println(
                        enrollRs.getInt("student_id") + " -> " +
                                enrollRs.getInt("course_id")
                );
            }

            // 2. Studentet qe kane me shume se 10 pike
            System.out.println("\nSTUDENTS WITH > 10 POINTS");

            ResultSet highPoints = stmt.executeQuery(
                    "SELECT * FROM student WHERE points > 10"
            );

            while (highPoints.next()) {
                System.out.println(
                        highPoints.getInt("student_id") + " | " +
                                highPoints.getString("student_name") + " | " +
                                highPoints.getInt("points")
                );
            }

            // 3. Shtoni nje student
            System.out.println("\nINSERT NEW STUDENT");

            stmt.executeUpdate(
                    "INSERT INTO student (student_name, email, birth_date, phone_number, points, course_id) " +
                            "VALUES ('DEV Student', 'dev@gmail.com', '2002-05-10', '0690000000', 100, 1)"
            );

            // e. Modifikoni piket e nje studenti
            System.out.println("\nUPDATE STUDENT POINTS");

            stmt.executeUpdate(
                    "UPDATE student SET points = 99 WHERE student_name = 'Elisa Isufi'"
            );

            // f. Fshini nje student
            System.out.println("\nDELETE STUDENT");

            stmt.executeUpdate(
                    "DELETE FROM student WHERE student_name = 'DEV Student'"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}