package org.example.models;

import org.example.exception.ErrorMessage;
import org.example.exception.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        String[][] levels = {
                {"1A", "1B", "1C", "1D", "1E"}
        };
        hotel = new Hotel(levels);
    }

    @Test
    void assignNearestAvailableRoom_Success() throws InvalidRequestException {
        Customer customer = hotel.registerCustomer("test");
        Room assignedRoom = hotel.assignNearestAvailableRoom(customer);

        assertNotNull(assignedRoom);
        assertEquals(customer, assignedRoom.getCustomer());
        assertEquals(Room.Status.OCCUPIED, assignedRoom.getStatus());
    }

    @Test
    void assignNearestAvailableRoom_CustomerNotFound() {
        Customer customer = new Customer("unknown");
        try {
            hotel.assignNearestAvailableRoom(customer);
            fail("Should fail");
        } catch (InvalidRequestException e) {
            assert e.getMessage().equals(ErrorMessage.Customer_NotFound.render(customer.getName()));
        }
    }

    @Test
    void assignNearestAvailableRoom_CustomerAlreadyCheckedIn() throws InvalidRequestException {
        Customer customer = hotel.registerCustomer("test");
        hotel.assignNearestAvailableRoom(customer);

        try {
            hotel.assignNearestAvailableRoom(customer);
            fail("Should fail");
        } catch (InvalidRequestException e) {
            assert e.getMessage().equals(ErrorMessage.Customer_AlreadyCheckedIn.render(customer.getName()));
        }
    }

    @Test
    void assignNearestAvailableRoom_NoRoomsAvailable() throws InvalidRequestException {
        String[][] levels = new String[1][0];
        hotel = new Hotel(levels);
        Customer customer = hotel.registerCustomer("test");
        Room room = hotel.assignNearestAvailableRoom(customer);
        assertNull(room);
    }

    @Test
    void checkoutByRoomNumber_Success() throws InvalidRequestException {
        Customer customer = hotel.registerCustomer("test");
        Room assignedRoom = hotel.assignNearestAvailableRoom(customer);

        hotel.checkoutByRoomNumber(assignedRoom.getRoomNumber());

        assertEquals(Room.Status.VACANT, assignedRoom.getStatus());
    }

    @Test
    void checkoutByRoomNumber_RoomNotOccupied() {
        Room room = hotel.getAvailableRooms().get(0);
        try {
            hotel.checkoutByRoomNumber(room.getRoomNumber());
            fail("Should fail");
        } catch (InvalidRequestException e) {
            assert e.getMessage().equals(ErrorMessage.Room_NotOccupied.render(room.getRoomNumber()));
        }
    }

    @Test
    void checkoutByCustomerName_Success() throws InvalidRequestException {
        Customer customer = hotel.registerCustomer("test");
        Room assignedRoom = hotel.assignNearestAvailableRoom(customer);

        hotel.checkoutByCustomerName("test");

        assertEquals(Room.Status.VACANT, assignedRoom.getStatus());
    }

    @Test
    void checkoutByCustomerName_CustomerNotRegistered() {
        Customer customer = hotel.registerCustomer("test");
        try {
            hotel.checkoutByCustomerName(customer.getName());
            fail("Should fail");
        } catch (InvalidRequestException e) {
            assert e.getMessage().equals(ErrorMessage.Customer_NotCheckedIn.render(customer.getName()));
        }
    }

    @Test
    void checkoutByCustomerName_CustomerNotCheckedIn() {
        Customer customer = new Customer("unknown");
        try {
            hotel.checkoutByCustomerName(customer.getName());
            fail("Should fail");
        } catch (InvalidRequestException e) {
            assert e.getMessage().equals(ErrorMessage.Customer_NotFound.render(customer.getName()));
        }
    }

    @Test
    void cleanRoomCompleted_Success() throws InvalidRequestException {
        Customer customer = hotel.registerCustomer("test");
        Room assignedRoom = hotel.assignNearestAvailableRoom(customer);

        hotel.checkoutByRoomNumber(assignedRoom.getRoomNumber());
        hotel.cleanRoomCompleted(assignedRoom.getRoomNumber());

        assertEquals(Room.Status.AVAILABLE, assignedRoom.getStatus());
    }

    @Test
    void cleanRoomCompleted_RoomNotVacant() {
        Room room = hotel.getAvailableRooms().get(0);
        try {
            hotel.cleanRoomCompleted(room.getRoomNumber());
            fail("Should fail");
        } catch (InvalidRequestException e) {
            assert e.getMessage().equals(ErrorMessage.Room_CannotBeCleaned.render(room.getRoomNumber()));
        }
    }

    @Test
    void repairRoomCompleted_Success() throws InvalidRequestException {
        Customer customer = hotel.registerCustomer("test");
        Room assignedRoom = hotel.assignNearestAvailableRoom(customer);

        hotel.checkoutByRoomNumber(assignedRoom.getRoomNumber());
        hotel.repairRoom(assignedRoom.getRoomNumber());
        hotel.repairRoomCompleted(assignedRoom.getRoomNumber());

        assertEquals(Room.Status.VACANT, assignedRoom.getStatus());
    }

    @Test
    void repairRoomCompleted_RoomNotUnderRepair() {
        Room room = hotel.getAvailableRooms().get(0);
        try {
            hotel.repairRoomCompleted(room.getRoomNumber());
            fail("Should fail");
        } catch (InvalidRequestException e) {
            assert e.getMessage().equals(ErrorMessage.Room_NotUnderRepair.render(room.getRoomNumber()));
        }
    }

    @Test
    void getAvailableRooms_Success() throws InvalidRequestException {
        Customer customer = hotel.registerCustomer("test");
        hotel.assignNearestAvailableRoom(customer);

        List<Room> availableRooms = hotel.getAvailableRooms();

        assertEquals(hotel.getAvailableRooms().size(), availableRooms.size());
        for (Room room : availableRooms) {
            assertEquals(Room.Status.AVAILABLE, room.getStatus());
        }
    }

    @Test
    void registerCustomer_Success() {
        Customer customer = hotel.registerCustomer("test");

        assertNotNull(customer);
    }

    @Test
    void registerCustomer_CustomerAlreadyExists() {
        Customer existingCustomer = hotel.registerCustomer("test");
        Customer newCustomer = hotel.registerCustomer("test");

        assertSame(existingCustomer, newCustomer);
    }

    @Test
    void repairRoom_Success() throws InvalidRequestException {
        Customer customer = hotel.registerCustomer("test");
        Room assignedRoom = hotel.assignNearestAvailableRoom(customer);
        hotel.checkoutByRoomNumber(assignedRoom.getRoomNumber());
        hotel.repairRoom(assignedRoom.getRoomNumber());

        assertEquals(Room.Status.REPAIR, assignedRoom.getStatus());
    }

    @Test
    void repairRoom_RoomNotVacant() {
        Customer customer = hotel.registerCustomer("test");
        Room assignedRoom = hotel.assignNearestAvailableRoom(customer);

        try {
            hotel.repairRoom(assignedRoom.getRoomNumber());
            fail("Should fail");
        } catch (InvalidRequestException e) {
            assert e.getMessage().equals(ErrorMessage.Room_CannotBeRepaired.render(assignedRoom.getRoomNumber()));
        }
    }

    @Test
    void findNearestAvailableRoom_Success() {
        Customer customer = hotel.registerCustomer("test");
        hotel.assignNearestAvailableRoom(customer);
        Room nextNearestRoom = hotel.findNearestAvailableRoom();
        assert(nextNearestRoom.getRoomNumber().equals(hotel.getAvailableRooms().get(0).getRoomNumber()));
    }

    @Test
    void findNearestAvailableRoom_NoRoomsAvailable() {
        String[][] levels = new String[1][0];
        hotel = new Hotel(levels);
        Customer customer = hotel.registerCustomer("test");
        Room emptyRoom = hotel.assignNearestAvailableRoom(customer);
        assertNull(emptyRoom);
    }
}
