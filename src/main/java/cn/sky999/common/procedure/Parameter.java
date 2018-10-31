package cn.sky999.common.procedure;

/**
 * 调用存储过程参数封装类
 * 
 * @author Firevery
 */
public class Parameter {

	private boolean out;// 是否输入
	private int paramType;// 参数类型，例Types.VARCHAR，OracleTypes.CURSOR
	private Object paramValue;// 参数值

	public Parameter() {

	}

	public Parameter(boolean out, int paramType, Object paramValue) {
		this.out = out;
		this.paramType = paramType;
		this.paramValue = paramValue;
	}

	public boolean isOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

	public int getParamType() {
		return paramType;
	}

	public void setParamType(int paramType) {
		this.paramType = paramType;
	}

	public Object getParamValue() {
		return paramValue;
	}

	public void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}

}
