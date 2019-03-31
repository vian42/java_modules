package fr.arolla.scooterprovider.scooter;

import fr.arolla.Randomizer;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ScooterTest {

    @Test
    public void test_setters() {
        final Scooter scooter = Randomizer.randomizer.nextObject(Scooter.class);

        final String newId = "azerty";
        scooter.setId(newId);
        assertThat(scooter.getId()).isNotEqualTo(newId);

        if (scooter.getProvider() == Provider.BIRD) {
            scooter.setProvider(Provider.LIME);
            assertThat(scooter.getProvider()).isNotEqualTo(Provider.LIME);
        } else {
            scooter.setProvider(Provider.BIRD);
            assertThat(scooter.getProvider()).isNotEqualTo(Provider.BIRD);
        }
    }

}