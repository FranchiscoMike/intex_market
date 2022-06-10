package uz.pdp.index_market.payload;

import lombok.*;
import uz.pdp.index_market.entity.Product;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {

    private UUID product_id;

    @Size(min = 3,message ="more than 3 please" )
    private String username;

    @Pattern(regexp = "[+9989][0-9]{8}",message = "please enter in valid type")
    private String phone_number; // pattern like +998 90 974 12 28

    private String address;
}
