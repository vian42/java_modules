package fr.arolla.scooterprovider.internal;

import fr.arolla.Randomizer;
import org.arolla.internal.DetailedScooter;
import fr.arolla.scooterprovider.scooter.Provider;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DetailedScooterTest {

    @Test
    public void test_setters() {
        final DetailedScooter detailedScooter = Randomizer.randomizer.nextObject(DetailedScooter.class);

        final String newId = "azerty";
        detailedScooter.setId(newId);
        assertThat(detailedScooter.getId()).isEqualTo(newId);

        if (detailedScooter.getProvider() == Provider.BIRD) {
            detailedScooter.setProvider(Provider.LIME);
            assertThat(detailedScooter.getProvider()).isEqualTo(Provider.LIME);
        } else {
            detailedScooter.setProvider(Provider.BIRD);
            assertThat(detailedScooter.getProvider()).isEqualTo(Provider.BIRD);
        }
    }

}