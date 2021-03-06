package ar.com.qestudio.server.backend.util;

import java.io.InputStream;
import java.util.Properties;

public class ContextHolderUtil {

	private static Properties prop = new Properties();
	
	static {
		try {
			InputStream inputStream = ContextHolderUtil.class.getClassLoader().getResourceAsStream("qestudio-server.properties");
			prop.load(inputStream);
		} catch (Exception e) {
		}
	}
	
	private ContextHolderUtil(){}
	
	public static String getProperty(String key){
		String value = prop.getProperty(key + "." +  getSystemName());
		if(value == null){
			value = prop.getProperty(key);			
		}
		return value;
	}
	
	public static String getSystemName(){
		return System.getProperty("os.name").replaceAll(" ", "");
	}

	
}
