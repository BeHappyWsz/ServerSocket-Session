package session.utils;
/**
 * 日志对象
 * @author wsz
 */
public class Logger {

	/**
	 * 发送时间
	 */
	private String time;
	/**
	 * 发送url
	 */
	private String url;
	/**
	 * HTTP body
	 */
	private String body;
	/**
	 * 连接信息
	 */
	private String message;
	
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Logger [time=" + time + ", url=" + url + ", body=" + body + ", message=" + message + "]";
	}
	
}
