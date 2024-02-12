package stm.carrier.service;

import stm.carrier.dto.CarrierDto;
import stm.carrier.dto.CarrierFullDto;

import java.util.List;

public interface CarrierService {
    CarrierFullDto addCarrier(CarrierDto dto);

    void deleteCarrierById(int id);

    List<CarrierFullDto> getAllFullCarriers();

    CarrierFullDto updateCarrier(int id, CarrierFullDto dto);

    CarrierFullDto getCarrierById(int id);

    List<CarrierDto> getAllCarriers();
}
