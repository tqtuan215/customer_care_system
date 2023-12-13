package loyalty.team2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "condition")
public class Condition {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer conditionId;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attribute_id", referencedColumnName = "attributeId")	
	private Attribute attribute;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "operator_id", referencedColumnName = "operatorId")	
	private Operator operator;
	private Integer value; 
}
