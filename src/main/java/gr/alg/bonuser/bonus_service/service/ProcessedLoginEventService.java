package gr.alg.bonuser.bonus_service.service;

import gr.alg.bonuser.bonus_service.entity.ProcessedLoginEventEntity;
import gr.alg.bonuser.bonus_service.repository.ProcessedLoginEventRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProcessedLoginEventService {
	private final ProcessedLoginEventRepository processedLoginEventRepository;

	public boolean eventExists(UUID eventId) {
		return processedLoginEventRepository.existsById(eventId);
	}

	public int countProcessedEvents(Long playerId) {
		return processedLoginEventRepository.countDistinctByPlayerId(playerId);
	}

	public void createNewEvent(UUID eventId, Long playerId) {
		var processedEvent = new ProcessedLoginEventEntity(eventId, playerId, LocalDateTime.now());
		processedLoginEventRepository.save(processedEvent);
	}
}