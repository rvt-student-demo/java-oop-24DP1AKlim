package rvt;

import java.io.File;
import java.util.Scanner;

public class PasutijumuVesture {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("data/orders.csv"));

            scanner.nextLine();

            double sum = 0;
            while (scanner.hasNextLine()) {
                String[] pas = scanner.nextLine().split(",");

                double price = Double.valueOf(pas[4]);
                int count = Integer.valueOf(pas[3]);

                // System.out.println("Pasutijums #" + pas[0] + ": " + pas[1] + " pasutija " +
                // count + " x " + pas[2]
                // + " (" + price + " EUR) -> Kopa: " + price * count + " EUR");
                System.out.printf("Pasutijums #%s: %s pasutija %s x %s (%.2f EUR) -> Kopa: %.2f EUR\n", pas[0], pas[1],
                        count,
                        pas[2], price, price * count);

                sum += price * count;
            }

            // System.out.println("Kopeja pasutijumu summa: " + sum + " EUR");
            System.out.printf("Kopeja pasutijumu summa: %.2f EUR\n", sum);

            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
