package org.arolla.internal;

import fr.arolla.scooterprovider.scooter.Scooter;

public class ScooterAdvancedDiagnostic {

    private static final int MIN_ALLOWED_MILEAGE = 10;
    private DetailedScooter detailedScooter;

    private ScooterAdvancedDiagnostic() {}

    public ScooterAdvancedDiagnostic(Scooter scooter) {
        this.detailedScooter = new DetailedScooter(scooter);
        detailedScooter.setBatteryPercentage(scooter.getBatteryPercentage());
        detailedScooter.setId(scooter.getId());
        detailedScooter.setProvider(scooter.getProvider());
        detailedScooter.setRented(scooter.isRented());
        detailedScooter.setState(scooter.getState());
    }

    public boolean changePower(Power newPowerMode) {
        if (detailedScooter.getBatteryPercentage() < 20) {
            return false;
        }

        detailedScooter.setPower(newPowerMode);

        return true;
    }

    public boolean changeMileage(Integer newMileage) {
        if (newMileage < MIN_ALLOWED_MILEAGE) {
            return false;
        }

        detailedScooter.setMileage(newMileage);

        return true;
    }

    public void updateMileage(Integer distanceTravelled) {
        final int updatedMileage = detailedScooter.getMileage() + distanceTravelled;
        detailedScooter.setMileage(updatedMileage);
    }
}
