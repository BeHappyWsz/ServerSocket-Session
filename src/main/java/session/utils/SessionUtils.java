package session.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 操作Session
 * @author wsz
 */
public class SessionUtils {

	/**
	 * 线程安全的HashMap
	 */
	public static ConcurrentHashMap<String,Object> sessions = new ConcurrentHashMap<String, Object>(16);
	
	/**
	 * Session保存
	 * @param sessionId
	 * @param time
	 */
	public  static void  addAttribute(String sessionId, Map<String,Object> map){
		Object obj = sessions.get(sessionId);
		if(obj == null)
			sessions.put(sessionId,  map);
		else
			removeAttribute(sessionId);
	}
	
	/**
	 * Session退出
	 * @param sessionId
	 */
	public static void removeAttribute(String sessionId){
		if(sessions.size()>0)
			sessions.remove(sessionId);
	}
	
	/**
	 * Session操作判断
	 * @param sessionId
	 * @param action
	 * @param time
	 * @return
	 */
	public static boolean operation(String sessionId, String action, long startTime, long stopTime){
		boolean flag = true;
		if("start".equals(action)){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("startTime", startTime);
			map.put("stopTime", stopTime);
			map.put("time", 10000 + System.currentTimeMillis());
			SessionUtils.addAttribute(sessionId, map);
		}else if("stop".equals(action)){
			SessionUtils.removeAttribute(sessionId);
		}else{
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 获取存活session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getSessionValues(){
		StringBuffer sb = new StringBuffer();
		for(String key : sessions.keySet()){
			Map<String,Object> obj = (Map<String, Object>) sessions.get(key);
			long startTime = (Long) obj.get("startTime");
			long stopTime = (Long) obj.get("stopTime");
			long time = (Long) obj.get("time");
			long now = System.currentTimeMillis();
			
			if( startTime == 0 && stopTime == 0){
				if( now >= time)
					sessions.remove(key);
				else
					sb.append(key+",");
			}else{
				if(now > stopTime){
					sessions.remove(key);
				}else if(startTime < now && now <= stopTime){
					sb.append(key+",");
				}
			}
		}
		return sb.toString();
	}
	
	public ConcurrentHashMap<String, Object> getSessions() {
		return sessions;
	}
	
	public static void main(String[] args) {
		
		ConcurrentHashMap<String, Object> map = SessionUtils.sessions;
		for(String key: map.keySet()){
			Long obj = (Long) map.get(key);
			long now = System.currentTimeMillis();
			if(now >= obj){
				map.remove(key);
			}
		}
		
		for(String key: map.keySet()){
			System.out.println(key);
		}
	}
}
