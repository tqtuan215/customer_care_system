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
@Table(name = "final_action_attribute")
public class FinalActionAttribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer finalActionAttributeId;
	private String attribute;
	
	// khong lưu vào DB
//	@OneToOne(mappedBy="finalActionAttribute")
//	private FinalActionAttribute finalActionAttribute;
}
