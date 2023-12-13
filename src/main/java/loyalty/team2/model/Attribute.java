package loyalty.team2.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "attribute")
public class Attribute {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer attributeId;
	private String attributeType;
	
}
