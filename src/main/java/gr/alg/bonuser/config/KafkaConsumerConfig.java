package gr.alg.bonuser.config;

import gr.alg.bonuser.common.kafka.dto.BonusEventDto;
import gr.alg.bonuser.common.kafka.dto.LoginEventDto;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

  private final KafkaProperties kafkaProperties;

  @Bean
  public ConsumerFactory<String, BonusEventDto> bonusServiceConsumerFactory() {
    Map<String, Object> props = getDefaultConfigProps();

    props.put(ConsumerConfig.GROUP_ID_CONFIG, "bonus-group");
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, BonusEventDto.class);

    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, BonusEventDto> bonusServiceKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, BonusEventDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(bonusServiceConsumerFactory());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, LoginEventDto> loginServiceConsumerFactory() {
    Map<String, Object> props = getDefaultConfigProps();

    props.put(ConsumerConfig.GROUP_ID_CONFIG, "login-group");
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, LoginEventDto.class);

    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, LoginEventDto> loginServiceKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, LoginEventDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(loginServiceConsumerFactory());
    return factory;
  }

  private Map<String, Object> getDefaultConfigProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    return props;
  }
}
