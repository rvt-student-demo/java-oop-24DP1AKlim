package rvt.studentu_registracija;

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
                break;
            }
        }

        scanner.close();
    }

    void showData() {

    }
}
