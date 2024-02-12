package stm.carrier.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stm.carrier.dto.CarrierDto;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.service.CarrierService;

import java.util.List;

@RestController
@RequestMapping("/admin/carriers")
@RequiredArgsConstructor
@Validated
public class CarrierControllerAdmin {
    private final CarrierService carrierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarrierFullDto addCarrier(@RequestBody @Validated CarrierDto dto) {
        return carrierService.addCarrier(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCarrier(@PathVariable int id){
        carrierService.deleteCarrierById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CarrierFullDto> getAllCarriers(){
        return carrierService.getAllFullCarriers();
    }

    @PatchMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarrierFullDto patchCarrier(@PathVariable int id,
                                   @RequestBody @Validated CarrierFullDto dto){
        return carrierService.updateCarrier(id, dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarrierFullDto getCarrierById(@PathVariable int id){
        return carrierService.getCarrierById(id);
    }
}
