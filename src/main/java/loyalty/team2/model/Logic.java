package loyalty.team2.model;

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
@Table(name="logic")
public class Logic {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer logicId;
	private String name;
	private String description;
}
