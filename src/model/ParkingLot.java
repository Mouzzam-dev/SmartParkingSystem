package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ParkingLot encapsulates the collection of ParkingSlot objects.
 * REFACTORING (Category B - Encapsulate Collection):
 *   BEFORE: BookingSystem had a public ParkingSlot[] slots array
 *           that any class could directly modify.
 *   AFTER:  ParkingLot controls all access. External code gets
 *           an unmodifiable view and can only mutate through
 *           controlled methods (bookSlot, releaseSlot).
 */
public class ParkingLot {
    private final List<ParkingSlot> slots;
    private final int totalSlots;

    public ParkingLot(int totalSlots) {
        this.totalSlots = totalSlots;
        this.slots = new ArrayList<>();
        for (int i = 1; i <= totalSlots; i++) {
            slots.add(new ParkingSlot(i));
        }
    }

    /** Returns an unmodifiable view — callers cannot mutate the list */
    public List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    public int getTotalSlots()     { return totalSlots; }

    public int countAvailableSlots() {
        int count = 0;
        for (ParkingSlot slot : slots) {
            if (!slot.isOccupied()) count++;
        }
        return count;
    }

    public ParkingSlot getSlot(int slotNumber) {
        for (ParkingSlot slot : slots) {
            if (slot.getSlotNumber() == slotNumber) return slot;
        }
        return null;
    }

    public boolean isSlotAvailable(int slotNumber) {
        ParkingSlot slot = getSlot(slotNumber);
        return slot != null && !slot.isOccupied();
    }

    public void bookSlot(int slotNumber, String vehicleNumber, String userName) {
        ParkingSlot slot = getSlot(slotNumber);
        if (slot != null) {
            slot.occupy(vehicleNumber, userName);
        }
    }

    public void releaseSlot(int slotNumber) {
        ParkingSlot slot = getSlot(slotNumber);
        if (slot != null) {
            slot.vacate();
        }
    }
}
