package fr.arolla.scooterprovider;

import fr.arolla.scooterprovider.scooter.Scooter;

public interface ScooterProvider {

    boolean rentAScooter(Scooter scooter);

    boolean giveBackAScooter(Scooter scooter);

    boolean juiceAScooter(Scooter scooter);
}
