package model;

public class Driver extends User {

    public Driver(String name, String userId, String vehicleNumber) {
        super(name, userId, vehicleNumber);
    }

    @Override
    public double calculateParkingFee(int parkingDurationInHours) {
        return parkingDurationInHours * 50.0; // Standard rate: Rs 50/hour
    }
}
