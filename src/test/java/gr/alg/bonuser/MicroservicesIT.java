package gr.alg.bonuser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gr.alg.bonuser.bonus_service.kafka.LoginEventConsumer;
import gr.alg.bonuser.login_service.repository.BonusRepository;
import gr.alg.bonuser.bonus_service.repository.ProcessedLoginEventRepository;
import gr.alg.bonuser.configuration.BaseIntegrationTest;
import gr.alg.bonuser.login_service.controller.dto.LoginDto;
import gr.alg.bonuser.login_service.kafka.BonusEventConsumer;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MicroservicesIT extends BaseIntegrationTest {

  @Value("${bonuses.min-bonus}")
  private long minBonus;

  @Autowired private BonusRepository bonusRepository;
  @Autowired private LoginEventConsumer loginEventConsumer;
  @Autowired private BonusEventConsumer bonusEventConsumer;

  @Autowired private ProcessedLoginEventRepository processedLoginEventRepository;

  @BeforeAll
  public void initialize() {
  }

  @BeforeEach
  public void setUp() {
    wipeDb();
  }

  @AfterEach
  public void tearDown() {
  }

  @AfterAll
  public void cleanUp() {
  }

  public void wipeDb() {
    bonusRepository.deleteAll();
    bonusRepository.flush();

    processedLoginEventRepository.deleteAll();
    processedLoginEventRepository.flush();
  }

  @Test
  @SneakyThrows
  void successfulBonusPointAward() {
    // Add a small sleep to make sure consumer is ready
    Thread.sleep(3000);

    var loginDto = new LoginDto(1L);

    // user logs in for the first time
    loginUser(loginDto, status().isOk());

    // assert the events are consumed
    boolean loginEventConsumed = loginEventConsumer.getLatch().await(10, TimeUnit.SECONDS);
    Assertions.assertTrue(loginEventConsumed);

    boolean bonusEventConsumed = bonusEventConsumer.getLatch().await(10, TimeUnit.SECONDS);
    Assertions.assertTrue(bonusEventConsumed);

    // assert player is awarded
    var bonusEntityList = bonusRepository.findAll();
    Assertions.assertEquals(1, bonusEntityList.size());

    var bonusEntity = bonusEntityList.getFirst();
    Assertions.assertEquals(minBonus, bonusEntity.getTotalBonus());
  }

  @Test
  @SneakyThrows
  void userGetsAwardedForMoreThanOneLogin() {
    // Add a small sleep to make sure consumer is ready
    Thread.sleep(3000);

    var loginDto = new LoginDto(1L);

    // user logs in for the first time
    loginUser(loginDto, status().isOk());

    // assert the events are consumed
    boolean loginEventConsumed = loginEventConsumer.getLatch().await(10, TimeUnit.SECONDS);
    Assertions.assertTrue(loginEventConsumed);

    boolean bonusEventConsumed = bonusEventConsumer.getLatch().await(10, TimeUnit.SECONDS);
    Assertions.assertTrue(bonusEventConsumed);

    // assert player is awarded
    var bonusEntityList = bonusRepository.findAll();
    Assertions.assertEquals(1, bonusEntityList.size());

    var bonusEntity = bonusEntityList.getFirst();
    Assertions.assertEquals(minBonus, bonusEntity.getTotalBonus());

    // user logs in a second time
    loginUser(loginDto, status().isOk());

    // reset latches
    loginEventConsumer.resetLatch(1);
    bonusEventConsumer.resetLatch(1);

    // assert the events are consumed
    loginEventConsumed = loginEventConsumer.getLatch().await(10, TimeUnit.SECONDS);
    Assertions.assertTrue(loginEventConsumed);

    bonusEventConsumed = bonusEventConsumer.getLatch().await(10, TimeUnit.SECONDS);
    Assertions.assertTrue(bonusEventConsumed);

    // assert player is awarded again
    bonusEntity = bonusRepository.findAll().getFirst();
    Assertions.assertEquals(minBonus + (minBonus * 2), bonusEntity.getTotalBonus());
  }

  @SneakyThrows
  public static void loginUser(LoginDto loginDto, ResultMatcher status) {
    staticMockMvc
        .perform(
            post("/api/v1/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(staticObjectMapper.writeValueAsString(loginDto))
        )
        .andExpect(status).andReturn();
  }
}