package gr.alg.bonuser.bonus_service.kafka;

import gr.alg.bonuser.common.kafka.dto.BonusEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BonusEventProducer {
	private final KafkaTemplate<String, BonusEventDto> kafkaTemplate;

	@Value("${spring.kafka.topics.player-bonus-events-topic}")
	protected String PLAYER_BONUS_TOPIC;

	public void sendBonusEvent(BonusEventDto bonusEventDto) {
		kafkaTemplate.send(PLAYER_BONUS_TOPIC, bonusEventDto);
	}
}
