package gr.alg.bonuser.common.kafka.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LoginEventDto implements Serializable {
  private long playerId;
  private UUID eventId;
  private LocalDateTime eventTimestamp;
}
