package com.heavenhr.challenge.service;

import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.repositories.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    private OfferService offerService;

    private Offer offer;

    @BeforeEach
    void setUp() {
        offer = Offer.builder()
                .jobTitle("Java Developer")
                .startDate(LocalDate.now())
                .build();

        offerService = new OfferService(offerRepository);
    }

    @Test
    @DisplayName("returns all the offers")
    void getOffers() {

        when(offerRepository.findAll()).thenReturn(Collections.singletonList(offer));

        Iterable<Offer> actual = offerService.getOffers();

        assertThat(actual).containsExactly(offer);
    }

    @Test
    @DisplayName("returns empty if there are no offers")
    void getOffersEmpty() {

        when(offerRepository.findAll()).thenReturn(Collections.emptyList());

        Iterable<Offer> actual = offerService.getOffers();

        assertThat(actual).isEmpty();
    }
}