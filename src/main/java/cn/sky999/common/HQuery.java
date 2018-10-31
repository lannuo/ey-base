package cn.sky999.common;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class HQuery implements java.io.Serializable
{
	
	private String queryString;
	
	private int pageStartNo;
	
	private int pageSize;
	
	private Map<String,Object> para;
	
	public HQuery(String hql){
		this.queryString = hql;
	}
	
	public HQuery addHqlSplit(String hqlSplit){
		queryString = (new StringBuffer(queryString)).append(hqlSplit).toString();
		return this;
	}
	
	public String getQueryString(){
		return queryString;
	}
	
	public HQuery setQueryString(String queryString){
		this.queryString = queryString;
		return this;
	}
	
	public int getPageStartNo(){
		return pageStartNo;
	}
	
	public HQuery setPageStartNo(int pageStartNo){
		this.pageStartNo = pageStartNo;
		return this;
	}
	
	public int getPageSize(){
		return pageSize;
	}
	
	public HQuery setPageSize(int pageSize){
		this.pageSize = pageSize;
		return this;
	}

	public HQuery addPara(String key,Object value){
		if(this.para==null)
			para=new HashMap<String, Object>();
		para.put(key, value);
		return this;
		
	}
	
	public Map<String, Object> getPara() {
		return para;
	}

	public void setPara(Map<String, Object> para) {
		this.para = para;
	}
}
