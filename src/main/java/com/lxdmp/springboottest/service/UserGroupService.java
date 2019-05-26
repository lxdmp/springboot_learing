package com.lxdmp.springboottest.service;

import java.util.List;
import com.lxdmp.springboottest.entity.User;
import com.lxdmp.springboottest.entity.UserGroup;
import com.lxdmp.springboottest.entity.UserPriviledge;
import com.lxdmp.springboottest.dto.UserGroupDto;

public interface UserGroupService
{
	Boolean addUserGroup(UserGroupDto userGroupDto); // 增加用户组
	Boolean delUserGroup(String userGroupName); // 删除用户组
	Boolean updateUserGroup(String userGroupName, String newUserGroupName); // 修改用户组名称
	List<UserGroup> queryAllUserGroups(); // 查询所有用户组
	UserGroup queryUserGroupByName(String userGroupName); // 查询用户组
	List<UserPriviledge> queryGroupNotAddedPriviledges(String userGroupName); // 查询用户组未被赋予的权限
	List<User> usersInThisGroup(String userGroupName); // 该用户组中的用户
	Boolean userGroupAddPriviledge(String userGroupName, String userPriviledgeName); // 用户组赋予某权限
	Boolean userGroupDelPriviledge(String userGroupName, String userPriviledgeName); // 用户组取消某权限
}

