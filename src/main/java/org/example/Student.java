package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Student {
    private Integer student_id;
    private String student_name;
    private String email;
    private java.time.LocalDate birth_date;
    private String phone_number;
    private Integer points;
    private Integer course_id;
}
