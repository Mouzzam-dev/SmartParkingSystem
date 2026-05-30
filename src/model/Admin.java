package model;

public class Admin extends User {

    public Admin(String name, String userId, String vehicleNumber) {
        super(name, userId, vehicleNumber);
    }

    @Override
    public double calculateParkingFee(int parkingDurationInHours) {
        double standardRate = parkingDurationInHours * 50.0;
        double discountRate = standardRate * 0.70; // 30% corporate discount
        return discountRate;
    }

    public boolean isAdmin() {
        return true;
    }
}
