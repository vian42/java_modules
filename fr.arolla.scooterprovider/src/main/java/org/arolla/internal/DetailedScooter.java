package org.arolla.internal;

import fr.arolla.scooterprovider.scooter.Scooter;
import io.github.benas.randombeans.randomizers.number.IntegerRandomizer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailedScooter extends Scooter {

    private Integer mileage;
    private Power power;

    public DetailedScooter(Scooter scooter) {
        this.setId(scooter.getId());
        this.setProvider(scooter.getProvider());
        this.setState(scooter.getState());
        this.setRented(scooter.isRented());
        this.setBatteryPercentage(scooter.getBatteryPercentage());
        this.setMileage(IntegerRandomizer.aNewIntegerRandomizer().getRandomValue());
        this.setPower(Power.STANDARD);
    }
}
