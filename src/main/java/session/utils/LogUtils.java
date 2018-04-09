package session.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 日志保存操作
 * @author wsz
 *
 */
public class LogUtils {

	private static final String FileURL = "c:\\log.txt";
	
	public static boolean insetrLog(Logger logger){
		
		File file =null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			file = new File(FileURL);
			if(!file.exists()){
				file.createNewFile();
			}
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			bw.write(logger.toString());
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(null != bw)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	public static void readLog(){
		File file  = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			file = new File(FileURL);
			if(!file.exists())
				file.createNewFile();
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			String info = null;
			while((info = br.readLine()) != null){
				System.out.println(info);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != br)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		readLog();
	}
}
