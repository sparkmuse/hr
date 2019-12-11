package com.heavenhr.challenge.controller;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.ApplicationDto;
import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.entity.Status;
import com.heavenhr.challenge.exceptions.EmailAlreadyExistsException;
import com.heavenhr.challenge.exceptions.OfferNotFoundException;
import com.heavenhr.challenge.exceptions.OfferTitleAlreadyExistsException;
import com.heavenhr.challenge.service.OfferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {OfferController.class, OfferControllerAdvice.class})
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @Test
    @DisplayName("returns all the offers")
    void returns200() throws Exception {

        LocalDate startDate = LocalDate.now();
        Offer offer = Offer.builder()
                .id(1L)
                .jobTitle("Java Developer")
                .startDate(startDate)
                .numberOfApplications(0)
                .build();

        when(offerService.getOffers()).thenReturn(Collections.singletonList(offer));

        mockMvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].jobTitle").value("Java Developer"))
                .andExpect(jsonPath("$[0].startDate").value(startDate.toString()))
                .andExpect(jsonPath("$[0].numberOfApplications").value(0));
    }


    @Test
    @DisplayName("returns all the offers")
    void returnsEmpty() throws Exception {

        when(offerService.getOffers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @DisplayName("returns offer")
    void returnsOneOffer() throws Exception {

        LocalDate startDate = LocalDate.now();
        Offer offer = Offer.builder()
                .id(1L)
                .jobTitle("Java Developer")
                .startDate(startDate)
                .numberOfApplications(0)
                .build();

        when(offerService.getOffer(anyLong())).thenReturn(offer);

        mockMvc.perform(get("/offers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.jobTitle").value("Java Developer"))
                .andExpect(jsonPath("$.startDate").value(startDate.toString()))
                .andExpect(jsonPath("$.numberOfApplications").value(0));
    }


    @Test
    @DisplayName("throws exception when offer not found")
    void notFound() throws Exception {

        when(offerService.getOffer(anyLong())).thenThrow(OfferNotFoundException.class);

        mockMvc.perform(get("/offers/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("returns created offer")
    void returnsCreatedOffer() throws Exception {

        LocalDate startDate = LocalDate.now();
        Offer offer = Offer.builder()
                .id(1L)
                .jobTitle("Java Developer")
                .startDate(startDate)
                .numberOfApplications(0)
                .build();

        when(offerService.createOffer(anyString())).thenReturn(offer);

        mockMvc.perform(post("/offers")
                .content("{\"offerTitle\": \"Java Developer\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.jobTitle").value("Java Developer"))
                .andExpect(jsonPath("$.startDate").value(startDate.toString()))
                .andExpect(jsonPath("$.numberOfApplications").value(0));
    }

    @Test
    @DisplayName("throws exception when offertitle already exists")
    void alreadyExists() throws Exception {

        when(offerService.createOffer(anyString())).thenThrow(OfferTitleAlreadyExistsException.class);

        mockMvc.perform(post("/offers")
                .content("{\"offerTitle\": \"Java Developer\"}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("returns offer with applications")
    void returnsOfferWithApplication() throws Exception {

        LocalDate startDate = LocalDate.now();
        Offer offer = Offer.builder()
                .id(1L)
                .jobTitle("Java Developer")
                .startDate(startDate)
                .numberOfApplications(0)
                .build();
        Application application = Application
                .builder()
                .id(1L)
                .candidateEmail("email@email.com")
                .resumeText("resume text")
                .status(Status.APPLIED)
                .build();
        offer.getApplications().add(application);

        when(offerService.createApplication(anyLong(), any(ApplicationDto.class)))
                .thenReturn(offer);

        mockMvc.perform(post("/offers/1/applications")
                    .content("{\"candidateEmail\": \"email@email.com\", \"resumeText\": \"resume text\"}")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.jobTitle").value("Java Developer"))
                .andExpect(jsonPath("$.startDate").value(startDate.toString()))
                .andExpect(jsonPath("$.numberOfApplications").value(1))
                .andExpect(jsonPath("$.applications[0].id").value(1L))
                .andExpect(jsonPath("$.applications[0].candidateEmail").value("email@email.com"))
                .andExpect(jsonPath("$.applications[0].resumeText").value("resume text"));
    }

    @Test
    @DisplayName("throws exception when candidate email already exists")
    void emailAlreadyExists() throws Exception {

        when(offerService.createApplication(anyLong(), any(ApplicationDto.class)))
                .thenThrow(EmailAlreadyExistsException.class);

        mockMvc.perform(post("/offers/1/applications")
                    .content("{\"candidateEmail\": \"email@email.com\", \"resumeText\": \"resume text\"}")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}