package com.heavenhr.challenge.repositories;

import com.heavenhr.challenge.entity.Offer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class OfferRepositoryTest {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private EntityManager entityManager;

    private Offer offer;

    @BeforeEach
    void setUp() {
        offer = Offer.builder()
                .jobTitle("Java Developer")
                .startDate(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("creates offer")
    void canSave() {

        Offer savedOffer = offerRepository.save(offer);

        Offer found = entityManager
                .createQuery("FROM Offer WHERE id = :id", Offer.class)
                .setParameter("id", savedOffer.getId())
                .getSingleResult();

        assertThat(found).isEqualToComparingFieldByField(offer);
    }

    @Test
    @DisplayName("gets a list of offers")
    void getOffers() {

        offerRepository.save(offer);

        Iterable<Offer> offers = offerRepository.findAll();

        assertThat(offers).containsExactly(offer);

    }

    @Test
    @DisplayName("gets an offer by id")
    void getOffer() {

        Offer savedOffer = offerRepository.save(offer);
        Optional<Offer> expected = Optional.of(savedOffer);

        Optional<Offer> actual = offerRepository.findById(savedOffer.getId());

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("gets an offer by title")
    void getOfferByTitle() {

        Offer savedOffer = offerRepository.save(offer);
        Optional<Offer> expected = Optional.of(savedOffer);

        Optional<Offer> actual = offerRepository.findByJobTitle(savedOffer.getJobTitle());

        assertThat(actual).isEqualTo(expected);

    }
}