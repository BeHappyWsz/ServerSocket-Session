package session.start;

import session.server.Server;
/**
 * 启动类-启动监听
 * @author wsz
 */
public class StartProject {

	/**
	 * http://127.0.0.1:8888/nbi/deliverysession?id=324&action=start
	 */
	public static void main(String[] args) {
		Server.startServer();
	}
}
