package com.test_stm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test_stm.model.Carrier;
import com.test_stm.service.CarrierService;

import java.util.List;

@RestController
@RequestMapping("/carriers")
public class CarrierController {
    @Autowired
    private CarrierService carrierService;

    @GetMapping
    public List<Carrier> getAllRoutes() {
        return carrierService.getAllCarriers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCarrierById(@PathVariable Long id) {
        return ResponseEntity.ok(carrierService.getCarrierById(id).toString());
    }

    @PostMapping
    public Carrier createCarrier(@RequestBody Carrier carrier) {
        return carrierService.createCarrier(carrier);
    }
}
