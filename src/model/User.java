package model;

public class User {
    private String name;
    private String userId;
    private String vehicleNumber;

    public User(String name, String userId, String vehicleNumber) {
        this.name = name;
        this.userId = userId;
        this.vehicleNumber = vehicleNumber;
    }

    public String getName()          { return name; }
    public String getUserId()        { return userId; }
    public String getVehicleNumber() { return vehicleNumber; }

    public double calculateParkingFee(int parkingDurationInHours) {
        return parkingDurationInHours * 50.0; // Base rate: Rs 50/hour
    }

    @Override
    public String toString() {
        return "Name: " + name + " | ID: " + userId + " | Vehicle: " + vehicleNumber;
    }
}
