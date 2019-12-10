package com.heavenhr.challenge.repositories;

import com.heavenhr.challenge.entity.Offer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
class OfferRepositoryTest {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("can create offer")
    void canSave() {

        Offer offer = Offer.builder()
                .jobTitle("Java Developer")
                .startDate(LocalDate.now())
                .build();
        Offer savedOffer = offerRepository.save(offer);

        Offer found = entityManager
                .createQuery("FROM Offer WHERE id = :id", Offer.class)
                .setParameter("id", savedOffer.getId())
                .getSingleResult();

        assertEquals(found, offer);
    }

}