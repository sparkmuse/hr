package com.heavenhr.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Application {

    @Id
    @GeneratedValue
    private Long id;

    private String candidateEmail;

    private String resumeText;

    private Status status;
}
