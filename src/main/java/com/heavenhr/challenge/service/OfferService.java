package com.heavenhr.challenge.service;

import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.exceptions.OfferNotFoundException;
import com.heavenhr.challenge.exceptions.OfferTitleAlreadyExistsException;
import com.heavenhr.challenge.repositories.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public Iterable<Offer> getOffers() {
        return offerRepository.findAll();
    }

    public Offer getOffer(Long offerId) {

        Optional<Offer> offer = offerRepository.findById(offerId);

        if (offer.isEmpty()) {
            String message = String.format("Offer with id=%d was not found", offerId);
            throw new OfferNotFoundException(message);
        }

        return offer.get();
    }


    public Offer createOffer(String offerTitle) {

        Optional<Offer> foundOffer = offerRepository.findByJobTitle(offerTitle);
        if (foundOffer.isPresent()) {
            throw new OfferTitleAlreadyExistsException("Offer title already exists");
        }

        Offer offer = Offer.builder()
                .startDate(LocalDate.now())
                .jobTitle(offerTitle)
                .build();

        return offerRepository.save(offer);
    }
}
