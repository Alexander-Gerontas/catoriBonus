package gr.alg.bonuser.login_service.service;

import gr.alg.bonuser.login_service.repository.BonusRepository;
import gr.alg.bonuser.common.kafka.dto.BonusEventDto;
import gr.alg.bonuser.login_service.entity.PlayerBonusEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BonusService {
	private final BonusRepository bonusRepository;
	private final ProcessedBonusEventService processedBonusEventService;

	public void updatePlayerBonus(BonusEventDto bonusEventDto) {
		Long playerId = bonusEventDto.getPlayerId();

		// Check if event has already been processed
		if (processedBonusEventService.eventExists(bonusEventDto.getEventId())) {
			// Event already processed, skip to avoid duplication
			log.info("Bonus event already processed for player: " + playerId);
			return;
		}

		// Retrieve or create new player bonus entity
    PlayerBonusEntity playerBonus = bonusRepository.findById(playerId)
				.orElse(new PlayerBonusEntity(playerId, 0L));

		// update the bonus and save db
		playerBonus.addBonus(bonusEventDto.getBonusPoints());
		bonusRepository.save(playerBonus);

		// Save the event to mark it as processed
		processedBonusEventService.createNewEvent(bonusEventDto.getEventId());
	}
}