package service;

import exception.InvalidInputException;
import exception.SlotAlreadyOccupiedException;
import model.*;
import util.FileHandler;

/**
 * BookingSystem — REFACTORED version.
 *
 * Category A refactorings applied here:
 *   1. Extract Method  : isValidRequest(), displayReceipt(), finalizeBooking()
 *   2. Rename Variable : parkingDurationInHours (was: int x or int h)
 *   3. Replace Temp with Query : totalFee() replaces local double temp variable
 *
 * Category B refactorings applied here:
 *   4. Inline Method   : checkAvailability() inlined into bookSlot()
 *   5. Move Method     : displayReceipt() moved here from Main (it belongs to booking)
 *   6. Encapsulate Collection : slots managed through ParkingLot (not raw array)
 */
public class BookingSystem {
    private final ParkingLot parkingLot;

    public BookingSystem(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    // -------------------------------------------------------
    // CATEGORY A — Extract Method: isValidRequest()
    // BEFORE: validation was inline inside bookSlot() — messy
    // AFTER : clean, reusable, readable method
    // -------------------------------------------------------
    private boolean isValidRequest(int slotNumber, String name,
                                   String vehicleNumber, int parkingDurationInHours) {
        if (slotNumber < 1 || slotNumber > parkingLot.getTotalSlots()) {
            throw new InvalidInputException(
                    "Slot number must be between 1 and " + parkingLot.getTotalSlots());
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty.");
        }
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            throw new InvalidInputException("Vehicle number cannot be empty.");
        }
        if (parkingDurationInHours < 1) {
            throw new InvalidInputException("Duration must be at least 1 hour.");
        }
        return true;
    }

    // -------------------------------------------------------
    // CATEGORY A — Replace Temp with Query: totalFee()
    // BEFORE: double total = hours * rate; (temp variable inline)
    // AFTER : query method — called wherever fee is needed
    // -------------------------------------------------------
    public double totalFee(User user, int parkingDurationInHours) {
        return user.calculateParkingFee(parkingDurationInHours);
    }

    // -------------------------------------------------------
    // CATEGORY A — Extract Method: displayReceipt()
    // CATEGORY B — Move Method: receipt logic moved FROM Main TO here
    // -------------------------------------------------------
    public void displayReceipt(User user, int slotNumber, int parkingDurationInHours) {
        double fee = totalFee(user, parkingDurationInHours);
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║       SMART PARKING RECEIPT          ║");
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.printf ("  ║  Name     : %-24s║%n", user.getName());
        System.out.printf ("  ║  Vehicle  : %-24s║%n", user.getVehicleNumber());
        System.out.printf ("  ║  Slot No  : %-24s║%n", slotNumber);
        System.out.printf ("  ║  Duration : %-20s hrs ║%n", parkingDurationInHours);
        System.out.printf ("  ║  Total Fee: Rs %-21s║%n", String.format("%.2f", fee));
        System.out.println("  ╚══════════════════════════════════════╝");
        System.out.println();
    }

    // -------------------------------------------------------
    // CATEGORY A — Extract Method: finalizeBooking()
    // Groups slot marking + file logging into one clean step
    // -------------------------------------------------------
    private void finalizeBooking(int slotNumber, User user, int parkingDurationInHours) {
        double fee = totalFee(user, parkingDurationInHours);
        parkingLot.bookSlot(slotNumber, user.getVehicleNumber(), user.getName());
        FileHandler.logBooking(user.getName(), user.getVehicleNumber(),
                               slotNumber, fee, parkingDurationInHours);
    }

    // -------------------------------------------------------
    // Main public booking method — clean and readable
    // -------------------------------------------------------
    public void bookSlot(int slotNumber, User user, int parkingDurationInHours) {
        isValidRequest(slotNumber, user.getName(),
                       user.getVehicleNumber(), parkingDurationInHours);

        if (!parkingLot.isSlotAvailable(slotNumber)) {
            throw new SlotAlreadyOccupiedException(slotNumber);
        }

        finalizeBooking(slotNumber, user, parkingDurationInHours);
        displayReceipt(user, slotNumber, parkingDurationInHours);
        System.out.println("  ✔ Booking confirmed for Slot " + slotNumber + "!");
    }

    public void releaseSlot(int slotNumber) {
        if (slotNumber < 1 || slotNumber > parkingLot.getTotalSlots()) {
            throw new InvalidInputException("Invalid slot number: " + slotNumber);
        }
        if (!parkingLot.getSlot(slotNumber).isOccupied()) {
            System.out.println("  Slot " + slotNumber + " is already empty.");
            return;
        }
        parkingLot.releaseSlot(slotNumber);
        System.out.println("  ✔ Slot " + slotNumber + " has been released.");
    }

    public void displayAllSlots() {
        System.out.println();
        System.out.println("  ┌─────────────────────────────────────────┐");
        System.out.println("  │          PARKING LOT STATUS             │");
        System.out.println("  ├─────────────────────────────────────────┤");
        for (model.ParkingSlot slot : parkingLot.getSlots()) {
            System.out.println("  │  " + slot);
        }
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.printf ("  │  Available: %-5d  Occupied: %-10d│%n",
                parkingLot.countAvailableSlots(),
                parkingLot.getTotalSlots() - parkingLot.countAvailableSlots());
        System.out.println("  └─────────────────────────────────────────┘");
        System.out.println();
    }
}
