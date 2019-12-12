package com.heavenhr.challenge.service.notifiers;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InvitedNotifier implements Notifier {

    @Override
    public boolean send(Application application) {
        if (application.getStatus() == Status.INVITED) {
            log.info("You have been INVITED for an interview");
            return true;
        }
        return false;
    }
}
