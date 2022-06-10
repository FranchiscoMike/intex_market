package uz.pdp.index_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.index_market.entity.Complecation;

import java.util.List;

public interface ComplecationRepository extends JpaRepository<Complecation, Integer> {
    List<Complecation> findByActiveIsTrue();
}