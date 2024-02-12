package evajan.mainService.devices.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@ToString
@Setter
@Getter
public class NewDeviceDto {
    @NotNull
    private DeviceTypeDto deviceTypeDto;
    private String brand;
    private String model;
    private String alternate;
}
