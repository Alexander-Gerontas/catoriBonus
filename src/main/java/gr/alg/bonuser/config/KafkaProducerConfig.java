package gr.alg.bonuser.config;

import gr.alg.bonuser.common.kafka.dto.BonusEventDto;
import gr.alg.bonuser.common.kafka.dto.LoginEventDto;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
  private final KafkaProperties kafkaProperties;

  @Bean
  public ProducerFactory<String, BonusEventDto> bonusEventProducerFactory() {
    var configProps = getConfigProps();
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, BonusEventDto> bonusEventKafkaTemplate() {
    return new KafkaTemplate<>(bonusEventProducerFactory());
  }

  @Bean
  public ProducerFactory<String, LoginEventDto> loginEventProducerFactory() {
    var configProps = getConfigProps();
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, LoginEventDto> loginEventKafkaTemplate() {
    return new KafkaTemplate<>(loginEventProducerFactory());
  }

  private Map<String, Object> getConfigProps() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    return configProps;
  }
}
