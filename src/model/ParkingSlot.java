package model;

public class ParkingSlot {
    private int slotNumber;
    private boolean isOccupied;
    private String occupiedBy;   // vehicle number
    private String occupiedByName; // user name
    private long checkInTime;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.isOccupied = false;
        this.occupiedBy = null;
        this.occupiedByName = null;
    }

    public int getSlotNumber()     { return slotNumber; }
    public boolean isOccupied()    { return isOccupied; }
    public String getOccupiedBy()  { return occupiedBy; }
    public String getOccupiedByName() { return occupiedByName; }
    public long getCheckInTime()   { return checkInTime; }

    public void occupy(String vehicleNumber, String userName) {
        this.isOccupied    = true;
        this.occupiedBy    = vehicleNumber;
        this.occupiedByName = userName;
        this.checkInTime   = System.currentTimeMillis();
    }

    public void vacate() {
        this.isOccupied    = false;
        this.occupiedBy    = null;
        this.occupiedByName = null;
        this.checkInTime   = 0;
    }

    @Override
    public String toString() {
        if (isOccupied) {
            return "Slot " + slotNumber + " [OCCUPIED] - " + occupiedByName + " (" + occupiedBy + ")";
        }
        return "Slot " + slotNumber + " [AVAILABLE]";
    }
}
