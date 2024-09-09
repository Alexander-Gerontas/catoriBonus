package gr.alg.bonuser.configuration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@EmbeddedKafka(partitions = 1, topics = {"player-login-events", "player-bonus-updates"})
public class BaseIntegrationTest {
  @Autowired public ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;

  public static MockMvc staticMockMvc;
	public static ObjectMapper staticObjectMapper;

  private static final ConfiguredPostgresContainer postgres;

  @PostConstruct
  public void init() {
    staticMockMvc = mockMvc;
    staticObjectMapper = objectMapper;
  }

  static {
    postgres = ConfiguredPostgresContainer.getInstance();
    postgres.start();
  }
}
