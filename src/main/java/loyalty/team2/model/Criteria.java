package loyalty.team2.model;

import java.time.LocalDate;

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
@Table(name = "criteria")
public class Criteria {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer criteriaId;
	private String name;
	private LocalDate createdAt;
	private Boolean isDeleted;
}
