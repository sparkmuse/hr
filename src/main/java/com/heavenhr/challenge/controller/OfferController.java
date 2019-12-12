package com.heavenhr.challenge.controller;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.ApplicationDto;
import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.entity.Status;
import com.heavenhr.challenge.service.OfferService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(value = "/{offerId}/applications", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOfferApplication(@PathVariable Long offerId, @RequestBody ApplicationDto applicationDto) {
        return offerService.createApplication(offerId, applicationDto);
    }

    @GetMapping(value = "/{offerId}/applications")
    public Iterable<Application> getApplications(@PathVariable Long offerId) {
        return offerService.getApplications(offerId);
    }

    @GetMapping(value = "/{offerId}/applications/{applicationId}")
    public Application getApplications(@PathVariable Long offerId, @PathVariable Long applicationId) {
        return offerService.getApplication(offerId, applicationId);
    }

    @PutMapping(value = "/{offerId}/applications/{applicationId}")
    public Application updateApplication(@PathVariable Long offerId,
                                       @PathVariable Long applicationId,
                                       @RequestBody Application application) {
        return offerService.updateApplication(offerId, applicationId, application);
    }
}