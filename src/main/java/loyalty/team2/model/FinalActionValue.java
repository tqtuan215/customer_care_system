package loyalty.team2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "final_action_value")
public class FinalActionValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer finalActionValueId;
	@ManyToOne	
	@JsonBackReference
	@JoinColumn(name = "final_action_id", referencedColumnName = "finalActionId")
	private FinalAction finalAction;
	@OneToOne
	@JoinColumn(name = "final_action_attribute_id")
	private FinalActionAttribute finalActionAttribute;
	private String value;

}
