package com.heavenhr.challenge.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Status {
    APPLIED, INVITED, REJECTED, HIRED;

    @JsonCreator
    public static Status create(String value) {
        return Arrays.stream(values())
                .filter(v -> v.toString().equals(value)).findFirst().orElse(null);
    }
}
