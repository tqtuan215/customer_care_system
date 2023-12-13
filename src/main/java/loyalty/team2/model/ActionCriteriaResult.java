package loyalty.team2.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
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
@Table(name = "action_criteria_result")
public class ActionCriteriaResult {
	@Id
	private Integer actionCriteriaResultId;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "action_criteria_id", referencedColumnName = "actionCriteriaId")
	private ActionCriteria actionCriteria;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id", referencedColumnName = "resultId")
	private Result result;
	
	
}
