package rvt.studentu_registracija;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    ArrayList<Student> students;
    FileHandler file;

    UserInterface() {
        students = new ArrayList<>();
        file = new FileHandler();
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.run();
    }

    void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String in = scanner.nextLine();
            if (in.equals("register")) {
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                if (!Validator.validateNameSurname(name)) {
                    System.out.println("Name should only contain letters and be atleast 3 letters long.");
                    continue;
                }

                System.out.print("Enter surname: ");
                String surname = scanner.nextLine();
                if (!Validator.validateNameSurname(surname)) {
                    System.out.println("Surname should only contain letters and be atleast 3 letters long.");
                    continue;
                }

                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                if (!Validator.validateEmail(email)) {
                    System.out.println("Email is not valid.");
                    continue;
                }

                System.out.print("Enter personal code: ");
                String pers_code = scanner.nextLine();
                if (!Validator.validatePerscode(pers_code)) {
                    System.out.println("Personal code is not valid.");
                    continue;
                }

                System.out.print("Enter date (dd.MM.yyyy HH:mm): ");
                String dateStr = scanner.nextLine();
                try {
                    LocalDateTime datetime = LocalDateTime.parse(dateStr,
                            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                    Student student = new Student(name, surname, email, dateStr, datetime);
                    students.add(student);

                    try {
                        file.addStudent(student);
                    } catch (Exception e) {
                        System.err.println("WARN: failed to write student to file: " + e.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("Failed to add user: " + e.getMessage());
                }
            } else if (in.equals("show")) {
                showData();
            } else if (in.equals("remove")) {

            } else if (in.equals("edit")) {

            } else if (in.equals("exit")) {
                break;
            } else {
                System.out.println("Unknown command: " + in);
            }
        }

        scanner.close();
    }

    void showData() {
        int[] maxwidths = { 4, 7, 5, 13, 17 };
        for (Student student : students) {
            if (student.name.length() > maxwidths[0]) {
                maxwidths[0] = student.name.length();
            }
            if (student.surname.length() > maxwidths[1]) {
                maxwidths[1] = student.surname.length();
            }
            if (student.email.length() > maxwidths[2]) {
                maxwidths[2] = student.email.length();
            }
            if (student.personal_code.length() > maxwidths[3]) {
                maxwidths[3] = student.personal_code.length();
            }
            if (student.reg_date.toString().length() > maxwidths[4]) {
                maxwidths[4] = student.reg_date.toString().length();
            }
        }

        String separator = tableSeparator(maxwidths);
        String format = "| %-" + maxwidths[0] + "s | %-" + maxwidths[1] + "s | %-" + maxwidths[2] + "| %-"
                + maxwidths[3] + "s | %-" + "| %-" + maxwidths[4] + "s |%n";

        System.out.println(separator);
        System.out.printf(format, "Name", "Surname", "Email", "Personal Code",
                "Registration Date");
        for (Student student : students) {
            System.out.printf(format, student.name, student.surname, student.email, student.personal_code,
                    student.reg_date);
            System.out.println(separator);
        }
    }

    String tableSeparator(int[] widths) {
        String result = "+";
        for (int i = 0; i < widths.length; i++) {
            for (int j = 0; j < widths[i]; j++) {
                result += "-";
            }
            result += "+";
        }
        return result;
    }
}
