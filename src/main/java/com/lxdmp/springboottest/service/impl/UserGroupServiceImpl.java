package com.lxdmp.springboottest.service.impl;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.lxdmp.springboottest.entity.User;
import com.lxdmp.springboottest.entity.UserGroup;
import com.lxdmp.springboottest.dto.UserGroupDto;
import com.lxdmp.springboottest.entity.UserPriviledge;
import com.lxdmp.springboottest.mapper.UserMapper;
import com.lxdmp.springboottest.mapper.UserGroupMapper;
import com.lxdmp.springboottest.mapper.GroupAndPriviledgeMapper;
import com.lxdmp.springboottest.mapper.UserPriviledgeMapper;
import com.lxdmp.springboottest.service.UserGroupService;
import com.lxdmp.springboottest.service.impl.UserServiceBaseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
@Service
public class UserGroupServiceImpl extends UserServiceBaseImpl implements UserGroupService
{
	private static final Logger logger = LoggerFactory.getLogger(UserGroupServiceImpl.class);
	
	// 增加用户组
	@Override
	public Boolean addUserGroup(UserGroupDto userGroupDto)
	{
		Integer duplicateUserGroupId = userGroupRepository.queryUserGroupIdByName(userGroupDto.getGroupName());
		if(isIdValid(duplicateUserGroupId)) // 已有同名的用户组
			return false;
		UserGroup group = new UserGroup();
		group.setGroupName(userGroupDto.getGroupName());
		userGroupRepository.addUserGroup(group);
		logger.debug(String.format("group \"%s\" added with id %d", group.getGroupName(), group.getGroupId()));
		return true;
	}

	// 删除用户组
	@Override
	public Boolean delUserGroup(String userGroupName)
	{
		Integer existedUserGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(!isIdValid(existedUserGroupId)) // 没有该用户组
			return false;
		userGroupRepository.delUserGroup(existedUserGroupId);
		return true;
	}

	// 修改用户组名称
	@Override
	public Boolean updateUserGroup(String userGroupName, String newUserGroupName)
	{
		Integer existedUserGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(!isIdValid(existedUserGroupId)) // 没有该用户组
			return false;
		userGroupRepository.updateUserGroup(existedUserGroupId, newUserGroupName);
		return true;
	}

	// 查询所有用户组
	@Override
	public List<UserGroup> queryAllUserGroups()
	{
		return userGroupRepository.queryAllUserGroups();
	}

	// 查询用户组
	@Override
	public UserGroup queryUserGroupByName(String userGroupName)
	{
		UserGroup userGroup = userGroupRepository.queryUserGroupByName(userGroupName);
		if(userGroup==null)
			return null;
		return userGroup;
	}

	// 查询用户组未被赋予的权限
	@Override
	public List<UserPriviledge> queryGroupNotAddedPriviledges(String userGroupName)
	{
		List<UserPriviledge> result = new LinkedList<UserPriviledge>();
		UserGroup userGroup = userGroupRepository.queryUserGroupByName(userGroupName);
		if(userGroup!=null)
		{
			List<UserPriviledge> allPriviledges = userPriviledgeRepository.queryAllUserPriviledges();
			Set<UserPriviledge> resultSet = new HashSet<UserPriviledge>();
			resultSet.addAll(allPriviledges);
			resultSet.removeAll(userGroup.getGroupPriviledges());
			result.addAll(resultSet);
		}
		return result;
	}

	// 该用户组中的用户
	@Override
	public List<User> usersInThisGroup(String userGroupName)
	{
		List<User> result = new LinkedList<User>();
		List<User> users = userGroupRepository.queryUsersByName(userGroupName);
		for(User user : users)
		{
			User userInDetail = userRepository.queryUserByName(user.getUserName());
			if(userInDetail!=null)
				result.add(userInDetail);
		}
		return result;
	}

	// 用户组赋予某权限
	@Override
	public Boolean userGroupAddPriviledge(String userGroupName, String userPriviledgeName)
	{
		Integer userGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(!isIdValid(userGroupId)) // 没有该用户组
			return false;

		Integer userPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(userPriviledgeName);
		if(!isIdValid(userPriviledgeId)) // 没有该用户权限
			return false;

		try{
			groupAndPriviledgeRepository.addPriviledgeToGroup(userGroupId, userPriviledgeId);
		}catch(DuplicateKeyException e){
			logger.warn(String.format("group %s already with priviledge %s", 
				userGroupName, userPriviledgeName
			));
			return false;
		}
		return true;
	}

	// 用户组取消某权限
	@Override
	public Boolean userGroupDelPriviledge(String userGroupName, String userPriviledgeName)
	{
		Integer userGroupId = userGroupRepository.queryUserGroupIdByName(userGroupName);
		if(!isIdValid(userGroupId)) // 没有该用户组
			return false;

		Integer userPriviledgeId = userPriviledgeRepository.queryUserPriviledgeIdByName(userPriviledgeName);
		if(!isIdValid(userPriviledgeId)) // 没有该用户权限
			return false;

		groupAndPriviledgeRepository.delPriviledgeFromGroup(userGroupId, userPriviledgeId);
		return true;	
	}
}

