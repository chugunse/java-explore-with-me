package evajan.mainService.consumers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullConsumerDto {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 20)
    private String sureName;
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
    @NotBlank
    @Size(min = 2, max = 20)
    private String lastName;
    private Set<PhoneNumberDto> phoneNumberDtos;
    private String Comments;
}
