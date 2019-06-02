package com.lxdmp.springboottest.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomAmqpReceiver
{
	private static final Logger logger = LoggerFactory.getLogger(CustomAmqpReceiver.class);

	@RabbitListener(queues={"test.queue1"})
	public void onReceived(String msg)
	{
		logger.debug(String.format("recv : %s!", msg));
	}
}

