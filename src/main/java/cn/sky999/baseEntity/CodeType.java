package cn.sky999.baseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 代码类型
 * 
 * @author Firevery
 */
@Entity
@Table(name = "t_sys_code_type")
public class CodeType  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "TYPE_ID", nullable = false, length = 32)
	private String typeId;//
	@Column(name = "TYPE_NAME", nullable = true, length = 32)
	private String typeName;// 代码类型名称
	@Column(name = "SORT_NO", nullable = true, length = 6)
	private Integer sortNo;// 排序号
	@Column(name = "REMARK", nullable = true, length = 128)
	private String remark;// 备注

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
