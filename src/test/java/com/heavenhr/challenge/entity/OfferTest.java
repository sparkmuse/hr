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
}