package org.devshred.camel.controller;

import org.devshred.camel.persistence.OrderItem;
import org.devshred.camel.persistence.Product;
import org.devshred.camel.services.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.devshred.camel.persistence.OrderItem.Priority.LOW;


@Controller
@RequestMapping("/order")
public class OrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	private static final String DEFAULT_VIEW = "index";
	private static final Product PRODUCT = new Product("test product");

	@Autowired
	private OrderService orderService;

	@RequestMapping("/test")
	public ModelAndView order() {
		LOGGER.info("Kilroy was here");
		return new ModelAndView(DEFAULT_VIEW);
	}

	@RequestMapping("/{productId:\\d+}")
	public ModelAndView order(@PathVariable("productId") final Long productId) {
		orderService.order(productId);
		return new ModelAndView(DEFAULT_VIEW);
	}

	@RequestMapping("/{productId:\\d+}/{classification:\\w}")
	public ModelAndView order(@PathVariable("productId") final Long productId,
		@PathVariable("classification") final String classification) {
		orderService.order(productId);
		return new ModelAndView(DEFAULT_VIEW);
	}
}
