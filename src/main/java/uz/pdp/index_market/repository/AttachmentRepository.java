package uz.pdp.index_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.index_market.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}