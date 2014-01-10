package org.devshred.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import static org.devshred.camel.persistence.OrderItem.Priority.HIGH;


@Component
public class OrderRoutes extends SpringRouteBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderRoutes.class);

	@Override
	public void configure() throws Exception {
		from("jms:topic:order").log("received order").routeId("topic order");

		from("jms:topic:order").to("jms:billing").log("forwarded to billing").routeId("fw billing");

		from("jms:topic:order").to("jms:shipping").log("forwarded to shipping").routeId("fw shipping");
		from("jms:shipping") //
		.choice() //
		.when(header("prio").isEqualTo(HIGH)).to("jms:topic:highPrio") //
		.when(header("prio").not().isEqualTo(HIGH)).to("jms:topic:lowPrio") //
		.routeId("shipping prio");

		from("jms:logging").process(new Processor() {
				@Override
				public void process(final Exchange exchange) throws Exception {
					LOGGER.info("logging event: {}", exchange.getIn().getBody());
				}
			}).routeId("logging");
	}
}
