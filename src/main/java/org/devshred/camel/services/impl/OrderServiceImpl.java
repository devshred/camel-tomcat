package org.devshred.camel.services.impl;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import org.devshred.camel.persistence.OrderItem;
import org.devshred.camel.persistence.OrderRepository;
import org.devshred.camel.persistence.Product;
import org.devshred.camel.persistence.ProductRepository;
import org.devshred.camel.services.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.devshred.camel.persistence.OrderItem.Priority.LOW;


@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	@Produce(uri = "jms:topic:order")
	ProducerTemplate inbox;

	@Produce(uri = "jms:logging")
	ProducerTemplate loggingQueue;

	@Autowired
	public OrderServiceImpl(final OrderRepository orderRepository, final ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	@Override
	public void order(final Long productId) {
		final Product product = productRepository.findOne(productId);
		final OrderItem orderItem = orderRepository.save(new OrderItem(LOW, product, 1));
		LOGGER.info(orderItem.toString());
		loggingQueue.sendBody("order received");
		inbox.sendBodyAndHeader(orderItem, "prio", orderItem.getPriority());
	}
}
