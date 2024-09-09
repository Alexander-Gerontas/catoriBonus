package gr.alg.bonuser.login_service.service;

import gr.alg.bonuser.login_service.controller.dto.LoginDto;
import gr.alg.bonuser.login_service.kafka.LoginEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
	private final LoginEventProducer loginEventProducer;

	public void loginUser(LoginDto loginDto) {
		// emit login event
		loginEventProducer.sendLoginEvent(loginDto.getPlayerId());

		// proceeds with user log in
	}
}