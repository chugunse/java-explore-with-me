package evajan.mainService.devices.service.impl;

import evajan.mainService.devices.dto.DeviceTypeDto;
import evajan.mainService.devices.dto.NewDeviceTypeDto;
import evajan.mainService.devices.mapper.DeviceTypeMapper;
import evajan.mainService.devices.model.DeviceType;
import evajan.mainService.devices.service.DeviceTypeService;
import evajan.mainService.devices.storage.DeviceTypeRepository;
import evajan.mainService.exceptions.model.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceTypeServiceImpl implements DeviceTypeService {
    private final DeviceTypeRepository deviceTypeRepository;
    private final DeviceTypeMapper mapper;

    @Override
    public DeviceTypeDto addNewDeviceType(NewDeviceTypeDto dto) {
        log.info("сохраняем в базу новый девайс тип {}", dto);
        DeviceType type = deviceTypeRepository.save(mapper.toModel(dto));
        return mapper.toDto(type);
    }

    @Override
    public DeviceTypeDto patchDeviceType(long id, NewDeviceTypeDto dto) {
        log.info("сохраняем измененный девайс тип {}, с id={}", dto, id);
        DeviceType type = mapper.toModel(dto);
        type.setId(id);
        deviceTypeRepository.save(type);
        return mapper.toDto(type);
    }

    @Override
    public void deleteDeviceType(long id) {
        log.info("удаляем из базы тип девайса с id={}", id);
        try {
            deviceTypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("тип девайса с id = " + id + "не найден");
        }
    }

    @Override
    public List<DeviceTypeDto> getAllDeviceType() {
        log.info("достаем из базы все типы девайсов");
        List<DeviceType> list = deviceTypeRepository.findAll();
        return mapper.toDtoList(list);
    }
}
