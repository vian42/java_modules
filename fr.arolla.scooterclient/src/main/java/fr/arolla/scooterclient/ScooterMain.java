package fr.arolla.scooterclient;

import fr.arolla.scooterprovider.ScooterProviderForIndividuals;
import fr.arolla.scooterprovider.scooter.Provider;
import fr.arolla.scooterprovider.scooter.Scooter;
import fr.arolla.scooterprovider.scooter.State;

public class ScooterMain {

    public static void main(String[] args) {
        final ScooterRentalClient scooterRentalClient = new ScooterRentalClient(new ScooterProviderForIndividuals());

        final User user = new User();
        final Scooter scooter = createScooterWithGivenState(State.GOOD);

        scooterRentalClient.rentAScooter(user, scooter);
        scooterRentalClient.giveBackAScooter(user, scooter);
    }

    private static Scooter createScooterWithGivenState(State state) {
        return new Scooter("id", Provider.LIME, state, 50, false, 0);
    }
}
