package loyalty.team2.model;

import jakarta.persistence.CascadeType;
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
@Table(name = "log")
public class Log {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer log_id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "final_action_event_id")
	private FinalActionEvent finalActionEvent;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "final_action_id")
	private FinalAction finalAction;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "final_action_value_id")
	private FinalActionValue finalActionValue;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "condition_id")
	private Condition condition;
	
	private String customerValue;
}
