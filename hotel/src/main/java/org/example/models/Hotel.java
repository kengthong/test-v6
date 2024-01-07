package org.example.models;

import org.example.exception.ErrorMessage;
import org.example.exception.InvalidRequestException;

import java.util.*;

/**
 * Assumptions:
 * 1. Assume no customer have the same name (else will need to identify by other properties which is not implemented)
 */
public class Hotel {
    private final HashMap<String, Room> rooms = new HashMap<>(); // simulate room db table with primary key name
    private final HashMap<String, Customer> customers = new HashMap<>(); // simulate customer db table with primary key name
    private final HashMap<Customer, Room> customerRmMap = new HashMap<>();
    private final PriorityQueue<Room> availableRooms = new PriorityQueue<>();

    public Hotel() {
        int idx = 0;
        String[][] levels = {
                {"1A", "1B", "1C", "1D", "1E"},
                {"2A", "2B", "2C", "2D", "2E"},
                {"3A", "3B", "3C", "3D", "3E"},
                {"4A", "4B", "4C", "4D", "4E"},
        };
        for (int i = 0; i< levels.length; i++) {
            if (i % 2 == 1) { // even floors are from the back
                for (int j = levels[i].length-1; j>=0; j--) {
                    Room rm = new Room(levels[i][j], idx++);
                    rooms.put(levels[i][j], rm);
                    availableRooms.add(rm);
                }
            } else {
                // odd floors
                for (int j = 0; j< levels[i].length; j++) {
                    Room rm = new Room(levels[i][j], idx++);
                    rooms.put(levels[i][j], rm);
                    availableRooms.add(rm);
                }
            }
        }
    }

    /**
     * Requirement 1: A method for requesting for room assignment, which reply with the assigned room number upon success.
     * Returning a Room object instead of the room number.
     */
    public Room assignNearestAvailableRoom(Customer customer) throws InvalidRequestException {
        ErrorMessage.ensure(customers.containsKey(customer.getName()), ErrorMessage.Customer_NotFound, customer.getName());
        ErrorMessage.ensure(!customerRmMap.containsKey(customer), ErrorMessage.Customer_AlreadyCheckedIn, customer.getName());

        Room nearestRoom = availableRooms.poll();
        nearestRoom.setOccupied(customer);
        customerRmMap.put(customer, nearestRoom);
        return nearestRoom;
    }

    /**
     * Requirement 2: A method to check out of a room.
     * @param roomNum The provided room number The provided room number
     */
    public void checkoutByRoomNumber(String roomNum) throws InvalidRequestException {
        Room rm = getAndValidateRoom(roomNum);
        // ensure room can be checked out
        ErrorMessage.ensure(rm.getStatus() == Room.Status.OCCUPIED, ErrorMessage.Room_NotOccupied, roomNum);
        Customer customer = rm.getCustomer();
        rm.checkout();
        customerRmMap.remove(customer);
        availableRooms.add(rm);
    }

    /**
     * Requirement 2: A method to check out of a room.
     * @param name The name of the customer
     */
    public void checkoutByCustomerName(String name) throws InvalidRequestException {
        Customer customer = customers.get(name);
        
        // Ensure customer has been registered
        ErrorMessage.ensure(customer != null, ErrorMessage.Customer_NotFound, name);
        // Ensure customer exists and is currently checkedIn
        ErrorMessage.ensure(customerRmMap.containsKey(customer), ErrorMessage.Customer_NotCheckedIn, name);

        Room rm = customerRmMap.get(customer);
        rm.checkout();
        customerRmMap.remove(customer);
    }

    /**
     * Requirement 3: A method to mark room cleaned
     * @param roomNum The provided room number
     */
    public void cleanRoomCompleted(String roomNum) throws InvalidRequestException {
        Room rm = getAndValidateRoom(roomNum);
        // Ensure room is currently vacant
        ErrorMessage.ensure(rm.getStatus() == Room.Status.VACANT, ErrorMessage.Room_CannotBeCleaned, roomNum);
        
        rm.cleanCompleted();
    }

    /**
     * Requirement 4: A method to mark room as room completed.
     * @param roomNum The provided room number
     */
    public void repairRoomCompleted(String roomNum) throws InvalidRequestException {
        Room rm = getAndValidateRoom(roomNum);
        // Ensure room is currently repairing
        ErrorMessage.ensure(rm.getStatus() == Room.Status.REPAIR, ErrorMessage.Room_NotUnderRepair, roomNum);

        rm.repairCompleted();
    }

    /**
     * Requirement 5: A method to get available rooms.
     * @return List of available rooms
     */
    public List<Room> getAvailableRooms() {
        List<Room> availRooms = new ArrayList<>(availableRooms);
        availRooms.sort(availableRooms.comparator());
        return availRooms;
    }

    /**
     * OTHER METHODS THAT CAN COME IN USEFUL ARE INCLUDED BELOW
     */
    
    public Customer registerCustomer(String name) {
        customers.putIfAbsent(name, new Customer(name));
        return customers.get(name);
    }

    private Room getAndValidateRoom(String roomNum) {
        // Ensure room exists
        ErrorMessage.ensure(rooms.containsKey(roomNum), ErrorMessage.Room_InvalidRoomNumber, roomNum);
        return rooms.get(roomNum);
    }
}
