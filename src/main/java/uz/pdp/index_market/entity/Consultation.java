package uz.pdp.index_market.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Transactional
public class Consultation {
    @Id
    private UUID id = UUID.randomUUID();

    private String username;

    private String phone_number; // should check pattern

    private boolean isChecked = false;
}
