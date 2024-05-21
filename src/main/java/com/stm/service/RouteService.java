package com.stm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stm.dao.RouteDAO;
import com.stm.model.Route;

@Service
public class RouteService {

    private RouteDAO routeDAO;

    public List<Route> getAllRoutes() {
        return routeDAO.getAllRoutes();
    }

    public void createRoute(Route route) {
        routeDAO.createRoute(route);
    }

    public Route getRouteById(Long id) {
        return routeDAO.getRouteById(id);
    }

    public void updateRoute(Route route) {
        routeDAO.updateRoute(route);
    }

    public void deleteRoute(Long id) {
        routeDAO.deleteRoute(id);
    }
}
