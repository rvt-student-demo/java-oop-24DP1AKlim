package rvt;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("data/data.csv"));

            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();

                String[] data = row.split(",");
                System.out.println(Arrays.toString(data));
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}