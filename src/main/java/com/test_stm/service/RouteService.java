package com.test_stm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test_stm.model.Route;
import com.test_stm.repository.RouteRepository;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public List<Route> getAllRoutes() {
        return (List<Route>) routeRepository.findAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public void createRoute(Route route) {
        routeRepository.save(route);
    }

    public void updateRoute(Route route) {
        routeRepository.update(route);
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }
}
