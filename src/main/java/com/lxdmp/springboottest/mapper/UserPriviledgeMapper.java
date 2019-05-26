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

public interface UserPriviledgeMapper
{
	@Insert("insert into UserPriviledge(name) values(#{priviledgeName})")
	@Options(useGeneratedKeys=true, keyProperty="priviledgeId")
	void addUserPriviledge(UserPriviledge userPriviledge); // 增加用户权限

	@Delete("delete from GroupWithPriviledge where priviledgeId=#{userPriviledgeId}; delete from UserPriviledge where id=#{userPriviledgeId};")
	void delUserPriviledge(Integer userPriviledgeId); // 删除用户权限

	@Update("update UserPriviledge set name=#{userPriviledgeName} where id=#{userPriviledgeId}")
	void updateUserPriviledge(
		@Param("userPriviledgeId") Integer userPriviledgeId, 
		@Param("userPriviledgeName") String userPriviledgeName
	); // 修改用户权限名

	@Select("select * from UserPriviledge")
	@Results({
		@Result(id=true, property="priviledgeId", column="id"), 
		@Result(property="priviledgeName", column="name"), 
	})
	List<UserPriviledge> queryAllUserPriviledges(); // 查询所有用户权限

	@Select("select * from UserPriviledge where name=#{userPriviledgeName}")
	@Results({
		@Result(id=true, property="priviledgeId", column="id"), 
		@Result(property="priviledgeName", column="name"), 
	})
	UserPriviledge queryUserPriviledgeByName(String userPriviledgeName); // 查询用户权限

	@Select("select id from UserPriviledge where name=#{userPriviledgeName}")
	Integer queryUserPriviledgeIdByName(String userPriviledgeName); // 查询用户权限id

	@Select("select * from User where id in (select userId from UserWithGroup where groupId in (select id from UserGroup where id in (select groupId from GroupWithPriviledge where priviledgeId=(select id from UserPriviledge where name=#{userPriviledgeName}))))")
	@Results({
		@Result(id=true, property="userId", column="id"), 
		@Result(property="userName", column="name"), 
		@Result(property="userPasswd", column="password"), 
		@Result(property="userGroups", column="id", 
			many=@Many(select="com.lxdmp.springboottest.mapper.UserMapper.queryUserJoinedGroups", fetchType=FetchType.LAZY)
		)
	})
	List<User> queryUsersByName(String userPriviledgeName); // 查询具有该权限的用户

	@Select("select * from UserGroup where id in (select groupId from GroupWithPriviledge where priviledgeId=(select id from UserPriviledge where name=#{userPriviledgeName}))")
	@Results({
		@Result(id=true, property="groupId", column="id"), 
		@Result(property="groupName", column="name"), 
		@Result(property="groupPriviledges", column="id", 
			many=@Many(select="com.lxdmp.springboottest.mapper.UserGroupMapper.queryGroupGrantedPriviledges", fetchType=FetchType.LAZY)
		)
	})
	List<UserGroup> queryUserGroupsByName(String userPriviledgeName); // 查询具有该权限的用户组
}

