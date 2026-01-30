package rvt;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TodoList {
   public static void main(String[] args) {
      UserInterface ui = new UserInterface(new TodoList(), new Scanner(System.in));
      ui.start();
   }

   private ArrayList<String> tasks;
   private final String filepath = "data/todo.csv";

   public TodoList() {
      tasks = new ArrayList<>();
      loadFromFile();
   }

   public void add(String task) {
      tasks.add(task);

      try {
         FileWriter fw = new FileWriter(new File(filepath), true);

         fw.append(getLastId() + 1 + "," + task);

         fw.close();
      } catch (Exception e) {
         System.err.println("Failed to write to file: " + e.getMessage());
      }
   }

   private void updateFile() {
      try {
         FileWriter fw = new FileWriter(new File(filepath));

         fw.append("id,task");
         for (int i = 0; i < tasks.size(); i++) {
            fw.append(i + "," + tasks.get(i));
         }

         fw.close();
      } catch (Exception e) {
         System.err.println("Failed to write to file: " + e.getMessage());
      }
   }

   public void remove(int number) {
      tasks.remove(number - 1);

      updateFile();
   }

   public void print() {
      for (int i = 0; i < tasks.size(); i++) {
         System.out.printf("%d: %s\n", i + 1, tasks.get(i));
      }
   }

   private void loadFromFile() {
      File file = new File(filepath);
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (Exception e) {
            System.err.println("Failed to create tasks file: " + e.getMessage());
            return;
         }
         return;
      }

      try {
         Scanner scanner = new Scanner(file);

         if (!scanner.hasNextLine()) {
            scanner.close();
            return;
         }
         scanner.nextLine(); // skip first line

         while (scanner.hasNextLine()) {
            tasks.add(scanner.nextLine().split(",")[1]);
         }

         scanner.close();
      } catch (Exception e) {
         System.err.println("Failed to open file: " + e.getMessage());
      }
   }

   private int getLastId() {
      return tasks.size();
   }

   private static final Pattern EVENT_PATTERN = Pattern.compile("^[a-zA-Z0-9 ]{3,}$");

   public boolean checkEventString(String value) {
      return EVENT_PATTERN.matcher(value).matches();
   }
}

class UserInterface {
   TodoList list;
   Scanner scanner;

   UserInterface(TodoList list, Scanner scanner) {
      this.list = list;
      this.scanner = scanner;
   }

   void start() {
      while (true) {
         System.out.print("Command: ");
         String command = scanner.nextLine();

         if (command.equals("stop")) {
            break;
         } else if (command.equals("add")) {
            System.out.print("To add: ");

            String task = scanner.nextLine();
            if (list.checkEventString(task)) {
               list.add(scanner.nextLine());
            } else {
               System.out.println("Task can only contain letters, digits and spaces!");
            }
         } else if (command.equals("list")) {
            list.print();
         } else if (command.equals("remove")) {
            System.out.print("Which one is removed? ");
            list.remove(Integer.valueOf(scanner.nextLine()));
         }
      }
   }
}