package com.lxdmp.springboottest.service;

import java.util.List;
import com.lxdmp.springboottest.entity.User;
import com.lxdmp.springboottest.entity.UserGroup;
import com.lxdmp.springboottest.dto.UserDto;

public interface UserService
{
	Boolean addUser(UserDto userDto); // 增加用户
	Boolean delUser(String userName); // 删除用户
	Boolean updateUserPassword(String userName, String oldPassword, String newPassword); // 修改用户密码
	List<User> queryAllUsers(); // 查询所有用户
	User queryUserByName(String userName); // 查询用户
	List<UserGroup> queryUserNotJoinedGroups(String userName); // 查询用户未加入的用户组
	Boolean userJoinGroup(String userName, String userGroupName); // 用户加入某用户组
	Boolean userLeaveGroup(String userName, String userGroupName); // 用户离开某用户组
}

