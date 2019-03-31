package fr.arolla.scooterclient;

import fr.arolla.scooterprovider.ScooterProviderForIndividuals;
import fr.arolla.scooterprovider.scooter.Provider;
import fr.arolla.scooterprovider.scooter.Scooter;
import fr.arolla.scooterprovider.scooter.State;
import org.arolla.internal.DetailedScooter;
import org.arolla.internal.Power;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class ScooterRentalClientTest {

    private ScooterRentalClient scooterRentalClient;

    @Before
    public void setUp() {
        scooterRentalClient = new ScooterRentalClient(new ScooterProviderForIndividuals());
    }

    @Test
    public void test_to_rent_successfully_a_scooter() {
        final User user = new User();
        final Scooter scooter = createScooterWithGivenState(State.GOOD);

        final boolean successRental = scooterRentalClient.rentAScooter(user, scooter);

        assertThat(successRental).isTrue();
        assertThat(user.isHappy()).isTrue();
        assertThat(user.isUsingALocomotion()).isTrue();
        assertThat(user.getRentedLocomotionId()).isEqualTo(scooter.getId());
    }

    @Test
    public void test_to_rent_unsuccessfully_a_scooter() {
        final User user = new User();
        user.setUsingALocomotion(false);
        final Scooter scooter = createScooterWithGivenState(State.BAD);

        final boolean successRental = scooterRentalClient.rentAScooter(user, scooter);

        assertThat(successRental).isFalse();
        assertThat(user.isHappy()).isFalse();
        assertThat(user.isUsingALocomotion()).isFalse();
        assertThat(user.getRentedLocomotionId()).isNotEqualTo(scooter.getId());
    }

    @Test
    public void test_to_give_back_successfully_a_scooter() {
        final User user = new User();
        final BigDecimal initialCredit = BigDecimal.TEN;
        user.setCreditAmount(initialCredit);
        final Scooter scooter = createScooterWithGivenState(State.GOOD);

        final boolean successGiveBack = scooterRentalClient.giveBackAScooter(user, scooter);

        assertThat(successGiveBack).isTrue();
        assertThat(user.isHappy()).isTrue();
        assertThat(user.getRentedLocomotionId()).isNull();

        boolean userWalletClassExists = false;

        try {
            Class.forName("fr.arolla.scooterprovider.userwallet.Wallet");
            userWalletClassExists = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (userWalletClassExists) {
            assertThat(user.getCreditAmount()).isNotEqualTo(initialCredit);
        } else {
            assertThat(user.getCreditAmount()).isEqualTo(initialCredit);
        }
    }

    @Test
    public void test_to_give_back_unsuccessfully_a_scooter() {
        final User user = new User();
        user.setRentedLocomotionId("rentedScooterId");
        final Scooter scooter = createScooterWithGivenState(State.BAD);

        final boolean successGiveBack = scooterRentalClient.giveBackAScooter(user, scooter);

        assertThat(successGiveBack).isFalse();
        assertThat(user.isHappy()).isFalse();
        assertThat(user.getRentedLocomotionId()).isNotNull();
    }

    @Test
    public void test_to_juice_successfully_a_scooter() {
        final User user = new User();
        final Scooter scooter = createScooterWithGivenState(State.GOOD);

        final boolean successJuice = scooterRentalClient.juiceAScooter(user, scooter);

        assertThat(successJuice).isTrue();
        assertThat(user.isHappy()).isTrue();
        assertThat(user.isJuicingALocomotion()).isTrue();
        assertThat(user.getRentedLocomotionId()).isEqualTo(scooter.getId());
        assertThat(scooter.isRented()).isTrue();
    }

    @Test
    public void test_to_juice_unsuccessfully_a_scooter() {
        final User user = new User();
        final Scooter scooter = new Scooter("id", Provider.LIME, State.GOOD, 80, false, 0);

        final boolean successGiveBack = scooterRentalClient.juiceAScooter(user, scooter);

        assertThat(successGiveBack).isFalse();
        assertThat(user.isHappy()).isFalse();
    }

    private Scooter createScooterWithGivenState(State state) {
        return new Scooter("id", Provider.LIME, state, 50, false, 0);
    }
    
    @Test
    public void test_scooter_free_upgrade() {
        final Scooter simpleScooter = createScooterWithGivenState(State.GOOD);

        final int wantedMileage = 100;
        final DetailedScooter upgradedScooter = scooterRentalClient.pimpMyScooter(simpleScooter, Power.SPORT, wantedMileage);

        assertThat(upgradedScooter.getMileage()).isEqualTo(wantedMileage);
        assertThat(upgradedScooter.getPower()).isEqualTo(Power.SPORT);

        // We can also change other parameters
        final String newId = "newId";
        upgradedScooter.setId(newId);

        assertThat(upgradedScooter.getId()).isEqualTo(newId);

        final User user = new User();
        final boolean successRental = scooterRentalClient.rentAScooter(user, upgradedScooter);

        assertThat(successRental).isTrue();
        assertThat(user.isHappy()).isTrue();
        assertThat(user.isUsingALocomotion()).isTrue();
        assertThat(user.getRentedLocomotionId()).isEqualTo(upgradedScooter.getId());
    }

}