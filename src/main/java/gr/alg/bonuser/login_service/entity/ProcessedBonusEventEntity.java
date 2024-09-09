package gr.alg.bonuser.login_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "processed_bonus_events")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessedBonusEventEntity {
	@Id
	@Column(name = "event_id", updatable = false, nullable = false)
	private UUID eventId;

	@Column(name = "event_timestamp", nullable = false)
	private LocalDateTime eventTimestamp;
}