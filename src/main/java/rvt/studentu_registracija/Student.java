package rvt.studentu_registracija;

import java.time.LocalDateTime;

public class Student {
    String name;
    String surname;
    String email;
    String personal_code;
    LocalDateTime reg_date; // Registration date

    Student(String name, String surname, String email, String personal_code, LocalDateTime reg_date) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.personal_code = personal_code;
        this.reg_date = reg_date;
    }
}
