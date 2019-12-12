package com.heavenhr.challenge.service.notifiers;

import com.heavenhr.challenge.entity.Application;

public interface  Notifier {
    boolean send(Application application);
}
