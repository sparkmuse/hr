package com.heavenhr.challenge.service;

import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.exceptions.OfferNotFoundException;
import com.heavenhr.challenge.exceptions.OfferTitleAlreadyExistsException;
import com.heavenhr.challenge.repositories.OfferRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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
                .id(1L)
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

    @Test
    @DisplayName("returns offer when found")
    void getOffer() {

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        Offer actual = offerService.getOffer(1L);

        assertThat(actual).isEqualToComparingFieldByField(offer);
    }

    @Test
    @DisplayName("throws OfferNotFoundException when the offer is not found")
    void getOfferNotFound() {

        when(offerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> offerService.getOffer(1L))
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer with id=%s was not found", 1L);
    }


    @Test
    @DisplayName("create an offer")
    void createOffer() {

        when(offerRepository.findByJobTitle("Java Developer")).thenReturn(Optional.empty());
        when(offerRepository.save(any())).thenReturn(offer);

        Offer actual = offerService.createOffer("Java Developer");

        assertThat(actual).isEqualToComparingFieldByField(offer);
    }

    @Test
    @DisplayName("throws OfferNotFoundException when the offer title exists")
    void createOfferTitleAlreadyExceists() {

        when(offerRepository.findByJobTitle("Java Developer")).thenReturn(Optional.of(offer));

        assertThatThrownBy(() -> offerService.createOffer("Java Developer"))
                .isInstanceOf(OfferTitleAlreadyExistsException.class)
                .hasMessage("Offer title already exists");
    }
}