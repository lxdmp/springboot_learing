package com.lxdmp.springboottest.schedule;

import java.util.*;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lxdmp.springboottest.amqp.CustomAmqpSender;

@Component
public class CustomTaskScheduler
{
	private static final Logger logger = LoggerFactory.getLogger(CustomTaskScheduler.class);

	@Autowired 
	private CustomAmqpSender amqpSender;

	// 格式与cron一致(与cron相比多了秒的精度,每天hh:mm:ss执行)
	@Scheduled(cron = "0 30 12 * * *")
	public void dailyWork()
	{
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info(String.format("daily work : %s", f.format(new Date())));
	}

	// 启动时执行一次,之后以若干时间周期执行(单位ms)
	@Scheduled(fixedRate = 1000*5)
	public void periodcTask()
	{
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.debug(String.format("periodic work : %s", f.format(new Date())));
	}

	@Scheduled(fixedRate = 1000*10)
	public void testForRabbitSend()
	{
		amqpSender.send(
			"test.topic", // exchange
			"test.abc", // routing-key
			String.format("%s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
		);
	}
}

