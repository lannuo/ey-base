package cn.sky999.baseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 代码信息
 * 
 * @author Firevery
 */
@Entity
@Table(name = "t_sys_code_info")
public class CodeInfo  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "INFO_ID", nullable = false, length = 32)
	private String infoId;//
	@Column(name = "TYPE_ID", nullable = true, length = 32)
	private String typeId;// 代码类型
	@Column(name = "INFO_VALUE", nullable = true, length = 128)
	private String infoValue;// 值
	@Column(name = "INFO_CONTENT", nullable = true, length = 128)
	private String infoContent;// 内容
	@Column(name = "SORT_NO", nullable = true, length = 6)
	private Integer sortNo;// 排序号
	@Column(name = "REMARK", nullable = true, length = 128)
	private String remark;// 备注

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getInfoValue() {
		return infoValue;
	}

	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
	}

	public String getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
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
