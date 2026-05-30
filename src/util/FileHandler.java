package util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileHandler {
    private static final String LOG_FILE = "parking_log.txt";

    public static void logBooking(String userName, String vehicleNumber,
                                  int slotNumber, double fee, int hours) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            bw.write("=== BOOKING RECORD ===");
            bw.newLine();
            bw.write("Time       : " + timestamp);
            bw.newLine();
            bw.write("Name       : " + userName);
            bw.newLine();
            bw.write("Vehicle    : " + vehicleNumber);
            bw.newLine();
            bw.write("Slot       : " + slotNumber);
            bw.newLine();
            bw.write("Hours      : " + hours);
            bw.newLine();
            bw.write("Total Fee  : Rs " + String.format("%.2f", fee));
            bw.newLine();
            bw.write("======================");
            bw.newLine();
            bw.newLine();

        } catch (IOException e) {
            System.out.println("[WARNING] Could not write to log file: " + e.getMessage());
        }
    }

    public static void printAllLogs() {
        File file = new File(LOG_FILE);
        if (!file.exists()) {
            System.out.println("  No booking records found.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("  " + line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("  [ERROR] Log file not found.");
        } catch (IOException e) {
            System.out.println("  [ERROR] Could not read log file: " + e.getMessage());
        }
    }
}
