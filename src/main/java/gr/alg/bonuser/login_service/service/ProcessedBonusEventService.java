package gr.alg.bonuser.login_service.service;

import gr.alg.bonuser.login_service.entity.ProcessedBonusEventEntity;
import gr.alg.bonuser.login_service.repository.ProcessedBonusEventRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProcessedBonusEventService {
	private final ProcessedBonusEventRepository processedBonusEventRepository;

	public boolean eventExists(UUID eventId) {
		return processedBonusEventRepository.existsById(eventId);
	}

	public void createNewEvent(UUID eventId) {
		var processedEvent = new ProcessedBonusEventEntity(eventId, LocalDateTime.now());
		processedBonusEventRepository.save(processedEvent);
	}
}