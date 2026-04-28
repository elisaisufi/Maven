package org.example;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/internship";
        String user = "postgres";
        String password = "elisa010106";

        DBConnection db = new DBConnection(url, user, password);

        // CREATE TABLES 
        System.out.println("\nCreating Tables: ");

        Map<String, String> studentColumns = new LinkedHashMap<>();
        studentColumns.put("student_id", "SERIAL PRIMARY KEY");
        studentColumns.put("student_name", "VARCHAR(100)");
        studentColumns.put("email", "VARCHAR(100)");
        studentColumns.put("birth_date", "DATE");
        studentColumns.put("phone_number", "VARCHAR(20)");
        studentColumns.put("points", "INTEGER");
        studentColumns.put("course_id", "INTEGER");

        db.createTable(studentColumns, "student");

        Map<String, String> courseColumns = new LinkedHashMap<>();
        courseColumns.put("id", "SERIAL PRIMARY KEY");
        courseColumns.put("course_name", "VARCHAR(100)");
        courseColumns.put("duration", "INTEGER");
        courseColumns.put("programming_language", "VARCHAR(50)");

        db.createTable(courseColumns, "course");

        // ADD STUDENT
        System.out.println("\n Adding Student: ");

        Student newStudent = new Student();
        newStudent.setStudent_name("Test Student");
        newStudent.setEmail("test@example.com");
        newStudent.setBirth_date(LocalDate.of(2000, 1, 1));
        newStudent.setPhone_number("0691234567");
        newStudent.setPoints(50);
        newStudent.setCourse_id(1);

        db.addStudent(newStudent);

        // GET STUDENT
        System.out.println("\n Getting Student: ");

        Student found = db.getStudentById(1);

        if (found != null) {
            System.out.println(found.getStudent_name() + " | " + found.getPoints());
        }

        // UPDATE STUDENT
        System.out.println("\n Updating Student: ");

        if (found != null) {
            found.setPoints(100);
            db.updateStudent(found);
        }

        // DELETE STUDENT
        System.out.println("\n Deleting Student: ");

        db.deleteStudent(1);

        System.out.println("\n=== DONE ===");
    }
}
