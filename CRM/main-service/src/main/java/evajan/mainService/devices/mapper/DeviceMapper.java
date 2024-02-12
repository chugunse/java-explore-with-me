package evajan.mainService.devices.mapper;

import evajan.mainService.devices.dto.DeviceDto;
import evajan.mainService.devices.dto.NewDeviceDto;
import evajan.mainService.devices.model.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DeviceTypeMapper.class)
public interface DeviceMapper {
    @Mapping(source = "deviceTypeDto", target = "deviceType")
    Device toModel(NewDeviceDto dto);
    @Mapping(source = "deviceType", target = "deviceTypeDto")
    DeviceDto toDeviceDto(Device device);
}
