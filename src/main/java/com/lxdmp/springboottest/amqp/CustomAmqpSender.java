package com.lxdmp.springboottest.amqp;

import java.util.UUID;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomAmqpSender implements RabbitTemplate.ConfirmCallback, ReturnCallback
{
	private static final Logger logger = LoggerFactory.getLogger(CustomAmqpSender.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
	@PostConstruct
	public void init()
	{
		rabbitTemplate.setConfirmCallback(this);
		rabbitTemplate.setReturnCallback(this);
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause)
	{
		if (ack)
		{
			logger.debug("消息发送成功:"+correlationData);
        }else{
			logger.warn("消息发送失败:"+cause);
		}
	}

	@Override
	public void returnedMessage(
		Message message, 
		int replyCode, String replyText, 
		String exchange, String routingKey
	)
	{
		logger.warn(message.getMessageProperties().getCorrelationId()+" 发送失败");
    }

	public void send(String exchange, String routing_key, String msg)
	{
		rabbitTemplate.convertAndSend(
			exchange, 
			routing_key, 
			msg
		);
    }
}

