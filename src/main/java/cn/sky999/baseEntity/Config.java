package cn.sky999.baseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 基础配置实体类<br>
 * 
 * @author Firevery
 */
@Entity
@Table(name = "t_sys_config")
public class Config  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CONFIG_ID", nullable = false, length = 32)
	private String configId;//
	@Column(name = "CONFIG_NAME", nullable = true, length = 32)
	private String configName;// 参数名称
	@Column(name = "CONFIG_VALUE", nullable = true, length = 32)
	private String configValue;// 参数值
	@Column(name = "SORT_NO", nullable = true, length = 6)
	private Integer sortNo;// 排序号
	@Column(name = "REMARK", nullable = true, length = 128)
	private String remark;// 备注

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
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
