package stm.carrier.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CarrierDto {
    @NotBlank
    String company;
    @Size(min=5, max = 11, message = "некорректный номер телефона")
    String phone;
}
