package gr.alg.bonuser.config;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Application configuration.
 */
@Configuration
public class AppConfig {

  /**
   * Bean for object mapper.
   *
   * @return The object mapper.
   */
  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
    return objectMapper;
  }
}
