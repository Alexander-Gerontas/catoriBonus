package gr.alg.bonuser.login_service.repository;

import gr.alg.bonuser.login_service.entity.PlayerBonusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<PlayerBonusEntity, Long> {
}