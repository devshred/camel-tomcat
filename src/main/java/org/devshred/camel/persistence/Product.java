package org.devshred.camel.persistence;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@ToString
public class Product implements Serializable {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	@Id
	private Long id;

	@Getter
	private String name;

	public Product(final String name) {
		this.name = name;
	}

	protected Product() {
	}
}
