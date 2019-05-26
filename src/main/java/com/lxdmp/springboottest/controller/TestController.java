package com.lxdmp.springboottest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/test")
public class TestController
{
	protected static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index()
	{
		logger.info("here 1");
		return "test";
	}

	@RequestMapping("/abc")
	public String index2()
	{
		logger.info("here 2");
		return "test_2";
	}
}

