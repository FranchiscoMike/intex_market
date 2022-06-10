package uz.pdp.index_market.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Transactional
public class Order { // orderlar idsini long qilaman agar muammo bo'lsa yana o'zgartiraman
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String username;

    @CreationTimestamp
    private Timestamp createdAt;

    private String phone_number; // pattern like +998 90 974 12 28

    private String address;

    private boolean isDelivered = false;
}
