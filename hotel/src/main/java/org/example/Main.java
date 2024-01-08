package org.example;

import org.example.exception.InvalidRequestException;
import org.example.models.Customer;
import org.example.models.Hotel;
import org.example.models.Room;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String[][] levels = {
                {"1A", "1B", "1C", "1D", "1E"},
                {"2A", "2B", "2C", "2D", "2E"},
                {"3A", "3B", "3C", "3D", "3E"},
                {"4A", "4B", "4C", "4D", "4E"},
        };
        Hotel hotel = new Hotel(levels);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Management CLI");
            System.out.println("1. Assign Nearest Available Room");
            System.out.println("2. Checkout by Room Number");
            System.out.println("3. Checkout by Customer Name");
            System.out.println("4. Mark Room as Cleaned");
            System.out.println("5. Mark Room as Repaired");
            System.out.println("6. Get Available Rooms");
            System.out.println("7. Find Nearest Available Room");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter customer name: ");
                        String customerName = scanner.nextLine();
                        Customer customer = hotel.registerCustomer(customerName);
                        Room assignedRoom = hotel.assignNearestAvailableRoom(customer);
                        if (assignedRoom != null) {
                            System.out.println("Assigned room: " + assignedRoom.getRoomNumber());
                        } else {
                            System.out.println("There are no rooms currently available.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter room number for checkout: ");
                        String roomNumberCheckout = scanner.nextLine();
                        hotel.checkoutByRoomNumber(roomNumberCheckout);
                        System.out.println("Room checked out successfully.");
                        break;
                    case 3:
                        System.out.print("Enter customer name for checkout: ");
                        String customerNameCheckout = scanner.nextLine();
                        hotel.checkoutByCustomerName(customerNameCheckout);
                        System.out.println("Room checked out successfully.");
                        break;
                    case 4:
                        System.out.print("Enter room number for cleaning completion: ");
                        String roomNumberCleaned = scanner.nextLine();
                        hotel.cleanRoomCompleted(roomNumberCleaned);
                        System.out.println("Room marked as cleaned.");
                        break;
                    case 5:
                        System.out.print("Enter room number for repair completion: ");
                        String roomNumberRepaired = scanner.nextLine();
                        hotel.repairRoomCompleted(roomNumberRepaired);
                        System.out.println("Room marked as repaired.");
                        break;
                    case 6:
                        System.out.println("Available Rooms:");
                        String rooms = hotel.getAvailableRooms().stream().map(Room::getRoomNumber)
                            .collect(Collectors.joining(", "));
                        System.out.println("[" + rooms + "]");
                        break;
                    case 7:
                        System.out.println("Find nearest available room:");
                        Room room = hotel.findNearestAvailableRoom();
                        if (room != null) {
                            System.out.println("The nearest available room is room " + room.getRoomNumber() + ".");
                        } else {
                            System.out.println("There are currently no rooms available.");
                        }
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InvalidRequestException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // consume the remaining input
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                scanner.nextLine(); // consume the remaining input
            }
        }
    }
}
