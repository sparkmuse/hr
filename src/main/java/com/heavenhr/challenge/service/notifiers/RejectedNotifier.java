package com.heavenhr.challenge.service.notifiers;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RejectedNotifier implements Notifier {

    @Override
    public boolean send(Application application) {
        if (application.getStatus() == Status.REJECTED) {
            log.info("I am sorry to inform that we are not moving forward with your application");
            return true;
        }
        return false;
    }
}
