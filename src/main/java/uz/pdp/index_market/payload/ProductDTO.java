package uz.pdp.index_market.payload;

import lombok.*;
import uz.pdp.index_market.entity.Attachment;
import uz.pdp.index_market.entity.Category;
import uz.pdp.index_market.entity.Complecation;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {

    private Integer attachment_id;

    private UUID category_id;

    private String depth;

    private String size;

    private Double real_price;

    private Double discount_price;

    private String uz_frame;

    private String ru_frame;

    // bu komplekationlar bir xil bo'lishi kerak tekshiladi

    private List<Complecation> complecationList_uz;

    private List<Complecation> complecationList_ru;

}
