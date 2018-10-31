package cn.sky999.baseEntity;

import cn.sky999.util.RedisUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志 <br>
 * 用于拦截器中，记录每次请求的ip，用户，操作，开始时间，结束时间
 * 
 * @author Firevery
 */
@Entity
@DynamicInsert
@DynamicUpdate(true)
@Table(name = "t_sys_log")
public class Log extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Log() {

	}

	public Log(String ip, String action) {
		this.ip = ip;
		this.action = action;
		this.status = 0;
		this.startTime = new Date();
	}

	public Log(String ip, String userId, String userName, String action) {
		this.ip = ip;
		this.userId = userId;
		this.userName = userName;
		this.action = action;
		this.status = 0;
		this.startTime = new Date();
	}

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID", nullable = false, length = 36)
	private String id;//
	@Column(name = "IP", nullable = true, length = 36)
	private String ip;//
	@Column(name = "USER_ID", nullable = true, length = 36)
	private String userId;//
	@Column(name = "USER_NAME", nullable = true, length = 36)
	private String userName;//
	@Column(name = "ACTION", nullable = true, length = 128)
	private String action;// 调用action

	@Column(name = "START_TIME", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;// 开始时间
	@Column(name = "END_TIME", nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;// 结束时间
	@Column(name = "STATUS", nullable = true, length = 1)
	private Integer status;// 执行状态 0-正常，1-异常
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CONTENT", columnDefinition = "BLOB", nullable = true)
	private byte[] content;// 异常内容

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public byte[] getContent() {
		return content;
	}

	public String getStrContent(){
		if(content!=null&&content.length>0){
String str=new String(content);
			return str;
		}
		return null;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
