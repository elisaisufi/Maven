package org.example;

import java.sql.*;
import org.apache.commons.lang3.StringUtils;

public class App {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/internship";
        String user = "postgres";
        String password = "elisa010106";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // COURSE
            System.out.println("\nCOURSE");
            ResultSet courseRs = stmt.executeQuery("SELECT * FROM course");

            while (courseRs.next()) {

                String name = StringUtils.defaultIfBlank(
                        courseRs.getString("course_name"), "N/A"
                );

                String language = StringUtils.upperCase(
                        StringUtils.defaultIfBlank(
                                courseRs.getString("programming_language"), "unknown"
                        )
                );

                String row = StringUtils.joinWith(" | ",
                        courseRs.getInt("id"),
                        name,
                        courseRs.getInt("duration"),
                        language
                );

                System.out.println(row);
            }

            // STUDENT
            System.out.println("\nSTUDENT");
            ResultSet studentRs = stmt.executeQuery("SELECT * FROM student");

            while (studentRs.next()) {

                String name = StringUtils.capitalize(
                        StringUtils.lowerCase(
                                StringUtils.defaultString(studentRs.getString("student_name"))
                        )
                );

                String row = StringUtils.joinWith(" | ",
                        studentRs.getInt("student_id"),
                        name,
                        studentRs.getInt("points"),
                        studentRs.getInt("course_id")
                );

                System.out.println(row);
            }

            // ENROLLMENT
            System.out.println("\nENROLLMENT");
            ResultSet enrollRs = stmt.executeQuery("SELECT * FROM enrollment");

            while (enrollRs.next()) {

                String row = StringUtils.joinWith(" -> ",
                        enrollRs.getInt("student_id"),
                        enrollRs.getInt("course_id")
                );

                System.out.println(row);
            }

            // 1. Beni query studentet qe kane me shume se 10 pike
            System.out.println("\nSTUDENTS WITH > 10 POINTS");

            ResultSet highPoints = stmt.executeQuery(
                    "SELECT * FROM student WHERE points > 10"
            );

            while (highPoints.next()) {

                String name = StringUtils.capitalize(
                        StringUtils.lowerCase(
                                StringUtils.defaultString(highPoints.getString("student_name"))
                        )
                );

                String row = StringUtils.joinWith(" | ",
                        highPoints.getInt("student_id"),
                        name,
                        highPoints.getInt("points")
                );

                System.out.println(row);
            }

            // 2. Shtoni nje student
            System.out.println("\nINSERT NEW STUDENT");

            String newName = StringUtils.capitalize("dev student");

            stmt.executeUpdate(
                    "INSERT INTO student (student_name, email, birth_date, phone_number, points, course_id) " +
                            "VALUES ('" + newName + "', 'dev@gmail.com', '2002-05-10', '0690000000', 100, 1)"
            );

            // 3. Modifikoni piket e nje studenti
            System.out.println("\nUPDATE STUDENT POINTS");

            String updateName = StringUtils.capitalize("elisa isufi");

            stmt.executeUpdate(
                    "UPDATE student SET points = 99 WHERE student_name = '" + updateName + "'"
            );

            // 4. Fshini nje student
            System.out.println("\nDELETE STUDENT");

            stmt.executeUpdate(
                    "DELETE FROM student WHERE student_name = '" + newName + "'"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
