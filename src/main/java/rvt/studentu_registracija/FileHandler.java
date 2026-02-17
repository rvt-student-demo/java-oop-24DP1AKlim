package rvt.studentu_registracija;

import java.io.File;
import java.io.FileWriter;

public class FileHandler {
    final String filepath = "./data/students.csv";

    FileHandler() {
    }

    void addStudent(Student student) throws Exception {
        try {
            FileWriter fw = new FileWriter(new File(filepath));

            fw.append(student.name + "," + student.surname + "," + student.email + "," + student.personal_code + ","
                    + student.reg_date);

            fw.close();
        } catch (Exception e) {
            throw e;
        }
    }
}
