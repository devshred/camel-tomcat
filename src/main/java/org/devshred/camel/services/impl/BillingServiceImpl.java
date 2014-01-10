package org.devshred.camel.services.impl;

import org.apache.camel.Consume;
import org.apache.camel.Exchange;

import org.devshred.camel.persistence.OrderItem;
import org.devshred.camel.services.BillingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;


@Service
public class BillingServiceImpl implements BillingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BillingServiceImpl.class);

	@Consume(uri = "jms:billing")
	public void triggerBilling(final Exchange exchange) {
		final OrderItem orderItem = (OrderItem) exchange.getIn().getBody();
		LOGGER.info("trigger billing: " + orderItem);
	}
}
