package com.lxdmp.springboottest.entity;

import java.io.Serializable;
import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lxdmp.springboottest.entity.UserPriviledge;

@XmlRootElement(name="group")
public class UserGroup implements Serializable
{
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private Integer groupId;

	private String groupName;

	private List<UserPriviledge> groupPriviledges; // 该用户组具有的权限

	public UserGroup()
	{
		super();
	}

	public String toString()
	{
		String s = "";
		s += groupName;
		s += "(";
		if(groupPriviledges.isEmpty()){
			s += "no-priviledge";
		}else{
			do{
				Iterator<UserPriviledge> iter = groupPriviledges.iterator();
				if(!iter.hasNext())
					break;
				s += String.valueOf(iter.next());
				while(iter.hasNext())
					s += ", "+String.valueOf(iter.next());
			}while(false);
		}
		s += ")";
		return s;
	}

	@XmlTransient
	public Integer getGroupId(){return this.groupId;}
	public void setGroupId(Integer groupId){this.groupId=groupId;}

	public String getGroupName(){return this.groupName;}
	public void setGroupName(String groupName){this.groupName=groupName;}

	public List<UserPriviledge> getGroupPriviledges(){return this.groupPriviledges;}
	public void setGroupPriviledges(List<UserPriviledge> groupPriviledges){this.groupPriviledges=groupPriviledges;}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroup other = (UserGroup) obj;
		return (groupId==other.groupId);
	}

	@Override
	public int hashCode()
	{
		return groupId%1024;
	}
}

