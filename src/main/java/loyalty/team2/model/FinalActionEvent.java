package loyalty.team2.model;

import java.time.LocalDate;

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
@Table(name = "final_action_event")
public class FinalActionEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer finalActionEventId;
	@OneToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	@OneToOne
	@JoinColumn(name="action_id")
	private Action action;
	private String type;
	private LocalDate eventTime;
	private Integer created_by;
	
}
