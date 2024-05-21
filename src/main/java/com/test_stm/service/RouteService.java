package com.test_stm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test_stm.dao.RouteDAO;
import com.test_stm.dao.UserDAO;
import com.test_stm.model.Route;
import com.test_stm.model.Ticket;

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
