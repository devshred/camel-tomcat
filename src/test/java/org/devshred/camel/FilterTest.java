package org.devshred.camel;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class FilterTest extends CamelTestSupport {
	private static final String HEADER_NAME = "title";
	private static final String HEADER_VALUE = "ABC";
	private static final String BODY = "message body";
	private static final String QUEUE_START = "direct:start";
	private static final String QUEUE_END = "mock:result";

	@EndpointInject(uri = QUEUE_END)
	protected MockEndpoint resultEndpoint;

	@Produce(uri = QUEUE_START)
	protected ProducerTemplate template;

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedBodiesReceived(BODY);

		template.sendBodyAndHeader(BODY, HEADER_NAME, HEADER_VALUE);

		assertMockEndpointsSatisfied();
	}

	@Test
	public void testSendNotMatchingMessage() throws Exception {
		resultEndpoint.expectedMessageCount(0);

		template.sendBodyAndHeader(BODY, HEADER_NAME, "invalid" + HEADER_VALUE);

		assertMockEndpointsSatisfied();
	}

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from(QUEUE_START).filter(header(HEADER_NAME).isEqualTo(HEADER_VALUE)).to(QUEUE_END);
			}
		};
	}
}