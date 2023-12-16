package loyalty.team2.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerActionWhy {
	private Action action;
	private List<Node> nodes;
	private List<CustomerAttribute> CA;
	private Customer customer;
}
