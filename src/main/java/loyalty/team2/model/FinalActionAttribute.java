package loyalty.team2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
//	private String attribute;
	
	@ManyToOne
	@JoinColumn(name="attribute", referencedColumnName = "name", unique = true)	
	private Criteria criteria;
	
	// khong lưu vào DB
//	@OneToOne(mappedBy="finalActionAttribute")
//	private FinalActionAttribute finalActionAttribute;
}
