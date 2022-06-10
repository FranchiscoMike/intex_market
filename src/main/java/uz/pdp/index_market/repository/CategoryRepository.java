package uz.pdp.index_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.index_market.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByActiveIsTrue();
}