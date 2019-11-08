package cn.qf.util;

/**
 * Description：DBCP连接池操作工具类<br/>
 * Copyright (c) ,2019 , Xuefengtao <br/>
 * This program is protected by copyright laws. <br/>
 * Date： 2019年11月04日
 *
 * @author 陶雪峰
 * @version : 1.0
 */

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @ program: scala_studying
 * @ author:  TaoXueFeng
 * @ create: 2019-11-04 17:02
 * @ desc:  
 **/

public class DBCPUtil {
    private static DataSource pool;

    static {
        Properties properties = new Properties();
        try {
            properties.load(DBCPUtil.class.getClassLoader().getResourceAsStream("dbcp.properties"));
            pool = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getConnectionPool() {
        return pool;
    }
    public static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

}
