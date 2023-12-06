package loyalty.team2.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//import jakarta.annotation.Generated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="purpose")
public class Purpose {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;
	private String purpose;
	
}
