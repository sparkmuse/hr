package com.heavenhr.challenge.service;

import com.heavenhr.challenge.entity.Application;
import com.heavenhr.challenge.service.notifiers.Notifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotifierService {

    private final List<Notifier> notifiers;

    public boolean send(Application application) {

        return notifiers.stream()
                .map(n -> n.send(application))
                .filter(r -> r)
                .findFirst()
                .orElse(false);
    }
}
