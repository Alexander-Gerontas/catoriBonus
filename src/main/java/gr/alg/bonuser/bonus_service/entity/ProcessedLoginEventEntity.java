package gr.alg.bonuser.bonus_service.entity;

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
@Table(name = "processed_login_events")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessedLoginEventEntity {
	@Id
	@Column(name = "event_id", updatable = false, nullable = false)
	private UUID eventId;

	@Column(name = "player_id", nullable = false)
	private Long playerId;

	@Column(name = "event_timestamp", nullable = false)
	private LocalDateTime eventTimestamp;
}