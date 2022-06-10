package uz.pdp.index_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.index_market.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}