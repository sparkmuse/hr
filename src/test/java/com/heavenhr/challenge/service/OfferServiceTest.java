package com.heavenhr.challenge.service;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.ApplicationDto;
import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.entity.Status;
import com.heavenhr.challenge.exceptions.ApplicationNotFoundException;
import com.heavenhr.challenge.exceptions.EmailAlreadyExistsException;
import com.heavenhr.challenge.exceptions.OfferNotFoundException;
import com.heavenhr.challenge.exceptions.OfferTitleAlreadyExistsException;
import com.heavenhr.challenge.repositories.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private NotifierService notifierService;

    private OfferService offerService;

    private Offer offer;

    @BeforeEach
    void setUp() {
        offer = Offer.builder()
                .id(1L)
                .jobTitle("Java Developer")
                .startDate(LocalDate.now())
                .build();

        offerService = new OfferService(offerRepository, notifierService);
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

    @Test
    @DisplayName("user can create an application")
    void createApplication() {

        Application application = Application
                .builder()
                .id(1L)
                .candidateEmail("email@email.com")
                .resumeText("resume text")
                .status(Status.APPLIED)
                .build();
        ApplicationDto applicationDto = new ApplicationDto("email@email.com", "resume text");

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        ArgumentCaptor<Offer> offerCapture = ArgumentCaptor.forClass(Offer.class);

        offerService.createApplication(1L, applicationDto);
        verify(offerRepository).save(offerCapture.capture());

        assertThat(offerCapture.getValue().getApplications())
                .usingElementComparatorIgnoringFields("id", "offer")
                .containsExactlyInAnyOrder(application);

    }

    @Test
    @DisplayName("throws ApplicationEmailAlreadyExists when the email already exists")
    void throwExceptionWhenEMail() {

        Application application = Application
                .builder()
                .id(1L)
                .candidateEmail("email@email.com")
                .resumeText("resume text")
                .status(Status.APPLIED)
                .build();
        offer.getApplications().add(application);
        ApplicationDto applicationDto = new ApplicationDto("email@email.com", "resume text");

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        assertThatThrownBy(() -> offerService.createApplication(1L, applicationDto))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage("Email already exists for this application");
    }

    @Test
    @DisplayName("returns application for the offer")
    void getApplication() {

        Application application = Application
                .builder()
                .id(1L)
                .candidateEmail("email@email.com")
                .resumeText("resume text")
                .status(Status.APPLIED)
                .build();
        offer.getApplications().add(application);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        Application actual = offerService.getApplication(1L, 1L);

        assertThat(actual).isEqualToComparingFieldByField(application);
    }

    @Test
    @DisplayName("returns all applications for the offer")
    void getApplications() {

        Application application = Application
                .builder()
                .id(1L)
                .candidateEmail("email@email.com")
                .resumeText("resume text")
                .status(Status.APPLIED)
                .build();
        offer.getApplications().add(application);
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        Iterable<Application> actual = offerService.getApplications(1L);

        assertThat(actual).containsExactlyInAnyOrder(application);
    }

    @Test
    @DisplayName("updates and returns application for the offer")
    void updateApplication() {

        Application application = Application
                .builder()
                .id(1L)
                .candidateEmail("email@email.com")
                .resumeText("resume text")
                .status(Status.APPLIED)
                .build();
        offer.getApplications().add(application);

        Application expected = Application
                .builder()
                .status(Status.INVITED)
                .build();

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        Application actual = offerService.updateApplication(1L, 1L, expected);

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @Test
    @DisplayName("throws exception when application is not found.")
    void updateApplicationException() {

        Application application = Application
                .builder()
                .id(1L)
                .candidateEmail("email@email.com")
                .resumeText("resume text")
                .status(Status.APPLIED)
                .build();
        Application expected = Application
                .builder()
                .status(Status.INVITED)
                .build();

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        assertThatThrownBy(() -> offerService.updateApplication(1L, 1L, expected))
                .isInstanceOf(ApplicationNotFoundException.class)
                .hasMessage("Application was not found");

    }

    @Test
    @DisplayName("update an application status sends a notification")
    void updateApplicationNotification() {

        Application application = Application
                .builder()
                .id(1L)
                .candidateEmail("email@email.com")
                .resumeText("resume text")
                .status(Status.APPLIED)
                .build();
        offer.getApplications().add(application);

        Application expected = Application
                .builder()
                .status(Status.INVITED)
                .build();

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        offerService.updateApplication(1L, 1L, expected);

        verify(notifierService).send(application);
    }
}