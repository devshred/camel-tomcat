package org.devshred.camel.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class InventoryItem {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	@ManyToOne(optional = false)
	private Product product;

	private Integer quantity;

	private Integer minThreshold;

	private Integer maxThreshold;
}
