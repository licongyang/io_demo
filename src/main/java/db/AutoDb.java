package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * 
* @ClassName: AutoDb 
* @Description: 
	在启动应用程序之前，就需要检查数据库中是否已经存在要配置的数据库名称，
	如果有则不添加新的数据库名称，如果没有，则需要通过连接创建数据库。
	然后通过命令中的mysql  -uroot -p123456 testjava < /tmp/testjava.sql.
* @author lcy
* @date 2017年11月20日 上午9:47:10 
*  
 */


public class AutoDb {
	private static final Logger logger = Logger.getLogger(AutoDb.class);  
	
	 private String url = "jdbc:mysql://127.0.0.1:3306/testjava";  
	    private String user = "root";  
	    private String password = "root";  
	  
	    public void start() {  
	        try {  
	            Class.forName("com.mysql.jdbc.Driver");  
	            DriverManager.getConnection(url, user, password);  
	            logger.info("the database have exist");  
	        } catch (Exception e) {  
	            url = "jdbc:mysql://127.0.0.1:3306/test";  
	            try {  
	                Class.forName("com.mysql.jdbc.Driver");  
	                Connection conn = DriverManager.getConnection(url, user, password);  
	                Statement stat = conn.createStatement();  
	                // 创建数据库hello  
	                stat.executeUpdate("create database testjava");  
	                logger.info("creat database proudlink");  
	                stat.close();  
	                conn.close();  
	                String path = System.getProperty("user.dir") + "/webapps/JettyHost/WEB-INF/classes/config/testjava.sql";  
	                logger.info("path:" + path);  
	                String statement = "mysql  -uroot -proot proudlink_test<"+path;  
	                try {  
	                    String[] comands = new String[] { "/bin/sh", "-c", statement };  
	                    Runtime.getRuntime().exec(comands);  
	                    logger.info("import database success");  
	                } catch (Exception e2) {  
	                    logger.info("error", e2);  
	                }  
	            } catch (Exception e1) {  
	                logger.error("connect database test error",e1);  
	            }  
	        }  
	  
	    }  
	    
	    public static void main(String[] args) {
	    	AutoDb db = new AutoDb();
	    	db.start();
		}
}
