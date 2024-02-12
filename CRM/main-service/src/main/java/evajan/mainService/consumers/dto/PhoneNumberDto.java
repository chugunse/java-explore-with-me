package evajan.mainService.consumers.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class PhoneNumberDto {
    int id;
    @NotBlank String number;
}
