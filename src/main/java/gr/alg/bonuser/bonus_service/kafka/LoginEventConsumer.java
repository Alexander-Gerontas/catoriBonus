package gr.alg.bonuser.bonus_service.kafka;

import gr.alg.bonuser.common.kafka.dto.LoginEventDto;
import gr.alg.bonuser.bonus_service.service.BonusCalculationService;
import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class LoginEventConsumer {
	private final BonusCalculationService bonusCalculationService;
	private CountDownLatch latch = new CountDownLatch(1);

	@KafkaListener(topics = "${spring.kafka.topics.player-login-events-topic}", containerFactory = "loginServiceKafkaListenerContainerFactory")
	public void listenForLoginEvents(LoginEventDto loginEventDto) {
		log.info("Received login event {}", loginEventDto.getPlayerId());

		bonusCalculationService.calculateAndUpdateBonus(loginEventDto);

		latch.countDown();
	}

	public void resetLatch(int tasks) {
		latch = new CountDownLatch(tasks);
	}
}