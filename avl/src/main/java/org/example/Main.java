package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AVL<Integer> avlTree = new AVL<>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("1. Insert an element");
            System.out.println("2. Search for an element");
            System.out.println("3. Print the tree in-order");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.print("Enter the element to insert: ");
                    int insertValue = scanner.nextInt();
                    avlTree.insert(insertValue);
                    System.out.println("Element inserted successfully.");
                    break;
                case 2:
                    System.out.print("Enter the element to search: ");
                    int searchValue = scanner.nextInt();
                    boolean found = avlTree.contains(searchValue);
                    if (found) {
                        System.out.printf("Found value: %s", searchValue);
                        System.out.println();
                    } else {
                        System.out.println("Element not found in the tree.");
                    }
                    break;
                case 3:
                    System.out.println("In-order traversal of the tree:");
                    avlTree.printInOrder(avlTree.root);
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
