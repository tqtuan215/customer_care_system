package loyalty.team2.model;

import java.util.List;

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
@Table(name = "final_action_detail")
public class FinalActionDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer finalActionDetailId;
	@OneToOne
	@JoinColumn(name="final_action_id", referencedColumnName = "finalActionId")
	private FinalAction finalAction;
	@OneToOne
	@JoinColumn(name="action_criteria_result_id", referencedColumnName = "actionCriteriaResultId")
	private List<ActionCriteriaResult> ACR;
//	private ActionCriteriaResult ACR;
}
