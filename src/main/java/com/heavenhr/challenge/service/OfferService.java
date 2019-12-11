package com.heavenhr.challenge.service;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.ApplicationDto;
import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.entity.Status;
import com.heavenhr.challenge.exceptions.ApplicationEmailAlreadyExists;
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

    public Offer createApplication(Long offerId, ApplicationDto applicationDto) {

        Offer offer = getOffer(offerId);

        if (hasEmail(applicationDto, offer)) {
            throw new ApplicationEmailAlreadyExists("Email already exists for this application");
        }

        Application application = Application.builder()
                .candidateEmail(applicationDto.getCandidateEmail())
                .resumeText(applicationDto.getResumeText())
                .status(Status.APPLIED).build();
        offer.addApplication(application);

        return offer;
    }

    private boolean hasEmail(ApplicationDto applicationDto, Offer offer) {
        return offer.getApplications()
                .stream()
                .anyMatch(a -> a.getCandidateEmail().equals(applicationDto.getCandidateEmail()));
    }
}
