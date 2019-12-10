package com.heavenhr.challenge.service;

import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.exceptions.OfferNotFoundException;
import com.heavenhr.challenge.repositories.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
