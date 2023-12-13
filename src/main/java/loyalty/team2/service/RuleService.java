package loyalty.team2.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.ActionCriteriaResult;
import loyalty.team2.model.Customer;
import loyalty.team2.model.CustomerAttribute;
import loyalty.team2.model.Node;

@Service
public class RuleService {

	@Autowired
	private NodeService NodeSv;
	@Autowired
	private CustomerAttributeService cusAttSv;
	@Autowired
	private CustomerService cusSv;
	@Autowired
	private ActionCriteriaResultService ACRSv;

	public void ruleForAll() {
		List<Customer> customers = cusSv.getAllCustomer();
		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR();

		for (Customer customer : customers) {
			for (ActionCriteriaResult ACR : ACRs) {
				List<CustomerAttribute> atts = cusAttSv.getAttributeForOneCustomer(customer.getCustomerId());
				List<Node> nodes = NodeSv.getNodesFlACR(ACR.getActionCriteriaResultId());
				Node root = findRoot(nodes);
				if (isMatchGroup(atts, root, nodes))
					System.out.println("=> customer " + customer.getCustomerId() + " thoa " + "rule " + root);
				else
					System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa" + "rule " + root);
			}

		}
	}

	public void ruleForAllRule(List<ActionCriteriaResult> ACRs) {
		List<Customer> customers = cusSv.getAllCustomer();
		for (Customer customer : customers) {
			if (recommend(customer.getCustomerId()))
				System.out.println("=> customer " + customer.getCustomerId() + " thoa");
			else
				System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa");
		}
	}

	public void ruleForAll1Rule() {
		List<Customer> customers = cusSv.getAllCustomer();
		for (Customer customer : customers) {
			if (recommend(customer.getCustomerId()))
				System.out.println("=> customer " + customer.getCustomerId() + " thoa");
			else
				System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa");
		}
	}

	public boolean recommend(Integer id) {
		List<CustomerAttribute> atts = cusAttSv.getAttributeForOneCustomer(id);
//		List<CustomerAttribute> atts = cusAttSv.getAttributeForOneCustomer(cusSv.getCustomerById(id));
		return isMatchGroup(atts, findRoot(NodeSv.getNodesFlACR(1)), NodeSv.getNodesFlACR(1));
	}

	public boolean recommend() {
		List<CustomerAttribute> atts = cusAttSv.getAllCustomerAttribute();
		return isMatchGroup(atts, findRoot(NodeSv.getNodes()), NodeSv.getNodes());
	}

	public boolean recommendForACR(List<CustomerAttribute> atts, List<Node> nodes, Integer ACRId) {
		nodes = NodeSv.getNodesFlACR(ACRId);
		return isMatchGroup(atts, findRoot(nodes), nodes);
	}

	public boolean isMatchGroup(List<CustomerAttribute> atts, Node currentNode, List<Node> nodes) {
		// check condition node
		System.out.println("-----" + currentNode);
		if (currentNode.getCondition() != null) {
			boolean bl = false;
			System.out.println("condition: " + currentNode);
			Integer currentValue = 0;
			for (CustomerAttribute item : atts) {
				if (item.getAttribute().getAttributeId() == currentNode.getCondition().getAttribute()
						.getAttributeId()) {
					currentValue = Integer.parseInt(item.getValue());
					System.out.println("cr val: " + currentValue);
				}
			}
			Integer operator = currentNode.getCondition().getOperator().getOperatorId();
			Integer value = currentNode.getCondition().getValue();
			bl = evalCondition(currentValue, operator, value);
			System.out.println(
					currentValue + " " + currentNode.getCondition().getOperator().getName() + " " + value + "->" + bl);
			return bl;
		} else {
			boolean bl = true;
			// check logic node

			System.out.println("logic: " + currentNode);
			List<Node> children = findChildren(currentNode, nodes);

			for (Node x : children) {
				System.out.println("children." + x);
			}

			if (currentNode.getLogic().getLogicId() == 1) {
				bl = children.stream().allMatch(child -> isMatchGroup(atts, child, nodes));
				System.out.println("rs logic and node." + currentNode.getNodeId() + " - " + bl);

			} else if (currentNode.getLogic().getLogicId() == 2) {
				bl = children.stream().anyMatch(child -> isMatchGroup(atts, child, nodes));
				System.out.println("rs logic and node." + currentNode.getNodeId() + " - " + bl);

			}
			return bl;
		}

	}

	public static Node findRoot(List<Node> groups) {
		return groups.stream().filter(x -> x.getHeadId() == null).findFirst().orElse(null);
	}

	public List<Node> findChildren(Node node, List<Node> nodes) {
		return nodes.stream().filter(x -> x.getHeadId() == node.getNodeId()).collect(Collectors.toList());
	}

	public boolean evalCondition(Integer currentValue, Integer operator, Integer value) {
		Boolean isTrue = false;
		switch (operator) {
		case 1: {
			if (currentValue > value)
				isTrue = true;

			break;
		}
		case 2: {
			if (currentValue < value)
				isTrue = true;

			break;
		}
		case 3: {
			if (currentValue == value)
				isTrue = true;

			break;
		}
		}

		return isTrue;

	}

}
