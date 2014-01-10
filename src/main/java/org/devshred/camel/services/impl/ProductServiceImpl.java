package org.devshred.camel.services.impl;

import java.util.List;

import org.devshred.camel.persistence.Product;
import org.devshred.camel.persistence.ProductRepository;
import org.devshred.camel.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository repository;

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}
}
