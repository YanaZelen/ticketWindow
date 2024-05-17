package com.test_stm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test_stm.model.Carrier;
import com.test_stm.repository.CarrierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarrierService {

    @Autowired
    private CarrierRepository carrierRepository;

    public List<Carrier> getAllCarriers() {
        return (List<Carrier>) carrierRepository.findAll();
    }

    public Carrier getCarrierById(Long id) {
        return carrierRepository.findCarrierById(id);
    }

    public Carrier createCarrier(Carrier carrier) {
        return carrierRepository.save(carrier);
    }

    public void updateCarrier(Carrier carrier) {
        carrierRepository.update(carrier);
    }

    public void deleteCarrier(Long id) {
        carrierRepository.deleteById(id);
    }
}