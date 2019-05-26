package com.lxdmp.springboottest.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.mapping.FetchType;
import com.lxdmp.springboottest.entity.User;
import com.lxdmp.springboottest.entity.UserGroup;
import com.lxdmp.springboottest.entity.UserPriviledge;

public interface UserGroupMapper
{
	@Insert("insert into UserGroup(name) values(#{groupName})")
	@Options(useGeneratedKeys=true, keyProperty="groupId")
	void addUserGroup(UserGroup userGroup); // 增加用户组

	@Delete("delete from GroupWithPriviledge where groupId=#{userGroupId}; delete from UserWithGroup where groupId=#{userGroupId}; delete from UserGroup where id=#{userGroupId};")
	void delUserGroup(Integer userGroupId); // 删除用户组

	@Update("update UserGroup set name = :groupName WHERE id = :groupId")
	void updateUserGroup(
		@Param("groupId") Integer userGroupId, 
		@Param("groupName") String userGroupName
	); // 修改用户组名

	@Select("select * from UserGroup")
	@Results({
		@Result(id=true, property="groupId", column="id"), 
		@Result(property="groupName", column="name"), 
		@Result(property="groupPriviledges", column="id", 
			many=@Many(select="com.lxdmp.springboottest.mapper.UserGroupMapper.queryGroupGrantedPriviledges", fetchType=FetchType.LAZY)
		)
	})
	List<UserGroup> queryAllUserGroups(); // 查询所有用户组

	@Select("select * from UserGroup where name=#{userGroupName}")
	@Results({
		@Result(id=true, property="groupId", column="id"), 
		@Result(property="groupName", column="name"), 
		@Result(property="groupPriviledges", column="id", 
			many=@Many(select="com.lxdmp.springboottest.mapper.UserGroupMapper.queryGroupGrantedPriviledges", fetchType=FetchType.LAZY)
		)
	})
	UserGroup queryUserGroupByName(String userGroupName); // 查询用户组

	@Select("select id from UserGroup where name=#{userGroupName}")
	Integer queryUserGroupIdByName(String userGroupName); // 查询用户组id

	@Select("select * from User where id in (select userId from UserWithGroup where groupId=(select id from UserGroup where name=#{userGroupName}))")
	@Results({
		@Result(id=true, property="userId", column="id"), 
		@Result(property="userName", column="name"), 
		@Result(property="userPasswd", column="password"), 
		@Result(property="userGroups", column="id", 
			many=@Many(select="com.lxdmp.springboottest.mapper.UserMapper.queryUserJoinedGroups", fetchType=FetchType.LAZY)
		)
	})
	List<User> queryUsersByName(String userGroupName); // 查询加入用户组的用户

	@Select("select * from UserPriviledge where id in (select priviledgeId from GroupWithPriviledge where groupId=#{userGroupId})")
	@Results({
		@Result(id=true, property="priviledgeId", column="id"), 
		@Result(property="priviledgeName", column="name"), 
	})
	List<UserPriviledge> queryGroupGrantedPriviledges(Integer userGroupId); // 查询用户组赋予的权限 
}

