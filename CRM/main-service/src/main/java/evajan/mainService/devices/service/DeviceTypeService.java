package evajan.mainService.devices.service;

import evajan.mainService.devices.dto.DeviceTypeDto;
import evajan.mainService.devices.dto.NewDeviceTypeDto;

import java.util.List;

public interface DeviceTypeService {

    DeviceTypeDto addNewDeviceType(NewDeviceTypeDto dto);

    DeviceTypeDto patchDeviceType(long id, NewDeviceTypeDto dto);

    void deleteDeviceType(long id);

    List<DeviceTypeDto> getAllDeviceType();
}
