package uz.pdp.index_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.index_market.entity.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    List<UserRole> findByActiveIsTrue();

    Optional<UserRole> findByName(String name);


}