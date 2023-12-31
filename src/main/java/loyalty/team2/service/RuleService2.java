package loyalty.team2.service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import loyalty.team2.model.Action;
import loyalty.team2.model.ActionCriteriaResult;
import loyalty.team2.model.Customer;
import loyalty.team2.model.CustomerActionWhy;
import loyalty.team2.model.CustomerAttribute;
import loyalty.team2.model.FinalAction;
import loyalty.team2.model.FinalActionAttribute;
import loyalty.team2.model.FinalActionEvent;
import loyalty.team2.model.FinalActionValue;
import loyalty.team2.model.Log;
import loyalty.team2.model.Node;

@Service
public class RuleService2 {

	@Autowired
	private NodeService NodeSv;
	@Autowired
	private CustomerService cusSv;
	@Autowired
	private ActionCriteriaResultService ACRSv;
	@Autowired
	private FinalActionService finalAcSv;
	@Autowired
	private FinalActionAttributeService finalAcAttSv;
	@Autowired
	private FinalActionEventService finalAESv;
	@Autowired
	private LogService logSv;

	public List<FinalAction> ruleExcelEventLog(List<Customer> customerList) {
		
		List<FinalAction> finalActionList = new ArrayList<FinalAction>();
		List<Customer> customers = cusSv.getAllCustomer(); // get all customer
		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR(); // get all ACRs
		List<FinalActionEvent> finalAEs = new ArrayList<FinalActionEvent>();
		
		// xet tieu chi nhan
		for (Customer cus : customerList) {
			System.out.println("check: "+cus.getCustomerId());
			for (Customer customer : customers) // for all customer
			{
				System.out.println("check inside: "+customer.getCustomerId());
				if (cus.getCustomerId().equals(customer.getCustomerId())) {
					System.out.println("------customer " + customer.getCustomerId());
					List<CustomerAttribute> atts = customer.getCusAtt(); // lay
																			// attributes
																			// cua 1 KH
					FinalActionEvent finalAE = new FinalActionEvent();
					for (ActionCriteriaResult ACR : ACRs) {
						int criteriaId = ACR.getActionCriteria().getCriteria().getCriteriaId();
						if (criteriaId == 1) { // tieu chi nhan
							List<Log> logListGet = new ArrayList<Log>();
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

								if (isMatchGroup(atts, root, nodes, logListGet)) {
									Action action = ACR.getActionCriteria().getAction();
									// bat dau luu su kien									
									finalAE.setCustomer(customer);
									finalAE.setAction(action);
									finalAE.setType("duyet");
									finalAE.setCreated_by(1);
									System.out.println("final action event: " + finalAE.getCustomer().getName()+" - "+finalAE.getAction().getName());
									FinalActionEvent x = finalAESv.persist(finalAE);
									finalAEs.add(x);
																		
									// su kien nay se tac dong den final action
									FinalAction rs = new FinalAction();
									rs.setCustomer(customer);
									rs.setAction(action);
									rs.setLastId(x.getFinalActionEventId());
									
									rs = finalAcSv.saveFinalAction(rs);
									finalActionList.add(rs);
									
									System.out.println("=> customer " + customer.getCustomerId() + " CO thoa " + "rule "
											+ root.getNodeId());
									//luu lai Final Action truoc khi luu log
//									finalAcSv.saveFinalAction(rs);
									//ghi lai log
									for(Log log: logListGet) {
										log.setFinalActionEvent(x);
										log.setFinalAction(rs);
//										logSv.persist(log);
									}
									logSv.persist(logListGet);
								} else {
									System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa "
											+ "rule " + root.getNodeId());

								}
							}
						}
					}
				}

			}
		}
			

		for (FinalAction fa : finalActionList)
			System.out.println(fa.getFinalActionId() +" - "+ fa.getCustomer().getCustomerId() + " - " + fa.getAction().getName());

		// sau khi nhan duoc hanh dong thi xet den tieu chi khac
