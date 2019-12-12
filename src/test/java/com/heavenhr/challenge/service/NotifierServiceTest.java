package com.heavenhr.challenge.service;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.Status;
import com.heavenhr.challenge.service.notifiers.Notifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {NotifierService.class, NotifierServiceTest.NotifierConfiguration.class})
class NotifierServiceTest {

    @Autowired
    private NotifierService notifierService;

    @ParameterizedTest
    @EnumSource(Status.class)
    @DisplayName("traverses all notifiers")
    void traverses( Status status) {

        Application application = Application.builder().status(status).build();

        boolean actual = notifierService.send(application);

        assertThat(actual).isTrue();
    }

    @TestConfiguration()
    @ComponentScan(basePackageClasses = Notifier.class)
    static class NotifierConfiguration {
    }
}