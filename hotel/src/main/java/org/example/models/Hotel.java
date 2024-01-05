package org.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Hotel {
    private final String[][] levels = {
            {"1A", "1B", "1C", "1D", "1E"},
            {"2A", "2B", "2C", "2D", "2E"},
            {"3A", "3B", "3C", "3D", "3E"},
            {"4A", "4B", "4C", "4D", "4E"},
    };
    private List<Room> orderedRooms = new ArrayList<>();
    private PriorityQueue<Room> availableRooms = new PriorityQueue<>();

    public Hotel() {
        int idx = 0;
        for (int i = 0; i<levels.length; i++) {
            if (i % 2 == 1) { // even floors are from the back
                for (int j = levels[i].length-1; j>=0; j--) {
                    Room rm = new Room(levels[i][j], idx++);
                    orderedRooms.add(rm);
                    availableRooms.add(rm);
                }
            } else {
                // odd floors
                for (int j = 0; j<levels[i].length; j++) {
                    Room rm = new Room(levels[i][j], idx++);
                    orderedRooms.add(rm);
                    availableRooms.add(rm);
                }
            }
        }
    }
}