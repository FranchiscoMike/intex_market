package uz.pdp.index_market.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Transactional
public class Complecation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uz_name;

    private String ru_name;

    private boolean active;
}
