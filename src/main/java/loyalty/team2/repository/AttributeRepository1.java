package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Attribute;
import loyalty.team2.model.Attribute1;

@Repository
public interface AttributeRepository1 extends JpaRepository<Attribute1, Integer> {
	public Attribute1 findByAttributeId(int attributeId);
}
