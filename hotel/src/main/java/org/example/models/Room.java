package org.example.models;

public class Room implements Comparable<Room> {
    public enum Status {
        AVAILABLE, OCCUPIED, VACANT, REPAIR
    }

    private String name;
    private Status status;
    private int index;
    private Customer currCustomer;

    public Room(String name, int idx) {
        this.name = name;
        this.index = idx;
    }

    public Status getStatus() {
        return this.status;
    }

    public int getIndex() { return this.index; }

    public void setOccupied(Customer customer) {
        this.status = Status.OCCUPIED;
        this.currCustomer = customer;
    }

    public void checkout() {
        this.status = Status.VACANT;
        this.currCustomer = null;
    }

    public void cleanCompleted() {
        this.status = Status.AVAILABLE;
    }

    public void repairCompleted() {
        this.status = Status.VACANT;
    }

    @Override
    public int compareTo(Room other) {
        // Comparing based on priority (ascending order)
        return Integer.compare(this.index, other.getIndex());
    }
}
