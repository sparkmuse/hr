package com.heavenhr.challenge.service;

import com.heavenhr.challenge.App;
import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.ApplicationDto;
import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.entity.Status;
import com.heavenhr.challenge.exceptions.ApplicationNotFoundException;
import com.heavenhr.challenge.exceptions.EmailAlreadyExistsException;
import com.heavenhr.challenge.exceptions.OfferNotFoundException;
import com.heavenhr.challenge.exceptions.OfferTitleAlreadyExistsException;
import com.heavenhr.challenge.repositories.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.metadata.HsqlTableMetaDataProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final NotifierService notifierService;

    public Iterable<Offer> getOffers() {
        return offerRepository.findAll();
    }

    public Offer getOffer(Long offerId) {
        return getOfferInternal(offerId);
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

        Offer offer = getOfferInternal(offerId);

        if (hasEmail(applicationDto, offer)) {
            throw new EmailAlreadyExistsException("Email already exists for this application");
        }

        Application application = Application.builder()
                .candidateEmail(applicationDto.getCandidateEmail())
                .resumeText(applicationDto.getResumeText())
                .status(Status.APPLIED).build();
        offer.getApplications().add(application);

        return offerRepository.save(offer);
    }

    private boolean hasEmail(ApplicationDto applicationDto, Offer offer) {
        return offer.getApplications()
                .stream()
                .anyMatch(a -> a.getCandidateEmail().equals(applicationDto.getCandidateEmail()));
    }

    public Iterable<Application> getApplications(Long offerId) {
        return getOfferInternal(offerId).getApplications();
    }

    public Application getApplication(Long offerId, Long applicationId) {
        Offer offer = getOfferInternal(offerId);
        return getApplicationInternal(offer, applicationId);
    }

    public Application updateApplication(Long offerId, Long applicationId, Application application) {

        Offer offer = getOfferInternal(offerId);
        Application found = getApplicationInternal(offer, applicationId);

        if (found.getStatus() != application.getStatus()) {
            found.setStatus(application.getStatus());
            notifierService.send(found);
        }

        offerRepository.save(offer);

        return application;
    }

    private Offer getOfferInternal(Long offerId) {
        return offerRepository
                .findById(offerId)
                .orElseThrow(() -> new OfferNotFoundException(String.format("Offer with id=%d was not found", offerId)));
    }

    private Application getApplicationInternal(Offer offer, Long applicationId) {
        return offer.getApplications()
                .stream()
                .filter(a -> a.getId().equals(applicationId))
                .findFirst()
                .orElseThrow(() -> new ApplicationNotFoundException("Application was not found"));
    }
}
