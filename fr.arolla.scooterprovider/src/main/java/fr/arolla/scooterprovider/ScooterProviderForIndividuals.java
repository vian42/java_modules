package fr.arolla.scooterprovider;

import org.arolla.internal.ScooterAdvancedDiagnostic;
import fr.arolla.scooterprovider.scooter.Scooter;
import fr.arolla.scooterprovider.scooter.State;

public class ScooterProviderForIndividuals implements ScooterProvider {
    @Override
    public boolean rentAScooter(Scooter scooter) {
        if (scooter.isRented() || scooter.getBatteryPercentage() < 10 || scooter.getState() == State.BAD) {
            return false;
        }

        scooter.setRented(true);

        return true;

    }

    @Override
    public boolean giveBackAScooter(Scooter scooter) {
        if (scooter.getState() == State.BAD) {
            return false;
        }

        scooter.setRented(false);
        final ScooterAdvancedDiagnostic scooterAdvancedDiagnostic = new ScooterAdvancedDiagnostic(scooter);
        scooterAdvancedDiagnostic.updateMileage(scooter.getDistanceTraveled());

        return true;
    }

    @Override
    public boolean juiceAScooter(Scooter scooter) {
        if (scooter.getBatteryPercentage() > 70 || scooter.isRented()) {
            return false;
        }

        scooter.setRented(true);

        return true;
    }
}
