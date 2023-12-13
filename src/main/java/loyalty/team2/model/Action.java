package loyalty.team2.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "action")
public class Action {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer actionId;
	private String name;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime storedAt;
	private boolean isDeleted;
	
}
