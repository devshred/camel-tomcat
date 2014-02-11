package org.devshred.camel;

import org.apache.activemq.broker.BrokerService;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.processor.RedeliveryPolicy;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DeadLetterQueueTest extends CamelTestSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeadLetterQueueTest.class);
	private static final String BODY = "some noise";
	private static final String QUEUE_START = "activemq:queue:start";
	private static final String QUEUE_ENDPOINT = "mock:end";
	private static final String QUEUE_DLQ = "activemq:queue:dlq";

	private BrokerService broker;
	private final CountDownLatch latch = new CountDownLatch(6);

	@EndpointInject(uri = QUEUE_ENDPOINT)
	protected MockEndpoint mockEndpoint;

	@Produce(uri = QUEUE_START)
	protected ProducerTemplate template;

	@Before
	public void setUp() throws Exception {
		broker = new BrokerService();
		broker.setPersistent(false);
		broker.setUseJmx(false);
		broker.addConnector("tcp://localhost:61616");
		broker.start();

		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();

		broker.stop();
	}

	@Test
	public void forwardMessageToDeadLetterQueue_IfRedeliveryLimitWasReached() throws Exception {
		mockEndpoint.expectedMessageCount(0);
		mockEndpoint.setSleepForEmptyTest(10000);

		template.sendBody(BODY);

		assertTrue(latch.await(10, TimeUnit.SECONDS));

		assertEquals(BODY, consumer.receiveBody(QUEUE_DLQ));
		assertMockEndpointsSatisfied();
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			public void configure() throws Exception {
				RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
				redeliveryPolicy.setMaximumRedeliveries(6);
				redeliveryPolicy.setRedeliveryDelay(1000);

				DeadLetterChannelBuilder deadLetterChannelBuilder = new DeadLetterChannelBuilder();
				deadLetterChannelBuilder.setDeadLetterUri(QUEUE_DLQ);
				deadLetterChannelBuilder.setRedeliveryPolicy(redeliveryPolicy);
				deadLetterChannelBuilder.setUseOriginalMessage(true);

				context.setErrorHandlerBuilder(deadLetterChannelBuilder);

				context.setTracing(true);

				from(QUEUE_START)
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
								LOGGER.info("process; latch count: " + latch.getCount());
								latch.countDown();
								throw new Exception("forced by unit test");
							}
						})
						.to(QUEUE_ENDPOINT);
			}
		};
	}
}
