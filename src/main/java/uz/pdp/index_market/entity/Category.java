package uz.pdp.index_market.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Category {

    @Id
    private UUID id = UUID.randomUUID();

    private String name_uz;

    private String name_ru;

    private boolean active = true;



}
