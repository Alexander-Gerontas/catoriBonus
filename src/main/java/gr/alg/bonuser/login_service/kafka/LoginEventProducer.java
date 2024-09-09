package gr.alg.bonuser.login_service.kafka;

import gr.alg.bonuser.common.kafka.dto.LoginEventDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginEventProducer {
	private final KafkaTemplate<String, LoginEventDto> loginEventKafkaTemplate;

	@Value("${spring.kafka.topics.player-login-events-topic}")
	protected String LOGIN_TOPIC;

	public void sendLoginEvent(Long playerId) {
		var dto = new LoginEventDto(playerId, UUID.randomUUID(), LocalDateTime.now());

		loginEventKafkaTemplate.send(LOGIN_TOPIC, dto);
	}
}