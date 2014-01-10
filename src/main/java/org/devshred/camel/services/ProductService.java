package org.devshred.camel.services;

import java.util.List;

import org.devshred.camel.persistence.Product;


public interface ProductService {
	List<Product> findAll();
}
