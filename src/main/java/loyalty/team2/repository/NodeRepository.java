package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Node;
import java.util.List;


@Repository
public interface NodeRepository  extends JpaRepository<Node, Integer> {
	public Node findByNodeId(int nodeId);
	public List<Node> findByHeadId(int headId);
	public List<Node> findByActionCriteriaResultId(int actionCriteriaResultId);
}
