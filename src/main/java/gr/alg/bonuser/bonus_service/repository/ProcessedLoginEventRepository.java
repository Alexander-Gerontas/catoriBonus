package gr.alg.bonuser.bonus_service.repository;

import gr.alg.bonuser.bonus_service.entity.ProcessedLoginEventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedLoginEventRepository extends JpaRepository<ProcessedLoginEventEntity, UUID> {
  int countDistinctByPlayerId(Long playerId);
}