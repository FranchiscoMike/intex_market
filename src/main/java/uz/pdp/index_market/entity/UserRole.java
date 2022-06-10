package uz.pdp.index_market.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class UserRole {
    @Id
    private UUID id = UUID.randomUUID();

    private String name; // userga tegishli bo'lgan role nomi!

    @CreationTimestamp
    private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp last_update;

    private boolean active = true;


}
