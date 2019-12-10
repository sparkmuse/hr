package com.heavenhr.challenge.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OfferTest {

    @Test
    @DisplayName("get the number of applications from the entity")
    void getNumberOfApplications() {

        Offer offer = new Offer();
        List<Application> applications = Collections.singletonList(new Application());
        offer.setApplications(applications);

        assertThat(offer.getNumberOfApplications()).isEqualTo(1);
    }

    @Test
    @DisplayName("add application adds an application to offer")
    void addApplication() {

        Offer offer = Offer.builder().build();
        offer.addApplication(new Application());

        assertThat(offer.getApplications()).hasSize(1);
    }

    @Test
    @DisplayName("add application sets offer field to offer")
    void addApplicationSet() {

        Application expected = new Application();
        Offer offer = Offer.builder().build();
        offer.addApplication(expected);

        Application actual = offer.getApplications().get(0);
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @Test
    @DisplayName("remove application removes an application from offer")
    void removeApplication() {

        Offer offer = Offer.builder().build();
        Application application = new Application();
        offer.addApplication(application);

        offer.removeApplication(application);

        assertThat(offer.getApplications()).isEmpty();
    }

    @Test
    @DisplayName("remove application sets offer field to NULL")
    void removeApplicationSet() {

        Application application = new Application();
        Offer offer = Offer.builder().build();
        offer.addApplication(application);

        offer.removeApplication(application);

        assertThat(application.getOffer()).isNull();
    }
}