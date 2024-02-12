package evajan.mainService.consumers.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Value
@Builder
public class NewConsumerDto {
    @NotBlank
    @Size(min = 2, max = 20)
    String sureName;
    @NotBlank
    @Size(min = 2, max = 20)
    String name;
    @NotBlank
    @Size(min = 2, max = 20)
    String lastName;
    Set<PhoneNumberDto> phoneNumberDtos;
    String Comments;
}
