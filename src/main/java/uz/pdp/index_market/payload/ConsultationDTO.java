package uz.pdp.index_market.payload;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConsultationDTO {

    @Size(min = 3,message = "more than 3 characteristics is required")
    private String username;

    @Pattern(regexp = "[+998][0-9]{9}")
    private String phone_number; // pattern like +998 90 974 12 28


}
