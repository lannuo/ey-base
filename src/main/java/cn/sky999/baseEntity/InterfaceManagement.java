package cn.sky999.baseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 接口管理实体类
 * 
 * @author Firevery
 */
@Entity
@Table(name = "t_sys_interface_management")
public class InterfaceManagement implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IF_MANAGEMENT_ID", nullable = false, length = 32)
	private String ifManagementId;// 接口ID
	@Column(name = "IF_MANAGEMENT_NAME", nullable = true, length = 32)
	private String ifManagementName;// 接口名称
	@Column(name = "IF_MANAGEMENT_LINK", nullable = true, length = 32)
	private String ifManagementLink;//接口链接
	@Column(name = "IF_MANAGEMENT_WAY", nullable = true, length = 16)
	private String ifManagementWay;//调用方式方式
	
	public String getIfManagementWay() {
		return ifManagementWay;
	}
	public void setIfManagementWay(String ifManagementWay) {
		this.ifManagementWay = ifManagementWay;
	}
	public String getIfManagementId() {
		return ifManagementId;
	}
	public void setIfManagementId(String ifManagementId) {
		this.ifManagementId = ifManagementId;
	}
	public String getIfManagementName() {
		return ifManagementName;
	}
	public void setIfManagementName(String ifManagementName) {
		this.ifManagementName = ifManagementName;
	}
	public String getIfManagementLink() {
		return ifManagementLink;
	}
	public void setIfManagementLink(String ifManagementLink) {
		this.ifManagementLink = ifManagementLink;
	}
	
}
