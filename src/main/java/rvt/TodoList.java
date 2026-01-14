package rvt;

import java.util.ArrayList;
import java.util.Scanner;

public class TodoList {
   public static void main(String[] args) {
      UserInterface ui = new UserInterface(new TodoList(), new Scanner(System.in));
      ui.start();
   }

   ArrayList<String> tasks;

   TodoList() {
      tasks = new ArrayList<>();
   }

   void add(String task) {
      tasks.add(task);
   }

   void remove(int number) {
      tasks.remove(number - 1);
   }

   void print() {
      for (int i = 0; i < tasks.size(); i++) {
         System.out.printf("%d: %s\n", i + 1, tasks.get(i));
      }
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
            list.add(scanner.nextLine());
         } else if (command.equals("list")) {
            list.print();
         } else if (command.equals("remove")) {
            System.out.print("Which one is removed? ");
            list.remove(Integer.valueOf(scanner.nextLine()));
         }
      }
   }
}