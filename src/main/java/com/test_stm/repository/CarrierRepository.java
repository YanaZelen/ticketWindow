package com.test_stm.repository;

import org.springframework.data.repository.CrudRepository;

import com.test_stm.model.Carrier;
import com.test_stm.model.Route;

public interface CarrierRepository extends CrudRepository<Carrier, Long> {

    void update(Carrier carrier);
}
