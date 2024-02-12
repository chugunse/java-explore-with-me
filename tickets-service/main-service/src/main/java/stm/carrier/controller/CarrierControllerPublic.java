package stm.carrier.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import stm.carrier.dto.CarrierDto;
import stm.carrier.service.CarrierService;

import java.util.List;

@RestController
@RequestMapping("carriers")
@RequiredArgsConstructor
public class CarrierControllerPublic {
    private final CarrierService carrierService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CarrierDto> getAllCarriers() {
        return carrierService.getAllCarriers();
    }
}
