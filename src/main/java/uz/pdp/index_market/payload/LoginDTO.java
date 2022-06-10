package uz.pdp.index_market.payload;

import lombok.*;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {

    @Size(min = 3,message = "Your username should be more than 3 characters")
    private String username;

    @Size(min = 6,message = "Please enter code in valid type")
    private String password;

}
