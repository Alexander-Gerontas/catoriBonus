package gr.alg.bonuser.login_service.kafka;

import gr.alg.bonuser.common.kafka.dto.BonusEventDto;
import gr.alg.bonuser.login_service.service.BonusService;
import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class BonusEventConsumer {
	private final BonusService bonusService;
	private CountDownLatch latch = new CountDownLatch(1);

	@KafkaListener(topics = "${spring.kafka.topics.player-bonus-events-topic}", containerFactory = "bonusServiceKafkaListenerContainerFactory")
	public void listenBonusUpdates(BonusEventDto bonusEventDto) {
		log.info("Received login event {}", bonusEventDto.getPlayerId());

		bonusService.updatePlayerBonus(bonusEventDto);

		latch.countDown();
	}

	public void resetLatch(int tasks) {
		latch = new CountDownLatch(tasks);
	}
}
