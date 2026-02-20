package rvt.studentu_registracija;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import rvt.util.ConsoleColors;

public class UserInterface {
    HashMap<String, Student> students;
    FileHandler file;
    HashSet<String> emails;

    UserInterface() {
        students = new HashMap<>();
        emails = new HashSet<>();
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

                emails.remove(students.get(pers_code).email);
                students.remove(pers_code);
                try {
                    file.writeStudents(students);
                } catch (Exception e) {
                    System.err.println("WARN: Failed to write students to file: " + e.getMessage());
                }

                System.out.println(ConsoleColors.RED.code + "Student removed!" + ConsoleColors.RESET.code);
            } else if (in.equals("edit")) {
                System.out.print("Enter students personal code: ");
                String pers_code = scanner.nextLine();

                if (!students.containsKey(pers_code)) {
                    System.out.println("Student does not exists.");
                }

                Student bk = students.get(pers_code);
                students.remove(pers_code);
                emails.remove(bk.email);

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

        if (emails.contains(email)) {
            System.out.println("Email already exists.");
            return false;
        }

        System.out.print("Enter personal code: ");
        String pers_code = scanner.nextLine();
        if (!Validator.validatePerscode(pers_code)) {
            System.out.println("Personal code is not valid.");
            return false;
        }

        if (students.containsKey(pers_code)) {
            System.out.println("Personal code exists.");
            return false;
        }

        System.out.print("Enter date (dd.MM.yyyy HH:mm): ");
        String dateStr = scanner.nextLine();
        try {
            LocalDateTime datetime = LocalDateTime.parse(dateStr,
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            Student student = new Student(name, surname, email, pers_code, datetime);
            students.put(pers_code, student);
            emails.add(email);

            try {
                file.addStudent(student);
            } catch (Exception e) {
                System.err.println("WARN: failed to write student to file: " + e.getMessage());
            }
        } catch (DateTimeParseException e) {
            System.out.println("Date is not valid.");
            return false;
        }

        System.out.println(ConsoleColors.GREEN.code + "Student added!" + ConsoleColors.RESET.code);

        return true;
    }

    void showData() {
        enum Widths {
            PersonalCode,
            Name,
            Surname,
            Email,
            Date
        }

        int[] maxwidths = { 13, 4, 7, 5, 17 };
        for (Student student : students.values()) {
            if (student.name.length() > maxwidths[Widths.Name.ordinal()]) {
                maxwidths[Widths.Name.ordinal()] = student.name.length();
            }
            if (student.surname.length() > maxwidths[Widths.Surname.ordinal()]) {
                maxwidths[Widths.Surname.ordinal()] = student.surname.length();
            }
            if (student.email.length() > maxwidths[Widths.Email.ordinal()]) {
                maxwidths[Widths.Email.ordinal()] = student.email.length();
            }
            if (student.personal_code.length() > maxwidths[Widths.PersonalCode.ordinal()]) {
                maxwidths[Widths.PersonalCode.ordinal()] = student.personal_code.length();
            }
            if (student.reg_date.toString().length() > maxwidths[Widths.Date.ordinal()]) {
                maxwidths[Widths.Date.ordinal()] = student.reg_date.toString().length();
            }
        }

        String separator = tableSeparator(maxwidths);
        // String format = "| %-" + maxwidths[0] + "s | %-" + maxwidths[1] + "s | %-" +
        // maxwidths[2] + "| %-"
        // + maxwidths[3] + "s | %-" + "| %-" + maxwidths[4] + "s |%n";
        String format = "| %-" + maxwidths[Widths.PersonalCode.ordinal()] + "s | %-"
                + maxwidths[Widths.Name.ordinal()]
                + "s | %-" + maxwidths[Widths.Surname.ordinal()] + "s | %-"
                + maxwidths[Widths.Email.ordinal()] + "s | %-" + maxwidths[Widths.Date.ordinal()]
                + "s |%n";

        System.out.println(separator);
        System.out.printf(format, ConsoleColors.GREEN.code + "Personal Code",
                "Name",
                "Surname", "Email",
                "Registration Date" + ConsoleColors.RESET.code);
        for (Student student : students.values()) {
            System.out.println(separator);
            System.out.printf(format, student.personal_code,
                    student.name, student.surname, student.email,
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
