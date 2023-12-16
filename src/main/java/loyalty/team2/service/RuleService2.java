package loyalty.team2.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.ActionCriteriaResult;
import loyalty.team2.model.Customer;
import loyalty.team2.model.CustomerActionWhy;
import loyalty.team2.model.CustomerAttribute;
import loyalty.team2.model.FinalAction;
import loyalty.team2.model.FinalActionDetail;
import loyalty.team2.model.Node;

@Service
public class RuleService2 {

	@Autowired
	private NodeService NodeSv;
	@Autowired
	private CustomerAttributeService cusAttSv;
	@Autowired
	private CustomerService cusSv;
	@Autowired
	private ActionCriteriaResultService ACRSv;
	@Autowired
	private FinalActionService finalAcSv;

	public List<FinalActionDetail> finalActionDetail() {
		List<Customer> customers = cusSv.getAllCustomer();
		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR();
		List<FinalAction> finalActionList = new ArrayList<FinalAction>();
		List<FinalActionDetail> list = new ArrayList<FinalActionDetail>();
		List<ActionCriteriaResult> ACRList = new ArrayList<ActionCriteriaResult>();
		int count = 1;

		for (Customer customer : customers) // for all customer
		{
			if (count > 5)
				break;
			if (customer.getCustomerId() == count) {
				List<CustomerAttribute> atts = cusAttSv.getAttribute(customer.getCustomerId()); // lay
																								// attributes
																								// cua 1 KH
				for (ActionCriteriaResult ACR : ACRs) {
					int id = ACR.getActionCriteria().getCriteria().getCriteriaId();
					if (id == 1) { // tieu chi nhan
						System.out.println(ACR.getActionCriteria().getCriteria().getName());
						Integer ACRid = ACR.getActionCriteriaResultId();
						System.out.println("ACR: " + ACRid);
						List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
																		// ACR
						if (nodes.isEmpty()) {
							System.out.println("ACR have no condition");

						} else {
							Node root = findRoot(nodes);
							System.out.println("root: " + root.getNodeId());
							if (isMatchGroup(atts, root, nodes)) {
								FinalAction rs = new FinalAction();
								rs.setCustomer(customer);
								rs.setAction(ACR.getActionCriteria().getAction());
								finalActionList.add(rs);
								System.out.println("=> customer " + customer.getCustomerId() + " CO thoa " + "rule "
										+ root.getNodeId());

							} else {
								System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa " + "rule "
										+ root.getNodeId());
								break;
							}
						}
					}
				}
			}
			count = count + 1;
		}
		// sau khi nhan duoc hanh dong thi xet den tieu chi khac
		Set<Customer> customerList = new TreeSet<>(Comparator.comparing(Customer::getCustomerId));
		for (FinalAction finalAction : finalActionList) {
			customerList.add(finalAction.getCustomer());
		}
		for (Customer customer : customerList) // for all customer
		{
			{
				List<CustomerAttribute> atts = cusAttSv.getAttribute(customer.getCustomerId()); // lay
																								// attributes
																								// cua 1 KH
				for (ActionCriteriaResult ACR : ACRs) {
					int id = ACR.getActionCriteria().getCriteria().getCriteriaId();
					if (id != 1)
						if (id == 2) { // tieu chi thoi gian

							System.out.println(ACR.getActionCriteria().getCriteria().getName());
							Integer ACRid = ACR.getActionCriteriaResultId();
							System.out.println("ACR: " + ACRid);
							List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
																			// ACR
							if (nodes.isEmpty()) {
								System.out.println("ACR have no condition");

							} else {
								Node root = findRoot(nodes);
								LocalDate DOB = findDOB(atts);
								System.out.println("root: " + root.getNodeId());
								if (isMatchGroup(atts, root, nodes) && DOB != null) {

									System.out.println("cutoemer " + customer.getCustomerId());
									FinalAction rs = new FinalAction();
									rs.setCustomer(customer);
									rs.setAction(ACR.getActionCriteria().getAction());

									// tim ngay sinh

									String bd = String.valueOf((int) DOB.getDayOfMonth()) + "-" + DOB.getMonthValue();

									FinalActionDetail finalActionDetail = new FinalActionDetail();
									String result = ACR.getResult().getResult();
									System.out.println("kq: " + result);

									ACR.getResult().setResult(result + bd);

									ACRList.add(ACR); // them ACR vao List ACR trong detail
									finalActionDetail.setACR(ACRList); // them ACR vao List ACR trong detail
//									finalActionDetail.setACR(ACR); //add 1 ACR vao 1 FAD 
									finalActionDetail.setFinalAction(rs);
									System.out.println(customer.getCustomerId() + ". final action: " + rs);
//									list.add(finalActionDetail); // them vao FAD
									list.add(finalActionDetail);

								}

							}
						} else {
							System.out.println(ACR.getActionCriteria().getCriteria().getName());
							Integer ACRid = ACR.getActionCriteriaResultId();
							System.out.println("ACR: " + ACRid);
							List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
																			// ACR
							if (nodes.isEmpty()) {
								System.out.println("ACR have no condition");

							} else {
								Node root = findRoot(nodes);
								System.out.println("root: " + root.getNodeId());
								if (isMatchGroup(atts, root, nodes)) {
									FinalActionDetail finalActionDetail = new FinalActionDetail();
									System.out.println("kq: " + ACR.getResult().getResult());

									FinalAction rs = new FinalAction(); // action hanh dong gi
									rs.setCustomer(customer);
									rs.setAction(ACR.getActionCriteria().getAction());
									ACRList.add(ACR); // them vao list ACR
									finalActionDetail.setACR(ACRList);
//									finalActionDetail.setACR(ACR); //old code
									finalActionDetail.setFinalAction(rs);
									list.add(finalActionDetail);
								}

							}
						}
				}
			}

		}
		// if 1,2
		finalAcSv.saveAllFinalAction(finalActionList); // luu KH nhan duoc hanh dong tho gi

