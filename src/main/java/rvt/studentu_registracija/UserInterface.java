package rvt.studentu_registracija;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Scanner;

import rvt.util.ConsoleColors;

public class UserInterface {
    HashMap<String, Student> students;
    FileHandler file;

    UserInterface() {
        students = new HashMap<>();
        file = new FileHandler();
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.run();
    }

    void run() {
        try {
            file.loadStudents(students);
        } catch (Exception e) {
            System.err.println("WARN: Failed to load students: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Choose an action (register|show|edit|remove|exit): ");
            String in = scanner.nextLine();
            if (in.equals("register")) {
                addStudent(scanner);
            } else if (in.equals("show")) {
                showData();
            } else if (in.equals("remove")) {
                System.out.print("Enter students personal code: ");
                String pers_code = scanner.nextLine();

                if (!students.containsKey(pers_code)) {
                    System.out.println("Student does not exists.");
                }

                students.remove(pers_code);
                try {
                    file.writeStudents(students);
                } catch (Exception e) {
                    System.err.println("WARN: Failed to write students to file: " + e.getMessage());
                }
            } else if (in.equals("edit")) {
                System.out.print("Enter students personal code: ");
                String pers_code = scanner.nextLine();

                if (!students.containsKey(pers_code)) {
                    System.out.println("Student does not exists.");
                }

                Student bk = students.get(pers_code);
                students.remove(pers_code);

                System.out.println("Enter new student info\n---------------------------------------------------------");
                if (!addStudent(scanner)) {
                    students.put(pers_code, bk);
                    continue;
                }

                try {
                    file.writeStudents(students);
                } catch (Exception e) {
                    System.err.println("WARN: Failed to write students to file: " + e.getMessage());
                }
            } else if (in.equals("exit")) {
                break;
            } else {
                System.out.println("Unknown command: " + in);
            }
        }

        scanner.close();
    }

    boolean addStudent(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        if (!Validator.validateNameSurname(name)) {
            System.out.println("Name should only contain letters and be atleast 3 letters long.");
            return false;
        }

        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        if (!Validator.validateNameSurname(surname)) {
            System.out.println("Surname should only contain letters and be atleast 3 letters long.");
            return false;
        }

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (!Validator.validateEmail(email)) {
            System.out.println("Email is not valid.");
            return false;
        }

        System.out.print("Enter personal code: ");
        String pers_code = scanner.nextLine();
        if (!Validator.validatePerscode(pers_code)) {
            System.out.println("Personal code is not valid.");
            return false;
        }

        System.out.print("Enter date (dd.MM.yyyy HH:mm): ");
        String dateStr = scanner.nextLine();
        try {
            LocalDateTime datetime = LocalDateTime.parse(dateStr,
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            Student student = new Student(name, surname, email, pers_code, datetime);
            students.put(pers_code, student);

            try {
                file.addStudent(student);
            } catch (Exception e) {
                System.err.println("WARN: failed to write student to file: " + e.getMessage());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Date is not valid.");
            return false;
        }

        return true;
    }

    void showData() {
        int[] maxwidths = { 4, 7, 5, 13, 17 };
        for (Student student : students.values()) {
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
        // String format = "| %-" + maxwidths[0] + "s | %-" + maxwidths[1] + "s | %-" +
        // maxwidths[2] + "| %-"
        // + maxwidths[3] + "s | %-" + "| %-" + maxwidths[4] + "s |%n";
        String format = "| %-" + maxwidths[0] + "s | %-" + maxwidths[1] + "s | %-" + maxwidths[2] + "s |"
                + ConsoleColors.GREEN.code + " %-"
                + maxwidths[3] + "s " + ConsoleColors.RESET.code + "| %-" + maxwidths[4]
                + "s |%n";

        System.out.println(separator);
        System.out.printf(format, "Name", "Surname", "Email", "Personal Code",
                "Registration Date");
        for (Student student : students.values()) {
            System.out.println(separator);
            System.out.printf(format, student.name, student.surname, student.email, student.personal_code,
                    student.reg_date);
        }
        System.out.println(separator);
    }

    String tableSeparator(int[] widths) {
        String result = "+";
        for (int i = 0; i < widths.length; i++) {
            for (int j = 0; j < widths[i] + 2; j++) {
                result += "-";
            }
            result += "+";
        }
        return result;
    }
}
