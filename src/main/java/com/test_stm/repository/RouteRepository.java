package com.test_stm.repository;

import org.springframework.data.repository.CrudRepository;

import com.test_stm.model.Route;

public interface RouteRepository extends CrudRepository<Route, Long> {

    Route findRouteById(Long id);

    void update(Route route);
}