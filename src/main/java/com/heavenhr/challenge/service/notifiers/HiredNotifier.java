package com.heavenhr.challenge.service.notifiers;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HiredNotifier implements Notifier {

    @Override
    public boolean send(Application application) {
        if (application.getStatus() == Status.HIRED) {
            log.info("Congratulations you have been HIRED!");
            return true;
        }
        return false;
    }
}
