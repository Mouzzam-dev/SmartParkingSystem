import exception.InvalidInputException;
import exception.SlotAlreadyOccupiedException;
import model.*;
import service.BookingSystem;
import util.ConfigLoader;
import util.FileHandler;

import java.util.Scanner;

/**
 * Smart Parking Management System
 * By: Mouzzam Ali Khan (025) & Javeria Zia (026)
 * Course: Software Construction & Development
 * Sir: Seemab Shoukat
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ConfigLoader config = new ConfigLoader();
    private static ParkingLot parkingLot = new ParkingLot(config.getTotalSlots());
    private static BookingSystem bookingSystem = new BookingSystem(parkingLot);

    public static void main(String[] args) {
        printBanner();
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readIntSafely("Enter choice: ");

            switch (choice) {
                case 1: handleDriverBooking();   break;
                case 2: handleAdminBooking();     break;
                case 3: bookingSystem.displayAllSlots(); break;
                case 4: handleReleaseSlot();      break;
                case 5: viewBookingHistory();     break;
                case 6:
                    System.out.println("\n  Thank you for using Smart Parking System. Goodbye!\n");
                    running = false;
                    break;
                default:
                    System.out.println("  [!] Invalid option. Please choose 1-6.");
            }
        }
        scanner.close();
    }

    // ─── Banner ────────────────────────────────────────────────────────────────

    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║     SMART PARKING MANAGEMENT SYSTEM v2.0    ║");
        System.out.println("  ║   Mouzzam Ali Khan 025 | Javeria Zia 026    ║");
        System.out.println("  ║        Software Construction & Dev           ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  Total Slots: " + config.getTotalSlots()
                + "   |   Rate: Rs " + config.getRatePerHour() + "/hour");
        System.out.println();
    }

    // ─── Main Menu ─────────────────────────────────────────────────────────────

    private static void printMainMenu() {
        System.out.println("  ┌──────────────────────────────┐");
        System.out.println("  │          MAIN MENU           │");
        System.out.println("  ├──────────────────────────────┤");
        System.out.println("  │  1. Book Slot (Driver)       │");
        System.out.println("  │  2. Book Slot (Admin)        │");
        System.out.println("  │  3. View All Slots           │");
        System.out.println("  │  4. Release a Slot           │");
        System.out.println("  │  5. View Booking History     │");
        System.out.println("  │  6. Exit                     │");
        System.out.println("  └──────────────────────────────┘");
    }

    // ─── Driver Booking ────────────────────────────────────────────────────────

    private static void handleDriverBooking() {
        System.out.println("\n  --- DRIVER BOOKING ---");
        bookingSystem.displayAllSlots();

        try {
            String name          = readStringSafely("  Enter your name          : ");
            String vehicleNumber = readStringSafely("  Enter your vehicle number: ");
            int slotNumber       = readIntSafely(   "  Enter slot number to book: ");
            int parkingDurationInHours = readIntSafely("  Enter duration (hours)   : ");

            Driver driver = new Driver(name, "D" + System.currentTimeMillis(), vehicleNumber);
            bookingSystem.bookSlot(slotNumber, driver, parkingDurationInHours);

        } catch (SlotAlreadyOccupiedException e) {
            System.out.println("\n  [ERROR] " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("\n  [ERROR] Invalid input — " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n  [ERROR] Something went wrong: " + e.getMessage());
        }
    }

    // ─── Admin Booking ─────────────────────────────────────────────────────────

    private static void handleAdminBooking() {
        System.out.println("\n  --- ADMIN BOOKING (30% Corporate Discount) ---");

        try {
            String password = readStringSafely("  Enter admin password: ");

            if (!password.equals(config.getAdminPassword())) {
                System.out.println("  [!] Incorrect admin password.");
                return;
            }

            bookingSystem.displayAllSlots();

            String name          = readStringSafely("  Enter admin name          : ");
            String vehicleNumber = readStringSafely("  Enter vehicle number       : ");
            int slotNumber       = readIntSafely(   "  Enter slot number to book  : ");
            int parkingDurationInHours = readIntSafely("  Enter duration (hours)    : ");

            Admin admin = new Admin(name, "A" + System.currentTimeMillis(), vehicleNumber);
            bookingSystem.bookSlot(slotNumber, admin, parkingDurationInHours);

        } catch (SlotAlreadyOccupiedException e) {
            System.out.println("\n  [ERROR] " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("\n  [ERROR] Invalid input — " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n  [ERROR] Something went wrong: " + e.getMessage());
        }
    }

    // ─── Release Slot ──────────────────────────────────────────────────────────

    private static void handleReleaseSlot() {
        System.out.println("\n  --- RELEASE SLOT ---");
        bookingSystem.displayAllSlots();
        try {
            int slotNumber = readIntSafely("  Enter slot number to release: ");
            bookingSystem.releaseSlot(slotNumber);
        } catch (InvalidInputException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ─── View History ──────────────────────────────────────────────────────────

    private static void viewBookingHistory() {
        System.out.println("\n  --- BOOKING HISTORY (from parking_log.txt) ---");
        FileHandler.printAllLogs();
    }

    // ─── Safe Input Helpers ────────────────────────────────────────────────────

    private static int readIntSafely(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a valid number.");
            }
        }
    }

    private static String readStringSafely(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new InvalidInputException("Input cannot be blank.");
        }
        return input;
    }
}
