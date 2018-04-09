package session.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import session.utils.LogUtils;
import session.utils.Logger;
import session.utils.SessionUtils;
/**
 * 多线程的实现
 * @author wsz
 */
public class ServerThread extends Thread{
	
	private Socket socket = null;
	
	public ServerThread(Socket socket){
		this.socket = socket;
	}
	
	/**
	 * 绑定固定的url路径
	 */
	private static final String URL = "/nbi/deliverysession";
	
	@Override
	public void run() {
		InputStreamReader isr = null;
		Logger logger = new Logger();//日志保存对象
		try {
			logger.setTime(new Date().toString());
			
			isr = new InputStreamReader(socket.getInputStream(),"UTF-8");
			String[] httpMsg = getHttpMsg(isr);
			String url = getURL(httpMsg);
			logger.setUrl(url);
			String msg = "";
			for (String str : httpMsg) {
				msg += str+" ";
			}
			logger.setBody(msg);
			
			if(!url.contains(URL)){
				returnParams("请求错误");
				logger.setMessage("请求错误");
			}else{
				Map<String, Object> params = paramsMap(getParams(httpMsg));
				
				String sessionId = (String) params.get("id");
				String action = (String) params.get("action");
				long startTime = params.get("startTime") == null? 0 : Long.valueOf((String) params.get("startTime"));
				long stopTime = params.get("stopTime") == null ? 0 : Long.valueOf((String) params.get("stopTime"));
				SessionUtils.operation(sessionId, action, startTime, stopTime);
				returnParams("存活Session:"+SessionUtils.getSessionValues());
				logger.setMessage("请求成功");
			}
			
		} catch (IOException e) {
			try {
				e.printStackTrace();
				returnParams("请求错误");
				logger.setMessage(e.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally{
			try {
				if(socket != null || !socket.isClosed())
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		LogUtils.insetrLog(logger);
	}
	
	
	/**
	 * 返回结果
	 * @param value
	 * @throws IOException
	 */
	public void returnParams(String value) throws IOException{
			socket.getOutputStream().write(("HTTP/1.1 200 OK\r\n" +  //响应头第一行
			          "Content-Type: text/html; charset=utf-8\r\n" +  //简单放一个头部信息
			          "\r\n" +  //这个空行是来分隔请求头与请求体的
			          "<h1>"+value+"</h1>\r\n").getBytes());
	}
	
	/**
	 * 获取HTTP信息
	 * @param isr
	 * @return
	 * @throws IOException
	 */
	public String[] getHttpMsg(InputStreamReader isr) throws IOException{
		StringBuilder builder = new StringBuilder();
		char[] charBuf = new char[1024];
        int mark = -1;
        while ((mark = isr.read(charBuf)) != -1) {
            builder.append(charBuf, 0, mark);
            if (mark < charBuf.length) {
                break;
            }
        }
        String[] splits = builder.toString().split("\r\n");
        return splits;
	}
	
	/**
	 * 获取URL信息
	 * @param httpMsg
	 * @return
	 */
	public String getURL(String[] httpMsg){
		String one = httpMsg[0];
		String two = httpMsg[1];
		if(one.indexOf(URL) == -1)
			return "";
		return two.substring(6)+one.substring(one.indexOf(URL), one.lastIndexOf("HTTP")-1);
	}
	
	/**
	 * 获取完整参数字符串
	 * @param firstStr
	 * @return
	 */
	public String getParams(String[] httpMsg){
		String url = getURL(httpMsg);
        return url.substring(url.indexOf("?") + 1);
	}
	
	/**
	 * 获取格式化后的参数
	 * @param params
	 *  action:start/stop
	 *  time:startTime/stopTime
	 * @return
	 */
	public Map<String,Object> paramsMap(String params){
		Map<String,Object> map = new HashMap<String, Object>(16);
		String[] split = params.split("&");
		for (String str : split) {
			String[] p = str.split("=");
			map.put(p[0], p[1]);
		}
		return map;
	}
}
