package org.example.models;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Hotel {
    private final String[][] levels = {
            {"1A", "1B", "1C", "1D", "1E"},
            {"2A", "2B", "2C", "2D", "2E"},
            {"3A", "3B", "3C", "3D", "3E"},
            {"4A", "4B", "4C", "4D", "4E"},
    };
    private final HashMap<String, Room> rooms = new HashMap<>();
    private final HashMap<Customer, Room> customerRmMap = new HashMap<>();
    private final PriorityQueue<Room> availableRooms = new PriorityQueue<>();

    public Hotel() {
        int idx = 0;
        for (int i = 0; i<levels.length; i++) {
            if (i % 2 == 1) { // even floors are from the back
                for (int j = levels[i].length-1; j>=0; j--) {
                    Room rm = new Room(levels[i][j], idx++);
                    rooms.put(levels[i][j], rm);
                    availableRooms.add(rm);
                }
            } else {
                // odd floors
                for (int j = 0; j<levels[i].length; j++) {
                    Room rm = new Room(levels[i][j], idx++);
                    rooms.put(levels[i][j], rm);
                    availableRooms.add(rm);
                }
            }
        }
    }

    public boolean hasAvailableRooms() {
        return !availableRooms.isEmpty();
    }

    /**
     * Requirement 1: A method for requesting for room assignment, which reply with the assigned room number upon success.
     * Returning a Room object instead of the room number.
     */
    public Room assignNearestAvailableRoom(Customer customer) {
        Room nearestRoom = availableRooms.poll();
        nearestRoom.setOccupied(customer);
        customerRmMap.put(customer, nearestRoom);
        return nearestRoom;
    }

    /**
     * Requirement 2: A method to check out of a room.
     * @param roomNum
     */
    public void checkoutByRoomNumber(String roomNum) {
        // TODO: Add validation and exception
        Room rm = rooms.get(roomNum);
        rm.checkout();
    }

    /**
     * Requirement 2: A method to check out of a room.
     * @param customer
     */
    public void checkoutByCustomer(Customer customer) {
        // TODO: Add validation and exception
        Room rm = customerRmMap.get(customer);
        rm.checkout();
    }

    /**
     * Requirement 3: A method to mark room cleaned
     * @param roomNum
     */
    public void cleanRoomCompleted(String roomNum) {
        // TODO: Add validation - room not vacant or in repair / room dont exist
        Room rm = rooms.get(roomNum);
        rm.cleanCompleted();
    }

    /**
     * Requirement 4: A method to mark room as room completed.
     * @param roomNum
     */
    public void repairRoomCompleted(String roomNum) {
        // TODO: Add validation - room.status not repaired / room dont exist
        Room rm = rooms.get(roomNum);
        rm.repairCompleted();
    }
}
