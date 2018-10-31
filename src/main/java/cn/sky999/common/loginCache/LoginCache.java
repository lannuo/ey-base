package cn.sky999.common.loginCache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class LoginCache implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String,Object> user;//-------------------------------登录用户信息
	
	private Object business;//---------------------------关联业务用户信息
	
	private Map<String,List<String>> roleAuthority;//----功能权限
	
	public Map<String, Object> getUser() {
		return user;
	}

	public void setUser(Map<String, Object> user) {
		this.user = user;
	}

	public Object getBusiness() {
		return business;
	}

	public void setBusiness(Object business) {
		this.business = business;
	}

	public Map<String, List<String>> getRoleAuthority() {
		return roleAuthority;
	}

	public void setRoleAuthority(Map<String, List<String>> roleAuthority) {
		this.roleAuthority = roleAuthority;
	}
}
