package com.heavenhr.challenge.service.notifiers;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppliedNotifier implements Notifier {

    @Override
    public boolean send(Application application) {
        if (application.getStatus() == Status.APPLIED) {
            log.info("You have APPLIED for the job");
            return true;
        }

        return false;
    }
}
