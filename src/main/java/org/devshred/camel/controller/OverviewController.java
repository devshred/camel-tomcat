package org.devshred.camel.controller;

import org.devshred.camel.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class OverviewController {
	private static final String DEFAULT_VIEW = "index";

	@Autowired
	private ProductService productService;

	@RequestMapping("/")
	public ModelAndView click() {
		final ModelAndView modelAndView = new ModelAndView(DEFAULT_VIEW);
		modelAndView.addObject("products", productService.findAll());
		return modelAndView;
	}
}
