package com.lxdmp.springboottest.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface UserAndGroupMapper
{
	@Insert("insert into UserWithGroup(userId, groupId) values (#{userId}, #{groupId})")
	void userJoinGroup(
		@Param("userId") Integer userId, 
		@Param("groupId") Integer userGroupId
	); // 用户加入某用户组

	@Delete("delete from UserWithGroup where userId=#{userId} and groupId=#{groupId}")
	void userLeaveGroup(
		@Param("userId") Integer userId, 
		@Param("groupId") Integer userGroupId
	); // 用户离开某用户组
}

