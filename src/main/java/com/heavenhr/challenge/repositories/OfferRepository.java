package com.heavenhr.challenge.repositories;

import com.heavenhr.challenge.entity.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository  extends CrudRepository<Offer, Long> {
    Optional<Offer> findByJobTitle(String title);
}
