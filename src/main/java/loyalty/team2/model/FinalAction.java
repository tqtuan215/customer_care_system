package loyalty.team2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	private Integer finalActionId;
	@OneToOne
	@JoinColumn(name = "action_id", referencedColumnName = "actionId")
	private Action action;
	@OneToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "customerId")
	private Customer customer;
	private Integer lastId;
	
}
