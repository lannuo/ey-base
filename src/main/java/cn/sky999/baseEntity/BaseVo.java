package cn.sky999.baseEntity;

import java.io.Serializable;
import java.util.Date;

public class BaseVo implements Serializable{
    private static final long serialVersionUID = 1L;

    private Date startDate;//开始时间
    private Date endDate;//结束时间
    private Integer deleteFlag;// 是否删除 0:删除 1:有效
    private String sortName;
    private String sortType="asc";

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
