package uz.pdp.index_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.index_market.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}