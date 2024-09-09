package gr.alg.bonuser.login_service.controller;

import gr.alg.bonuser.login_service.controller.dto.LoginDto;
import gr.alg.bonuser.login_service.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/login")
public class LoginController {
	private final LoginService loginService;

	@PostMapping()
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
		loginService.loginUser(loginDto);
		return ResponseEntity.ok("Player logged in, event sent.");
	}
}