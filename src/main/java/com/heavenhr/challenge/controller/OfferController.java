package com.heavenhr.challenge.controller;

import com.heavenhr.challenge.entity.ApplicationDto;
import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping
    public Iterable<Offer> getOffers() {
        return offerService.getOffers();
    }

    @GetMapping(value = "/{offerId}")
    public Offer getOffer(@PathVariable Long offerId) {
        return offerService.getOffer(offerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOffer(@RequestBody String offerTitle) {
        return offerService.createOffer(offerTitle);
    }

    @PostMapping(value = "/{offerId}/applications")
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOffer(@PathVariable Long offerId, @RequestBody ApplicationDto applicationDto) {
        return offerService.createApplication(offerId, applicationDto);
    }
}