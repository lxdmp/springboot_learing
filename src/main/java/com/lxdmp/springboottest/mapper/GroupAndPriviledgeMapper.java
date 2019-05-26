package com.lxdmp.springboottest.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface GroupAndPriviledgeMapper
{
	@Insert("insert into GroupWithPriviledge (groupId, priviledgeId) values(#{groupId}, #{priviledgeId})")
	void addPriviledgeToGroup(
		@Param("groupId") Integer userGroupId, 
		@Param("priviledgeId") Integer priviledgeId
	); // 用户组给予某权限

	@Delete("delete from GroupWithPriviledge where groupId=#{groupId} and priviledgeId=#{priviledgeId}")
	void delPriviledgeFromGroup(
		@Param("groupId") Integer userGroupId, 
		@Param("priviledgeId") Integer priviledgeId
	); // 用户组去除某权限
}

