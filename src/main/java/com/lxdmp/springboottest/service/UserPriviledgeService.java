package com.lxdmp.springboottest.service;

import java.util.List;
import com.lxdmp.springboottest.entity.User;
import com.lxdmp.springboottest.entity.UserGroup;
import com.lxdmp.springboottest.entity.UserPriviledge;
import com.lxdmp.springboottest.dto.UserPriviledgeDto;

public interface UserPriviledgeService
{
	Boolean addUserPriviledge(UserPriviledgeDto userPriviledgeDto); // 增加用户权限
	Boolean delUserPriviledge(String userPriviledgeName); // 删除用户权限
	Boolean updateUserPriviledge(String userPriviledgeName, String newUserPriviledgeName); // 修改用户权限名称
	List<UserPriviledge> queryAllUserPriviledges(); // 查询所有用户权限
	UserPriviledge queryUserPriviledgeByName(String userPriviledgeName); // 查询用户权限
	List<UserGroup> userGroupsWithPriviledge(String userPriviledgeName); // 具有该权限的用户组
	List<User> usersWithPriviledge(String userPriviledgeName); // 具有该权限的用户
}