		return list;
	}

	public List<CustomerActionWhy> CusAcCon() {
		List<Customer> customers = cusSv.getAllCustomer();
		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR();
		List<FinalAction> finalActionList = new ArrayList<FinalAction>();
		List<CustomerActionWhy> CAWs = new ArrayList<CustomerActionWhy>();
		int count = 1;
		for (Customer customer : customers) // for all customer
		{
			if (count > 5)
				break;
			if (customer.getCustomerId() == count) {
				List<CustomerAttribute> atts = cusAttSv.getAttribute(customer.getCustomerId()); // lay
																								// attributes
																								// cua 1 KH
				for (ActionCriteriaResult ACR : ACRs) {
					Integer ACRid = ACR.getActionCriteriaResultId();
					System.out.println("ACR: " + ACRid);
					List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
																	// ACR
					if (nodes.isEmpty()) {
						System.out.println("ACR have no condition");

					} else {
						Node root = findRoot(nodes);
						System.out.println("root: " + root.getNodeId());
						if (isMatchGroup(atts, root, nodes)) {
							FinalAction rs = new FinalAction();
							rs.setCustomer(customer); // KH 1, 2
							// rs.setCustomer();
							rs.setAction(ACR.getActionCriteria().getAction());
							finalActionList.add(rs);

							// why
							CustomerActionWhy CAW = new CustomerActionWhy();
							CAW.setCustomer(customer);
							CAW.setAction(rs.getAction());
							CAW.setNodes(nodes);
							CAW.setCA(atts);
							System.out.println("=> customer " + customer.getCustomerId() + " CO thoa " + "rule "
									+ root.getNodeId());
							CAWs.add(CAW);
						} else
							System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa " + "rule "
									+ root.getNodeId());

					}
				}
			}
			count++;
		} // if 1,2
		finalAcSv.saveAllFinalAction(finalActionList);
		return CAWs;

	}

	public List<FinalAction> ruleForAll() {
		List<Customer> customers = cusSv.getAllCustomer();
		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR();
		List<FinalAction> finalActionList = new ArrayList<FinalAction>();

		int count = 1;
		for (Customer customer : customers) // for all customer
		{
			if (count > 5)
				break;
			if (customer.getCustomerId() == count) {
				List<CustomerAttribute> atts = cusAttSv.getAttribute(customer.getCustomerId()); // lay
																								// attributes
																								// cua 1 KH
				for (ActionCriteriaResult ACR : ACRs) {
					if (ACR.getActionCriteria().getCriteria().getCriteriaId() == 1) { // tieu chi nhan
						Integer ACRid = ACR.getActionCriteriaResultId();
						System.out.println("ACR: " + ACRid);
						List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
																		// ACR
						if (nodes.isEmpty()) {
							System.out.println("ACR have no condition");

						} else {
							Node root = findRoot(nodes);

							System.out.println("root: " + root.getNodeId());
							if (isMatchGroup(atts, root, nodes)) {

								FinalAction rs = new FinalAction();
								rs.setCustomer(customer); // KH 1, 2
								// rs.setCustomer();
								rs.setAction(ACR.getActionCriteria().getAction());
								finalActionList.add(rs);
								System.out.println("=> customer " + customer.getCustomerId() + " CO thoa " + "rule "
										+ root.getNodeId());
							} else
								System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa " + "rule "
										+ root.getNodeId());

						}
					}
				}
			}
			count = count + 1;
		}
		// if 1,2
