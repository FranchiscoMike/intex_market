package uz.pdp.index_market.payload;

import lombok.*;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {
    @Size(min = 1,message = "uzbek name is required!")
    private String name_uz;

    @Size(min = 1,message = "russian name is required!")
    private String name_ru;
}
