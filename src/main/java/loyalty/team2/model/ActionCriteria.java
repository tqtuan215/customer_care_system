package loyalty.team2.model;

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
@Table(name = "action_criteria")
public class ActionCriteria {
	@Id
	private Integer actionCriteriaId;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "action_id", referencedColumnName = "actionId")
	private Action action;	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "criteria_id", referencedColumnName = "criteriaId")
	private Criteria criteria;
	
	
}
