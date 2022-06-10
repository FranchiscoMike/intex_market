package uz.pdp.index_market.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Product {
    @Id
    private UUID id = UUID.randomUUID();

    @OneToOne
    private Attachment attachment;

    @ManyToOne
    private Category category;

    private String depth;

    private String size;

    private Double real_price;

    private Double discount_price;

    private String uz_frame;

    private String ru_frame;

    // bu komplekationlar bir xil bo'lishi kerak tekshiladi


    @ManyToMany
    private List<Complecation> complecationList_uz;

    @ManyToMany
    private List<Complecation> complecationList_ru;

}
