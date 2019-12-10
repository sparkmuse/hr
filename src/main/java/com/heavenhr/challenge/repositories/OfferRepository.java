package com.heavenhr.challenge.repositories;

import com.heavenhr.challenge.entity.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository  extends CrudRepository<Offer, Long> {
}
