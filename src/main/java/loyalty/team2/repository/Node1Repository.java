package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Node1;

import java.util.List;


@Repository
public interface Node1Repository extends JpaRepository<Node1, Integer> {
	public Node1 findByNodeId(int nodeId);
	public List<Node1> findByHeadId(int headId);
}
