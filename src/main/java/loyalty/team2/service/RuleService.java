package loyalty.team2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.ActionCriteriaResult;
import loyalty.team2.model.Customer;
import loyalty.team2.model.CustomerAttribute;
import loyalty.team2.model.FinalAction;
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

	/**
	 * all customer will be checked with all rule
	 */
//	public void ruleForAll() {
//		List<Customer> customers = cusSv.getAllCustomer();
//		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR();
//
//		for (Customer customer : customers) {
//			List<CustomerAttribute> atts = cusAttSv.getAttribute(customer.getCustomerId()); // lay
//																							// attributes
//																							// cua 1 KH
//			for (ActionCriteriaResult ACR : ACRs) {
//				Integer ACRid = ACR.getActionCriteriaResultId();
//				System.out.println("ACR: " + ACRid);
//				List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
//																// ACR
//				if (nodes.isEmpty()) {
//					System.out.println("ACR have no condition");
//					break;
//				} else {
//					Node root = findRoot(nodes);
//					System.out.println("root: " + root.getNodeId());
//					if (isMatchGroup(atts, root, nodes))
//						System.out.println(
//								"=> customer " + customer.getCustomerId() + " thoa " + "rule " + root.getNodeId());
//					else
//						System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa " + "rule "
//								+ root.getNodeId());
//				}
//
//			}
//
//		}
//	}
//
//	public void ruleForAllRule(List<ActionCriteriaResult> ACRs) {
//		List<Customer> customers = cusSv.getAllCustomer();
//		for (Customer customer : customers) {
//			if (recommend(customer.getCustomerId()))
//				System.out.println("=> customer " + customer.getCustomerId() + " thoa");
//			else
//				System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa");
//		}
//	}
//
//	public void ruleForAll1Rule() {
//		List<Customer> customers = cusSv.getAllCustomer();
//		for (Customer customer : customers) {
//			if (recommend(customer.getCustomerId()))
//				System.out.println("=> customer " + customer.getCustomerId() + " thoa");
//			else
//				System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa");
//		}
//	}

//	public boolean recommend(Integer id) {
//		List<CustomerAttribute> atts = cusAttSv.getAttribute(id);
////		List<CustomerAttribute> atts = cusAttSv.getAttributeForOneCustomer(cusSv.getCustomerById(id));
//		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR();
//		for (ActionCriteriaResult ACR : ACRs) {
//			Integer ARCId = ACR.getActionCriteriaResultId();
//			return isMatchGroup(atts, findRoot(NodeSv.getNodesFlACR(ARCId)), NodeSv.getNodesFlACR(ARCId));
//		}
//		return false;
//	}
//
//	public List<FinalAction> recommend1(Integer id) {
//		List<FinalAction> FAs = new ArrayList<FinalAction>();
//		List<CustomerAttribute> atts = cusAttSv.getAttribute(id);
//		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR();
//		
//		for (ActionCriteriaResult ACR : ACRs) {
//			Integer ARCId = ACR.getActionCriteriaResultId();
//			Node root = findRoot(NodeSv.getNodesFlACR(ARCId));
//			if (isMatchGroup(atts, root, NodeSv.getNodesFlACR(ARCId))) {
//				FinalAction rs = new FinalAction();
//				Customer c = new Customer();
//				c.setCustomerId(id);
//				rs.setCustomer(c); // KH 1, 2
//				// rs.setCustomer();
//				rs.setAction(ACR.getActionCriteria().getAction());
//				FAs.add(rs);
//			} else {
//				System.out.println("khach hang " + id + " KHONG thoa rule" + root.getNodeId());
//			}
//		}
//		return FAs;
//
//	}

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
			String currentValue = "";
			for (CustomerAttribute item : atts) {
				if (item.getAttribute().getAttributeId() == currentNode.getCondition().getAttribute()
						.getAttributeId()) {
					currentValue = item.getValue();
					System.out.println("cr val: " + currentValue);
				}
			}
			Integer operator = currentNode.getCondition().getOperator().getOperatorId();
			String value = currentNode.getCondition().getValue();
			bl = evalCondition(currentValue, operator, value);
			System.out.println(currentValue + " " + currentNode.getCondition().getOperator().getSymbol() + " " + value
					+ "->" + bl);
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

	public boolean evalCondition(String currentValue, Integer operator, String value) {
		Boolean isTrue = false;
		switch (operator) {
		case 1: {
			if (Integer.parseInt(currentValue) > Integer.parseInt(value))
				isTrue = true;

			break;
		}
		case 2: {
			if (Integer.parseInt(currentValue) < Integer.parseInt(value))
				isTrue = true;

			break;
		}
		case 3: {
			if (currentValue.equalsIgnoreCase(value))
				isTrue = true;

			break;
		}
		}

		return isTrue;

	}

}
