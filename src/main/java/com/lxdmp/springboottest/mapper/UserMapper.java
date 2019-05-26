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

public interface UserMapper
{
	@Insert("insert into User(name, password) values (#{userName}, #{userPasswd})")
	@Options(useGeneratedKeys=true, keyProperty="userId")
	void addUser(User user); // 增加用户

	@Delete("delete from UserWithGroup where userId=#{userId}; delete from User where id=#{userId};")
	void delUser(Integer userId); // 删除用户

	@Update("update User set password=#{userPw} WHERE name=#{userName}")
	void updateUserPassword(
		@Param("userName") String userName, 
		@Param("userPw") String newPassword
	); // 修改用户密码

	@Select("select * from User")
	@Results({
		@Result(id=true, property="userId", column="id"), 
		@Result(property="userName", column="name"), 
		@Result(property="userPasswd", column="password"), 
		@Result(property="userGroups", column="id", 
			many=@Many(select="com.lxdmp.springboottest.mapper.UserMapper.queryUserJoinedGroups", fetchType=FetchType.LAZY)
		)
	})
	List<User> queryAllUsers(); // 查询所有用户

	@Select("select * from UserGroup where id in (select groupId from UserWithGroup where userId=#{userId})")
	@Results({
		@Result(id=true, property="groupId", column="id"), 
		@Result(property="groupName", column="name"), 
		@Result(property="groupPriviledges", column="id", 
			many=@Many(select="com.lxdmp.springboottest.mapper.UserGroupMapper.queryGroupGrantedPriviledges", fetchType=FetchType.LAZY)
		)
	})
	List<UserGroup> queryUserJoinedGroups(Integer userId); // 查询用户加入的用户组

	@Select("select * from User where name=#{userName}")
	@Results({
		@Result(id=true, property="userId", column="id"), 
		@Result(property="userName", column="name"), 
		@Result(property="userPasswd", column="password"), 
		@Result(property="userGroups", column="id", 
			many=@Many(select="com.lxdmp.springboottest.mapper.UserMapper.queryUserJoinedGroups", fetchType=FetchType.LAZY)
		)
	})
	User queryUserByName(String userName); // 查询用户

	@Select("select id from User where name=#{userName}")
	Integer queryUserIdByName(String userName); // 查询用户id
}

