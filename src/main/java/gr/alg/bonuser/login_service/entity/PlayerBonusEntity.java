package gr.alg.bonuser.login_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "player_bonus")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerBonusEntity {
	@Id
	@Column(name = "player_id", updatable = false, nullable = false)
	private Long playerId;

	@Column(name = "total_bonus", nullable = false)
	private Long totalBonus;

	public void addBonus(long bonus) {
		this.totalBonus += bonus;
	}
}