package com.lxdmp.springboottest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lxdmp.springboottest.mapper.UserMapper;
import com.lxdmp.springboottest.mapper.UserAndGroupMapper;
import com.lxdmp.springboottest.mapper.UserGroupMapper;
import com.lxdmp.springboottest.mapper.GroupAndPriviledgeMapper;
import com.lxdmp.springboottest.mapper.UserPriviledgeMapper;

public class UserServiceBaseImpl
{
	@Autowired
	protected UserMapper userRepository;

	@Autowired
	protected UserAndGroupMapper userAndGroupRepository;

	@Autowired
	protected UserGroupMapper userGroupRepository;

	@Autowired
	protected GroupAndPriviledgeMapper groupAndPriviledgeRepository;

	@Autowired
	protected UserPriviledgeMapper userPriviledgeRepository;

	protected boolean isIdValid(Integer userId)
	{
		return (userId!=null && userId>=0);
	}
}

