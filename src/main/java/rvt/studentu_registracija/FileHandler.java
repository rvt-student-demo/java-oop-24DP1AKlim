package rvt.studentu_registracija;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

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

    void loadStudents(HashMap<String, Student> students) throws Exception {
        File file = new File(filepath);
        file.createNewFile();

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                String[] fields = row.split(",");
                students.put(fields[3],
                        new Student(fields[0], fields[1], fields[2], fields[3], LocalDateTime.parse(fields[4])));
            }

            scanner.close();
        } catch (Exception e) {
            throw e;
        }
    }

    void writeStudents(HashMap<String, Student> students) throws Exception {
        try {
            FileWriter fw = new FileWriter(new File(filepath), false);

            for (Student student : students.values()) {

                fw.append(student.name + "," + student.surname + "," + student.email + "," + student.personal_code + ","
                        + student.reg_date);
            }

            fw.close();
        } catch (Exception e) {
            throw e;
        }
    }
}
