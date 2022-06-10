package uz.pdp.index_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.index_market.entity.Consultation;

import java.util.UUID;

public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {
}