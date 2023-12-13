package loyalty.team2.model;

import jakarta.persistence.CascadeType;
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
@Table(name="node_1")
public class Node1{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer nodeId;
	private Integer headId;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logic_id", referencedColumnName = "logicId")
	private Logic logic;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condition_id", referencedColumnName = "conditionId")
	private Condition condition;
}
