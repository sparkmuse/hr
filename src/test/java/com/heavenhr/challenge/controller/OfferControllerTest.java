package com.heavenhr.challenge.controller;

import com.heavenhr.challenge.entity.Offer;
import com.heavenhr.challenge.service.OfferService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OfferController.class)
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
}