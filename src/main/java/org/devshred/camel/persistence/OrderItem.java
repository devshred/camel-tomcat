package org.devshred.camel.persistence;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
@Getter
@ToString
public class OrderItem implements Serializable {
	public enum Priority {
		HIGH, LOW
	}

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	@Enumerated(EnumType.STRING)
	private Priority priority;

	@ManyToOne(optional = false)
	private Product product;

	private Integer quantity;

	public OrderItem(final Priority priority, final Product product, final Integer quantity) {
		this.priority = priority;
		this.product = product;
		this.quantity = quantity;
	}
}
