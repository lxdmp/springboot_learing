package com.lxdmp.springboottest.dto;

import java.io.Serializable;

public final class UserGroupDto implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String groupName;

	public UserGroupDto()
	{
		super();
	}

	public String getGroupName(){return this.groupName;}
	public void setGroupName(String groupName){this.groupName=groupName;}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroupDto other = (UserGroupDto)obj;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime*result+((groupName==null)?0:groupName.hashCode());
		return result;
	}
}

