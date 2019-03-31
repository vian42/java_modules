package fr.arolla.scooterclient;

import org.arolla.internal.DetailedScooter;
import org.arolla.internal.Power;
import fr.arolla.scooterprovider.ScooterProvider;
import fr.arolla.scooterprovider.scooter.Scooter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

class ScooterRentalClient {

    private static final BigDecimal RENTAL_PRICE = BigDecimal.ONE;
    private ScooterProvider scooterProvider;

    ScooterRentalClient(ScooterProvider scooterProvider) {
        this.scooterProvider = scooterProvider;
    }

    boolean rentAScooter(User user, Scooter scooter) {
        final boolean rentalSuccess = scooterProvider.rentAScooter(scooter);

        if (!rentalSuccess) {
            user.setHappy(false);
            return false;
        }

        user.setHappy(true);
        user.setRentedLocomotionId(scooter.getId());
        user.setUsingALocomotion(true);
        return true;
    }

    boolean giveBackAScooter(User user, Scooter scooter) {
        final boolean giveBackSuccess = scooterProvider.giveBackAScooter(scooter);

        if (!giveBackSuccess) {
            user.setHappy(false);
            return false;
        }

        user.setUsingALocomotion(false);
        user.setRentedLocomotionId(null);
        user.setHappy(true);
        updateUserWallet(user);

        return true;
    }

    boolean juiceAScooter(User user, Scooter scooter) {
        final boolean juiceSuccessful = scooterProvider.juiceAScooter(scooter);

        if (!juiceSuccessful) {
            user.setHappy(false);
            return false;
        }

        user.setRentedLocomotionId(scooter.getId());
        user.setJuicingALocomotion(true);
        user.setHappy(true);

        return true;
    }

    // The provider JAR is not well protected and we can tweak the scooter without their permission :)
    DetailedScooter pimpMyScooter(Scooter scooter, Power newPower, Integer newMileage) {
        final DetailedScooter detailedScooter = new DetailedScooter(scooter);

        detailedScooter.setPower(newPower);
        detailedScooter.setMileage(newMileage);

        return detailedScooter;
    }

    // TODO : what do we have to do to use reflection during the unit tests ?
    // During the execution ?
    private static void updateUserWallet(User user) {
        Class wallet = null;
        try {
            wallet = Class.forName("fr.arolla.scooterprovider.userwallet.Wallet");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (wallet != null) {

            final Method[] declaredMethods = wallet.getDeclaredMethods();

            try {
                final Method getUserCreditAmountById = Arrays.stream(declaredMethods)
                        .filter(m -> m.getName().contains("getUserCreditAmountById"))
                        .collect(Collectors.toList())
                        .get(0);

                final BigDecimal currentUserCredit = (BigDecimal) getUserCreditAmountById.invoke(wallet, user.getId());

                final BigDecimal finalUserCredit = currentUserCredit.subtract(RENTAL_PRICE);
                user.setCreditAmount(finalUserCredit);

                final Method updateUserCreditAmount = Arrays.stream(declaredMethods)
                        .filter(m -> m.getName().contains("updateUserCreditAmount"))
                        .collect(Collectors.toList())
                        .get(0);
                updateUserCreditAmount.invoke(wallet, user.getId(), finalUserCredit);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