//		return finalAcSv.saveAllFinalAction(finalActionList); // luu KH nhan duoc hanh dong tho gi
		return finalActionList;

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
		List<CustomerAttribute> atts = cusAttSv.getAttribute(id);
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
			String currentValue = "";
			// boolean f = false;
			for (CustomerAttribute item : atts) {
				if (item.getAttribute().getAttributeId() == currentNode.getCondition().getAttribute()
						.getAttributeId()) {
					currentValue = item.getValue();
					// f = true;
//					System.out.println("cr val: " + currentValue+" - "+item.getCustomer().getCustomerId());
					System.out.println("cr val: " + currentValue + " - " + item.getCustomerId());
				}

			}
			// if(!f) return false;
			Integer operator = currentNode.getCondition().getOperator().getOperatorId();
			String value = currentNode.getCondition().getValue();
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

	public boolean evalCondition(String currentValue, Integer operator, String value) {
		if (currentValue == null)
			return false;
		if (currentValue.isEmpty())
			return false;
		switch (operator) {
		case 1: {
			if (Integer.parseInt(currentValue) > Integer.parseInt(value))
				return true;

			break;
		}
		case 2: {
			if (Integer.parseInt(currentValue) < Integer.parseInt(value))
				return true;
			break;
		}
		case 3: {
			if (currentValue.equalsIgnoreCase(value))
				return true;
			break;
		}
		case 4:
			if (currentValue != value) {
				return true;
			}
			break;

		}
		return false;

	}

	// xac dinh node co tieu chi thoi gian
	public Node findNodeTime(List<Node> nodes) {
		return nodes.stream().filter(x -> x.getCondition().getAttribute().getAttributeId() == 4).findFirst()
				.orElse(null);
	}

	// xac dinh ngay sinh cua khach hang
	public LocalDate findDOB(List<CustomerAttribute> list) {
		for (CustomerAttribute CA : list) {
			if (CA.getAttribute().getAttributeId() == 4) {

				String value = CA.getValue();
				if (value.isEmpty())
					return null;
				LocalDate DOB = LocalDate.parse(value);
				return DOB;
			}
		}
		return null;
	}

	// temp

	public List<FinalActionDetail> finalActionDetailOrigin() {
		List<Customer> customers = cusSv.getAllCustomer();
		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR();
		List<FinalAction> finalActionList = new ArrayList<FinalAction>();
		List<FinalActionDetail> list = new ArrayList<FinalActionDetail>();
		List<ActionCriteriaResult> ACRList = new ArrayList<ActionCriteriaResult>();
		int count = 1;

		for (Customer customer : customers) // for all customer
		{
			if (count > 5)
				break;
			if (customer.getCustomerId() == count) {
				List<CustomerAttribute> atts = cusAttSv.getAttribute(customer.getCustomerId()); // lay
																								// attributes
																								// cua 1 KH
				for (ActionCriteriaResult ACR : ACRs) {
					int id = ACR.getActionCriteria().getCriteria().getCriteriaId();
					if (id == 1) { // tieu chi nhan
						System.out.println(ACR.getActionCriteria().getCriteria().getName());
						Integer ACRid = ACR.getActionCriteriaResultId();
						System.out.println("ACR: " + ACRid);
						List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
																		// ACR
						if (nodes.isEmpty()) {
							System.out.println("ACR have no condition");

						} else {
							Node root = findRoot(nodes);
							System.out.println("root: " + root.getNodeId());
							if (isMatchGroup(atts, root, nodes)) {
								FinalAction rs = new FinalAction();
								rs.setCustomer(customer); // KH 1, 2
								// rs.setCustomer();
								rs.setAction(ACR.getActionCriteria().getAction());
								finalActionList.add(rs);
								System.out.println("=> customer " + customer.getCustomerId() + " CO thoa " + "rule "
										+ root.getNodeId());

							} else {
								System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa " + "rule "
										+ root.getNodeId());
								break;
							}
						}
					}
				}
			}
			count = count + 1;
		}
		// sau khi nhan duoc hanh dong thi xet den tieu chi khac
		Set<Customer> customerList = new TreeSet<>(Comparator.comparing(Customer::getCustomerId));
		for (FinalAction finalAction : finalActionList) {
			customerList.add(finalAction.getCustomer());
		}
		for (Customer customer : customerList) // for all customer
		{

			{
				List<CustomerAttribute> atts = cusAttSv.getAttribute(customer.getCustomerId()); // lay
																								// attributes
																								// cua 1 KH
				for (ActionCriteriaResult ACR : ACRs) {
					int id = ACR.getActionCriteria().getCriteria().getCriteriaId();
					if (id != 1)
						if (id == 2) { // tieu chi thoi gian

							System.out.println(ACR.getActionCriteria().getCriteria().getName());
							Integer ACRid = ACR.getActionCriteriaResultId();
							System.out.println("ACR: " + ACRid);
							List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
																			// ACR
							if (nodes.isEmpty()) {
								System.out.println("ACR have no condition");

							} else {
								Node root = findRoot(nodes);
								LocalDate DOB = findDOB(atts);
								System.out.println("root: " + root.getNodeId());
								if (isMatchGroup(atts, root, nodes) && DOB != null) {

									System.out.println("cutoemer " + customer.getCustomerId());
									FinalAction rs = new FinalAction();
									rs.setCustomer(customer);
									rs.setAction(ACR.getActionCriteria().getAction());

									// tim ngay sinh

									String bd = String.valueOf((int) DOB.getDayOfMonth()) + "-" + DOB.getMonthValue();

									FinalActionDetail finalActionDetail = new FinalActionDetail();
									String result = ACR.getResult().getResult();
									System.out.println("kq: " + result);

									ACR.getResult().setResult(result + bd);

									ACRList.add(ACR); // them ACR vao List ACR trong detail
									finalActionDetail.setACR(ACRList); // them ACR vao List ACR trong detail
//									finalActionDetail.setACR(ACR); //add 1 ACR vao 1 FAD 
									finalActionDetail.setFinalAction(rs);
									System.out.println(customer.getCustomerId() + ". final action: " + rs);
//									list.add(finalActionDetail); // them vao FAD
									list.add(finalActionDetail);

								}

							}
						} else {
							System.out.println(ACR.getActionCriteria().getCriteria().getName());
							Integer ACRid = ACR.getActionCriteriaResultId();
							System.out.println("ACR: " + ACRid);
							List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
																			// ACR
							if (nodes.isEmpty()) {
								System.out.println("ACR have no condition");

							} else {
								Node root = findRoot(nodes);
								System.out.println("root: " + root.getNodeId());
								if (isMatchGroup(atts, root, nodes)) {
									FinalActionDetail finalActionDetail = new FinalActionDetail();
									System.out.println("kq: " + ACR.getResult().getResult());

									FinalAction rs = new FinalAction(); // action hanh dong gi
									rs.setCustomer(customer);
									rs.setAction(ACR.getActionCriteria().getAction());
									ACRList.add(ACR); // them vao list ACR
									finalActionDetail.setACR(ACRList);
//									finalActionDetail.setACR(ACR); //old code
									finalActionDetail.setFinalAction(rs);
									list.add(finalActionDetail);
								}

							}
						}
				}
			}

		}
		// if 1,2
		finalAcSv.saveAllFinalAction(finalActionList); // luu KH nhan duoc hanh dong tho gi

		return list;
	}

}
