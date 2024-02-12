package stm.carrier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Carrier {
    private int id;
    private final String company;
    private final String phone;
}
