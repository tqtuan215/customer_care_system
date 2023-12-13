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
@Table(name = "result")
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)	
	private Integer ResultId;
	private String result;
	private LocalDate createdAt;
	
}
