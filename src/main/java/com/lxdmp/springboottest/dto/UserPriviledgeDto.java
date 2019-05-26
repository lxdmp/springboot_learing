package com.lxdmp.springboottest.dto;

import java.io.Serializable;

public final class UserPriviledgeDto implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String priviledgeName;

	public UserPriviledgeDto()
	{
		super();
	}

	public String getPriviledgeName(){return this.priviledgeName;}
	public void setPriviledgeName(String priviledgeName){this.priviledgeName=priviledgeName;}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPriviledgeDto other = (UserPriviledgeDto)obj;
		if (priviledgeName == null) {
			if (other.priviledgeName != null)
				return false;
		} else if (!priviledgeName.equals(other.priviledgeName))
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime*result+((priviledgeName==null)?0:priviledgeName.hashCode());
		return result;
	}
}

