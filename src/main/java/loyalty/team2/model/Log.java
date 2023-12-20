package loyalty.team2.model;

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
	@ManyToOne
	@JoinColumn(name = "final_action_event_id")
	private FinalActionEvent finalActionEvent;
	@ManyToOne
	@JoinColumn(name = "final_action_id")
	private FinalAction finalAction;	
	@ManyToOne
	@JoinColumn(name = "conditon_id")
	private Condition condition;
	
	private String customerValue;
}
