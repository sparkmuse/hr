package com.heavenhr.challenge.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Offer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String jobTitle;

    private LocalDate startDate;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "offer_id")
    private List<Application> applications = new ArrayList<>();

    @Transient
    @Setter(AccessLevel.PRIVATE)
    private int numberOfApplications;

    public int getNumberOfApplications() {
        return this.applications.size();
    }
}
