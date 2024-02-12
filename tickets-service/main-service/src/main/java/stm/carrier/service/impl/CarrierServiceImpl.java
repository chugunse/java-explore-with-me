package stm.carrier.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import stm.carrier.dto.CarrierDto;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.mapper.CarrierMapper;
import stm.carrier.model.Carrier;
import stm.carrier.service.CarrierService;
import stm.carrier.storage.CarrierRepository;
import stm.exception.model.ConflictException;
import stm.exception.model.ResourceNotFoundException;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class CarrierServiceImpl implements CarrierService {
    private final CarrierRepository carrierRepository;
    private final CarrierMapper carrierMapper;

    @Override
    public CarrierFullDto addCarrier(CarrierDto dto) {
        try {
            Carrier carrier = carrierRepository.save(carrierMapper.toCarrierModel(dto));
            return carrierMapper.toCarrierFullDto(carrier);
        } catch (DuplicateKeyException e) {
            throw new ConflictException("компания '" + dto.getCompany() + "' или ее номер телефона '" +
                    dto.getPhone() + " уже используются в базе");
        }
    }

    @Override
    public void deleteCarrierById(int id) {
        try {
            carrierRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception){
            throw new ResourceNotFoundException("компания с id = " + id + " не найдена");
        }
    }

    @Override
    public List<CarrierFullDto> getAllFullCarriers() {
        return carrierMapper.toCarrierFullDtoList(carrierRepository.getAll());
    }

    @Override
    public CarrierFullDto updateCarrier(int id, CarrierFullDto dto) {
        CarrierFullDto carrierFullDtoFromDB = getCarrierById(id);
        ofNullable(dto.getCompany()).ifPresent(carrierFullDtoFromDB::setCompany);
        ofNullable(dto.getPhone()).ifPresent(carrierFullDtoFromDB::setPhone);
        carrierRepository.update(carrierMapper.toCarrierModel(carrierFullDtoFromDB));
        return carrierFullDtoFromDB;
    }

    @Override
    public CarrierFullDto getCarrierById(int id) {
        try {
            return carrierMapper.toCarrierFullDto(carrierRepository.getById(id));
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }

    @Override
    public List<CarrierDto> getAllCarriers() {
        return carrierMapper.toCarrierDtoList(carrierRepository.getAll());
    }


}
