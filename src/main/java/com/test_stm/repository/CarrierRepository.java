package com.test_stm.repository;

import org.springframework.data.repository.CrudRepository;

import com.test_stm.model.Carrier;
import com.test_stm.model.Route;

public interface CarrierRepository extends CrudRepository<Carrier, Long> {

    Carrier findCarrierById(Long id);

    void update(Carrier carrier);
}