//		Set<Customer> customerList = new TreeSet<>(Comparator.comparing(Customer::getCustomerId));
//		for (FinalAction finalAction : finalActionList) {
//			customerList.add(finalAction.getCustomer());
//		}

		//
		List<FinalActionAttribute> finalAcAtts = finalAcAttSv.getAllFAA();
		
		
		for (FinalAction finalAction : finalActionList) {
			Customer customer = finalAction.getCustomer();
			List<CustomerAttribute> atts = customer.getCusAtt();
			List<FinalActionValue> valueList = new ArrayList<FinalActionValue>();
			List<Log> logs = new ArrayList<Log>();
			for (ActionCriteriaResult ACR : ACRs) {				
				if (ACR.getActionCriteria().getAction().equals(finalAction.getAction())) {
					System.out.println("ACR: " + ACR.getActionCriteria().getAction().getName() + ", "
							+ ACR.getActionCriteriaResultId() + " - FA: " + finalAction.getAction().getName());
					if (ACR.getActionCriteria().getCriteria().getCriteriaId() != 1) { // tieu chi khac
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

							if (isMatchGroup(atts, root, nodes, logs)) {
								System.out.println("customer " + customer.getCustomerId());
								String bd = "";
								if (DOB != null)
									bd = String.valueOf((int) DOB.getDayOfMonth()) + "/" + DOB.getMonthValue()+"/"+ String.valueOf(Year.now().getValue());

								String result = ACR.getResult().getResult();
								//kết quả động
								String newResult = replaceContentInsideBrackets(result, "test", "hehe");
								System.out.println("start: " + newResult);
								newResult = replaceContentInsideBrackets(newResult, "DOB", bd);
								System.out.println("DOB: " + newResult);
								newResult = replaceContentInsideBrackets(newResult, "name", customer.getName()); 
								System.out.println("name: " + customer.getName() + " - result name:" + newResult);
								
								for(CustomerAttribute cu: atts)
									if(cu.getAttribute().getAttributeId()==8)
										newResult = replaceContentInsideBrackets(newResult, "sp", cu.getValue());
								
								for (CustomerAttribute ca : atts)
									if (ca.getAttribute().getAttributeId() == 3)
										if (ca.getValue().equals("gen Z")) {
											newResult = replaceContentInsideBrackets(newResult, "TIME", "0:00:00");
											System.out.println("time: " + result);
										}

										else {
											newResult = replaceContentInsideBrackets(newResult, "TIME", "7:00:00");
											System.out.println("time: " + result);
										}

								System.out.println("final result: " + newResult);

								FinalActionAttribute finalAcAtt = new FinalActionAttribute();
								boolean x = false;
								for (FinalActionAttribute FAA : finalAcAtts)
									if (FAA.getCriteria().equals(ACR.getActionCriteria().getCriteria())) {
										finalAcAtt = FAA;
										x = true;
									}
								if (x == false) {
									finalAcAtt.setCriteria(ACR.getActionCriteria().getCriteria());
									finalAcAtt = finalAcAttSv.persist(finalAcAtt);
								}

								FinalActionValue finalAcVal = new FinalActionValue();

								finalAcVal.setFinalAction(finalAction);
								finalAcVal.setFinalActionAttribute(finalAcAtt);
								finalAcVal.setValue(newResult);

								valueList.add(finalAcVal);
								
								//ghi lai log
								for(Log log: logs) {									
									log.setFinalActionValue(finalAcVal);
//									logSv.persist(log);
								}
								finalAction.setFinalActionValue(valueList);
								finalAcSv.update(finalAction);								
							}
						}
					}
				}
			}
		}		
