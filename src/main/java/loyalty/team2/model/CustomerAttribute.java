package loyalty.team2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customer_attribute")
public class CustomerAttribute {
	@Id
	private Integer customerAttributeId;
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="customer_id")
	private Customer customer;
//	private Integer customerId;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attribute_id", referencedColumnName = "attributeId")
	private Attribute attribute;
	private String value;

}
