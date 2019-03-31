package fr.arolla.scooterprovider.scooter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.arolla.internal.DetailedScooter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Scooter {

    private String id;
    private Provider provider;
    private State state;
    private Integer batteryPercentage;
    @Setter
    private boolean rented;
    private Integer distanceTraveled;

    public void setId(String id) {
        if (this instanceof DetailedScooter) {
            this.id = id;
        }
    }

    public void setProvider(Provider provider) {
        if (this instanceof DetailedScooter) {
            this.provider = provider;
        }
    }

    public void setState(State state) {
        if (this instanceof DetailedScooter) {
            this.state = state;
        }
    }

    public void setBatteryPercentage(Integer batteryPercentage) {
        if (this instanceof DetailedScooter) {
            this.batteryPercentage = batteryPercentage;
        }
    }
}
