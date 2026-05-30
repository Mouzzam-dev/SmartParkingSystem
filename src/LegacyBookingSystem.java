import java.io.*;
import java.util.Scanner;

public class LegacyBookingSystem {

    // BAD: public array — anyone can directly modify slots
    public boolean[] s = new boolean[10];
    public String[]  v = new String[10];
    public String[]  nm = new String[10];

    int totalS = 10;
    double r = 50;   // r = rate (what is r? not obvious)

    Scanner sc = new Scanner(System.in);

    // BAD: one giant method doing EVERYTHING
    public void book() {
        System.out.print("name: ");
        String n = sc.nextLine();      // BAD variable name

        System.out.print("vehicle: ");
        String vn = sc.nextLine();     // BAD variable name

        System.out.print("slot: ");
        int x = 0;                     // BAD: what is x??
        // BAD: no try-catch — crashes if user types a letter
        x = Integer.parseInt(sc.nextLine());

        System.out.print("hours: ");
        int h = Integer.parseInt(sc.nextLine()); // BAD: crashes

        // BAD: magic numbers hardcoded
        if (x < 1 || x > 10) {
            System.out.println("bad slot");
            return;
        }

        if (n == null || n.equals("")) {
            System.out.println("no name");
            return;
        }

        // BAD: checking availability inline
        if (s[x - 1] == true) {
            System.out.println("slot taken");
            return;
        }

        // BAD: temp variable — should be query method
        double total = h * r;

        // BAD: directly mutating public array
        s[x - 1]  = true;
        v[x - 1]  = vn;
        nm[x - 1] = n;

        // BAD: no try-catch — crashes if file cannot open
        FileWriter fw = null;
        try {
            fw = new FileWriter("log.txt", true);
            fw.write(n + "," + vn + "," + x + "," + total + "\n");
            fw.close();
        } catch (Exception e) {
            // BAD: swallowing exception silently
            e.printStackTrace();
        }

        // BAD: receipt mixed inside booking logic
        System.out.println("---receipt---");
        System.out.println("name: " + n);
        System.out.println("vehicle: " + vn);
        System.out.println("slot: " + x);
        System.out.println("fee: " + total);
        System.out.println("---end---");
    }

    // BAD: dead code — never called anywhere
    public boolean checkAvailability(int slotNum) {
        return !s[slotNum - 1];
    }

    // BAD: directly accesses public array
    public void show() {
        for (int i = 0; i < 10; i++) {
            System.out.println("slot " + (i+1) + ": " +
                    (s[i] == true ? nm[i] + " " + v[i] : "empty"));
        }
    }
}