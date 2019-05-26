package com.lxdmp.springboottest.dto;

import java.io.Serializable;

public class UserDto implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String userName;
	private String userPasswd;

	public UserDto()
	{
		super();
	}

	public String getUserName(){return this.userName;}
	public void setUserName(String userName){this.userName=userName;}

	public String getUserPasswd(){return this.userPasswd;}
	public void setUserPasswd(String userPasswd){this.userPasswd=userPasswd;}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto)obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime*result+((userName==null)?0:userName.hashCode());
		return result;
	}
}

