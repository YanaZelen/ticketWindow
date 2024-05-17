package com.test_stm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test_stm.model.Route;
import com.test_stm.service.RouteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getRouteById(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRouteById(id).toString());
    }
    

    @PostMapping
    public void createRoute(@RequestBody Route route) {
        routeService.createRoute(route);
    }

    @PutMapping("/{id}")
    public void updateRoute(@PathVariable Long id, @RequestBody Route route) {
        route.setId(id);
        routeService.updateRoute(route);
    }

    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
    }
}
