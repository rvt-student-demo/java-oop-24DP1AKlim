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
                } catch (Exception e) {

                }
            } else if (in.equals("show")) {

            }
        }

        scanner.close();
    }

    void showData() {

    }
}
