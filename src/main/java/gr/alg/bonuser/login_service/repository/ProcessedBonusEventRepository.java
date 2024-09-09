package gr.alg.bonuser.login_service.repository;

import gr.alg.bonuser.login_service.entity.ProcessedBonusEventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedBonusEventRepository extends
    JpaRepository<ProcessedBonusEventEntity, UUID> {
}