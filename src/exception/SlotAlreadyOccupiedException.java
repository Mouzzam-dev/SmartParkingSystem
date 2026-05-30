package exception;

public class SlotAlreadyOccupiedException extends RuntimeException {
    private int slotNumber;

    public SlotAlreadyOccupiedException(int slotNumber) {
        super("Slot " + slotNumber + " is already occupied. Please choose another slot.");
        this.slotNumber = slotNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}
