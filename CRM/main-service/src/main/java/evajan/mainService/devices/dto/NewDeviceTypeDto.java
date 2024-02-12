package evajan.mainService.devices.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@Setter
@Getter
public class NewDeviceTypeDto {
    @Size(max = 50)
    @NotBlank
    private String deviceType;
}
