package gr.alg.bonuser.bonus_service.service;

import gr.alg.bonuser.bonus_service.kafka.BonusEventProducer;
import gr.alg.bonuser.common.kafka.dto.BonusEventDto;
import gr.alg.bonuser.common.kafka.dto.LoginEventDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BonusCalculationService {
	private final BonusEventProducer bonusEventProducer;
	private final ProcessedLoginEventService processedLoginEventService;

	@Value("${bonuses.min-bonus}")
	private long minBonus;

	public void calculateAndUpdateBonus(LoginEventDto loginEventDto) {
		Long playerId = loginEventDto.getPlayerId();

		// Check if event has already been processed
		if (processedLoginEventService.eventExists(loginEventDto.getEventId())) {
			// Event already processed, skip to avoid duplication
			log.info("Login event already processed for player: " + playerId);
			return;
		}

		// get player login times
		var loginTimes = processedLoginEventService.countProcessedEvents(playerId);

		// player gets bonus points based on his login frequency.
		var bonusPoints = loginTimes > 0
				? (loginTimes + 1) * minBonus
				: minBonus;

		// Save the event to mark it as processed
		processedLoginEventService.createNewEvent(loginEventDto.getEventId(), playerId);

		// Send bonus update event
		var dto = new BonusEventDto(playerId, UUID.randomUUID(), bonusPoints);
		bonusEventProducer.sendBonusEvent(dto);
	}
}