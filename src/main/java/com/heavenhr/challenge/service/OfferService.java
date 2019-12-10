package com.heavenhr.challenge.service;

import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.repositories.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public Iterable<Offer> getOffers() {
        return offerRepository.findAll();
    }
}
