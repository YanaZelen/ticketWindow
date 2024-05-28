package com.stm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stm.model.Carrier;
import com.stm.service.CarrierService;

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
    public ResponseEntity<Carrier> getCarrierById(@PathVariable Long id) {
        Carrier carrier = carrierService.getCarrierById(id);
        return ResponseEntity.ok(carrier);
    }

    @PostMapping
    public Carrier createCarrier(@RequestBody Carrier carrier) {
        return carrierService.createCarrier(carrier);
    }

    @PutMapping("/{id}")
    public void updateCarrier(@PathVariable Long id, @RequestBody Carrier carrier) {
        carrier.setId(id);
        carrierService.updateCarrier(carrier);
    }

    @DeleteMapping("/{id}")
    public void deleteCarrier(@PathVariable Long id) {
        carrierService.deleteCarrier(id);
    }
}