//		finalAcSv.saveAllFinalAction(finalActionList); // luu KH nhan duoc hanh dong

		
		return finalActionList;
	}

	/* OK
	public List<FinalAction> ruleExcelEvent(List<Customer> customerList) {
		List<FinalAction> finalActionList = new ArrayList<FinalAction>();
		List<Customer> customers = cusSv.getAllCustomer(); // get all customer
		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR(); // get all ACRs

		// xet tieu chi nhan
		for (Customer cus : customerList)
			for (Customer customer : customers) // for all customer
			{

				if (cus.getCustomerId() == customer.getCustomerId()) {
					System.out.println("------customer " + customer.getCustomerId());
					List<CustomerAttribute> atts = customer.getCusAtt(); // lay
																			// attributes
																			// cua 1 KH
					FinalActionEvent finalAE = new FinalActionEvent();
					for (ActionCriteriaResult ACR : ACRs) {
						int criteriaId = ACR.getActionCriteria().getCriteria().getCriteriaId();
						if (criteriaId == 1) { // tieu chi nhan
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
									Action action = ACR.getActionCriteria().getAction();
									// bat dau luu su kien									
									finalAE.setCustomer(customer);
									finalAE.setAction(action);
									finalAE.setType("duyet");
									finalAE.setCreated_by(1);
									System.out.println("final action event: " + finalAE.getCustomer().getName()+" - "+finalAE.getAction().getName());
									FinalActionEvent x = finalAESv.persist(finalAE);
									// su kien nay se tac dong
									FinalAction rs = new FinalAction();
									rs.setCustomer(customer);
									rs.setAction(action);
									rs.setLastId(x.getFinalActionEventId());
									finalActionList.add(rs);
									System.out.println("=> customer " + customer.getCustomerId() + " CO thoa " + "rule "
											+ root.getNodeId());

								} else {
									System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa "
											+ "rule " + root.getNodeId());

								}
							}
						}
					}
				}

			}

		for (FinalAction fa : finalActionList)
			System.out.println(fa.getCustomer().getCustomerId() + " - " + fa.getAction().getName());

		// sau khi nhan duoc hanh dong thi xet den tieu chi khac
//		Set<Customer> customerList = new TreeSet<>(Comparator.comparing(Customer::getCustomerId));
//		for (FinalAction finalAction : finalActionList) {
//			customerList.add(finalAction.getCustomer());
//		}

		//
		List<FinalActionAttribute> finalAcAtts = finalAcAttSv.getAllFAA();

		for (FinalAction finalAction : finalActionList) {
			Customer customer = finalAction.getCustomer();
			List<CustomerAttribute> atts = customer.getCusAtt();
			List<FinalActionValue> valueList = new ArrayList<FinalActionValue>();

			for (ActionCriteriaResult ACR : ACRs) {

				if (ACR.getActionCriteria().getAction().equals(finalAction.getAction())) {
					System.out.println("ACR: " + ACR.getActionCriteria().getAction().getName() + ", "
							+ ACR.getActionCriteriaResultId() + " - FA: " + finalAction.getAction().getName());
					if (ACR.getActionCriteria().getCriteria().getCriteriaId() != 1) { // tieu chi khac
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

							if (isMatchGroup(atts, root, nodes)) {
								System.out.println("customer " + customer.getCustomerId());
								String bd = "";
								if (DOB != null)
									bd = String.valueOf((int) DOB.getDayOfMonth()) + "-" + DOB.getMonthValue(); // ngay
																												// sinh
																												// KH

								String result = ACR.getResult().getResult();

								String newResult = replaceContentInsideBrackets(result, "test", "hehe");
								System.out.println("start: " + newResult);
								newResult = replaceContentInsideBrackets(newResult, "DOB", bd);
								System.out.println("DOB: " + newResult);
								newResult = replaceContentInsideBrackets(newResult, "name", customer.getName()); // kết
																													// quả
																													// động
								System.out.println("name: " + customer.getName() + " - result name:" + newResult);
								for (CustomerAttribute ca : atts)
									if (ca.getAttribute().getAttributeId() == 3)
										if (ca.getValue().equals("gen Z")) {
											newResult = replaceContentInsideBrackets(newResult, "TIME", "0:00:00");
											System.out.println("time: " + result);
										}

										else {
											newResult = replaceContentInsideBrackets(newResult, "TIME", "7:00:00");
											System.out.println("time: " + result);
										}

								System.out.println("final result: " + newResult);

								FinalActionAttribute finalAcAtt = new FinalActionAttribute();
								boolean x = false;
								for (FinalActionAttribute FAA : finalAcAtts)
									if (FAA.getCriteria().equals(ACR.getActionCriteria().getCriteria())) {
										finalAcAtt = FAA;
										x = true;
									}
								if (x == false) {
									finalAcAtt.setCriteria(ACR.getActionCriteria().getCriteria());
									finalAcAtt = finalAcAttSv.persist(finalAcAtt);
								}

								FinalActionValue finalAcVal = new FinalActionValue();

								finalAcVal.setFinalAction(finalAction);
								finalAcVal.setFinalActionAttribute(finalAcAtt);
								finalAcVal.setValue(newResult);

								valueList.add(finalAcVal);

								finalAction.setFinalActionValue(valueList);
							}

						}
					}
				}

			}
//			finalAcSv.saveFinalAction(finalAction);
		}

		finalAcSv.saveAllFinalAction(finalActionList); // luu KH nhan duoc hanh dong
		return finalActionList;
	}

	*/
	/*
	public List<FinalAction> ruleExcel(List<Customer> customerList) {
		List<FinalAction> finalActionList = new ArrayList<FinalAction>();
		List<Customer> customers = cusSv.getAllCustomer(); // get all customer
		List<ActionCriteriaResult> ACRs = ACRSv.getAllACR(); // get all ACRs

		// xet tieu chi nhan
		for (Customer cus : customerList)
			for (Customer customer : customers) // for all customer
			{

				System.out.println("------customer " + customer.getCustomerId());
				if (cus.getCustomerId() == customer.getCustomerId()) {
					List<CustomerAttribute> atts = customer.getCusAtt(); // lay
																			// attributes
																			// cua 1 KH
					for (ActionCriteriaResult ACR : ACRs) {
						int criteriaId = ACR.getActionCriteria().getCriteria().getCriteriaId();
						if (criteriaId == 1) { // tieu chi nhan
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
									System.out.println("=> customer " + customer.getCustomerId() + " KHONG thoa "
											+ "rule " + root.getNodeId());

								}
							}
						}
					}
				}

			}
		System.out.println("start nhan hanh dong: ");
		for (FinalAction fa : finalActionList)
			System.out.println(fa.getCustomer().getCustomerId() + " - " + fa.getAction().getName());
		System.out.println("end nhan hanh dong");
		// sau khi nhan duoc hanh dong thi xet den tieu chi khac
//		Set<Customer> customerList = new TreeSet<>(Comparator.comparing(Customer::getCustomerId));
//		for (FinalAction finalAction : finalActionList) {
//			customerList.add(finalAction.getCustomer());
//		}

		//
		List<FinalActionAttribute> finalAcAtts = finalAcAttSv.getAllFAA();

		for (FinalAction finalAction : finalActionList) {
			Customer customer = finalAction.getCustomer();
			List<CustomerAttribute> atts = customer.getCusAtt();
			List<FinalActionValue> valueList = new ArrayList<FinalActionValue>();

			for (ActionCriteriaResult ACR : ACRs) {

				if (ACR.getActionCriteria().getAction().equals(finalAction.getAction())) {
					System.out.println("ACR: " + ACR.getActionCriteria().getAction().getName() + ", "
							+ ACR.getActionCriteriaResultId() + " - FA: " + finalAction.getAction().getName());
					if (ACR.getActionCriteria().getCriteria().getCriteriaId() != 1) { // tieu chi khac
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

							if (isMatchGroup(atts, root, nodes)) {
								System.out.println("customer " + customer.getCustomerId());
								String bd = "";
								if (DOB != null)
									bd = String.valueOf((int) DOB.getDayOfMonth()) + "-" + DOB.getMonthValue(); // ngay
																												// sinh
																												// KH

								String result = ACR.getResult().getResult();

								String newResult = replaceContentInsideBrackets(result, "test", "hehe");
								System.out.println("start: " + newResult);
								newResult = replaceContentInsideBrackets(newResult, "DOB", bd);
								System.out.println("DOB: " + newResult);
								newResult = replaceContentInsideBrackets(newResult, "name", customer.getName()); // kết
																													// quả
																													// động
								System.out.println("name: " + customer.getName() + " - result name:" + newResult);
								for (CustomerAttribute ca : atts)
									if (ca.getAttribute().getAttributeId() == 3)
										if (ca.getValue().equals("gen Z")) {
											newResult = replaceContentInsideBrackets(newResult, "TIME", "0:00:00");
											System.out.println("time: " + result);
										}

										else {
											newResult = replaceContentInsideBrackets(newResult, "TIME", "7:00:00");
											System.out.println("time: " + result);
										}

								System.out.println("final result: " + newResult);

								FinalActionAttribute finalAcAtt = new FinalActionAttribute();
								boolean x = false;
								for (FinalActionAttribute FAA : finalAcAtts)
									if (FAA.getCriteria().equals(ACR.getActionCriteria().getCriteria())) {
										finalAcAtt = FAA;
										x = true;
									}
								if (x == false) {
									finalAcAtt.setCriteria(ACR.getActionCriteria().getCriteria());
									finalAcAtt = finalAcAttSv.persist(finalAcAtt);
								}

//								FinalActionAttribute finalAcAtt = new FinalActionAttribute();
//								finalAcAtt.setCriteria(ACR.getActionCriteria().getCriteria());
//								finalAcAtt = finalAcAttSv.persist(finalAcAtt);

								FinalActionValue finalAcVal = new FinalActionValue();

								finalAcVal.setFinalAction(finalAction);
								finalAcVal.setFinalActionAttribute(finalAcAtt);
								finalAcVal.setValue(newResult);
//								finalAcVals.add(finalAcVal);

								valueList.add(finalAcVal);

								finalAction.setFinalActionValue(valueList);
//								finalAction.getFinalActionValue().addAll(valueList);

							}

						}
					}
				}

			}
//			finalAcSv.saveFinalAction(finalAction);
		}

		finalAcSv.saveAllFinalAction(finalActionList); // luu KH nhan duoc hanh dong
		return finalActionList;
	}
*/
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
				List<CustomerAttribute> atts = customer.getCusAtt(); // lay
																		// attributes
																		// cua 1 KH
				for (ActionCriteriaResult ACR : ACRs) {
					List<Log> logs = new ArrayList<Log>();
					Integer ACRid = ACR.getActionCriteriaResultId();
					System.out.println("ACR: " + ACRid);
					List<Node> nodes = NodeSv.getNodesFlACR(ACRid); // lay 1 cay dieu kien cua 1
																	// ACR
					if (nodes.isEmpty()) {
						System.out.println("ACR have no condition");

					} else {
						Node root = findRoot(nodes);
						System.out.println("root: " + root.getNodeId());
						if (isMatchGroup(atts, root, nodes, logs)) {
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

	public boolean isMatchGroup(List<CustomerAttribute> atts, Node currentNode, List<Node> nodes, List<Log> logs) {
//		logs = new ArrayList<Log>();
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
					System.out.println("cr val: " + currentValue + " - " + item.getCustomer().getCustomerId());
				}

			}
			// if(!f) return false;
			Integer operator = currentNode.getCondition().getOperator().getOperatorId();
			String value = currentNode.getCondition().getValue();
			bl = evalCondition(currentValue, operator, value);
			//ghi lai ket qua lúc xét điều kiện 
			{
				Log log = new Log();
				log.setCondition(currentNode.getCondition());
				log.setCustomerValue(currentValue);
				logs.add(log);
			}
				
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
				bl = children.stream().allMatch(child -> isMatchGroup(atts, child, nodes, logs));
				System.out.println("rs logic and node." + currentNode.getNodeId() + " - " + bl);

			} else if (currentNode.getLogic().getLogicId() == 2) {
				bl = children.stream().anyMatch(child -> isMatchGroup(atts, child, nodes, logs));
				System.out.println("rs logic or node." + currentNode.getNodeId() + " - " + bl);

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

	public static String replaceContentInsideBrackets(String input, String placeholder, String replacement) {
		Pattern pattern = Pattern.compile("\\{" + placeholder + "\\}");
		Matcher matcher = pattern.matcher(input);

		StringBuffer result = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(result, replacement);
		}

		matcher.appendTail(result);
		return result.toString();
	}


}
