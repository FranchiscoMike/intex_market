package uz.pdp.index_market.payload;

import lombok.*;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ComplecationDTO {

    @Size(min = 3,message = "name_uz is required")
    private String uz_name;

    @Size(min = 3,message = "name_ru is required")
    private String ru_name;
}
