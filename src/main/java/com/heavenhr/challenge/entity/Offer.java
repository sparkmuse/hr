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
    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();

    @Transient
    @Setter(AccessLevel.PRIVATE)
    private int numberOfApplications;

    public int getNumberOfApplications() {
        return this.applications.size();
    }

    public void addApplication(Application application) {
        applications.add(application);
        application.setOffer(this);
    }

    public void removeApplication(Application application) {
        applications.remove(application);
        application.setOffer(null);
    }
}
