package com.test_stm.service;

import org.springframework.stereotype.Service;

import com.test_stm.dao.CarrierDAO;
import com.test_stm.model.Carrier;
import java.util.List;

@Service
public class CarrierService {

    private CarrierDAO carrierDAO;

    public List<Carrier> getAllCarriers() {
        return carrierDAO.getAllCarriers();
    }

    public Carrier createCarrier(Carrier carrier) {
        return carrierDAO.createCarrier(carrier);
    }

    public Carrier getCarrierById(Long id) {
        return carrierDAO.getCarrierById(id);
    }

    public void updateCarrier(Carrier carrier) {
        carrierDAO.updateCarrier(carrier);
    }

    public void deleteCarrier(Long id) {
        carrierDAO.deleteCarrier(id);
    }
}