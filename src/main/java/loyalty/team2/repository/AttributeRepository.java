package loyalty.team2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import loyalty.team2.model.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
	public Attribute findByAttributeId(int attributeId);
}
