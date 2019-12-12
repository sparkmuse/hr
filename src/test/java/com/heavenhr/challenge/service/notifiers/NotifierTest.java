package com.heavenhr.challenge.service.notifiers;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.management.DescriptorKey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NotifierTest {

    @Test
    @DisplayName("returns true when notifier matches")
    void send() {
        Application application = Application.builder().status(Status.APPLIED).build();
        AppliedNotifier appliedNotifier = new AppliedNotifier();

        boolean actual = appliedNotifier.send(application);

        assertThat(actual).isTrue();
    }
}