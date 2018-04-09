package session.utils;
/**
 * 接收对象
 * @author wsz
 *
 */
public class SessionObj {
	
	/**
	 * sessionId
	 */
	private String sessionId;
	
	/**
	 * 动作类型 start/stop
	 */
	private String action;
	
	/**
	 * 并发参数
	 */
	private String num;
	
	/**
	 * 开始时间
	 */
	private long startTime;
	
	/**
	 * 结束时间
	 */
	private long stopTime;
	/**
	 * 版本信息
	 */
	private String version;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getStopTime() {
		return stopTime;
	}
	public void setStopTime(long stopTime) {
		this.stopTime = stopTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
