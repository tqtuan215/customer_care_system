package loyalty.team2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "final_action")
public class FinalAction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer finalActionId;
	@OneToOne
	@JoinColumn(name = "action_id")
	private Action action;
	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	private Integer lastId;
	
	@OneToMany(mappedBy="finalAction", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<FinalActionValue> finalActionValue;
}
