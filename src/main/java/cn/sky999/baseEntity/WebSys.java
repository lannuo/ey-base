package cn.sky999.baseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 系统表
 * 
 * @author Firevery
 */
@Entity
@Table(name = "t_sys_web_sys")
public class WebSys implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "SYS_CODE", nullable = false, length = 36)
	private String sysCode;
	@Column(name = "SYS_NAME", nullable = true, length = 80)
	private String sysName;
	@Column(name = "SYS_URL", nullable = true, length = 200)
	private String sysUrl;
	@Column(name = "SORT_NO", nullable = true, length = 6)
	private Integer sortNo;// 排序号
	@Column(name = "REMARK", nullable = true, length = 128)
	private String remark;// 备注

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
