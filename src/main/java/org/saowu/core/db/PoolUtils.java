package org.saowu.core.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.saowu.core.annotation.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 初始化数据库链接池
 */
@Component
public class PoolUtils {

    private HikariDataSource hikariDataSource;

    public PoolUtils() {
        try {
            InputStream inputStream = PoolUtils.class.getClassLoader().getResourceAsStream("hikari.properties");
            Properties props = new Properties();
            if (inputStream != null) {
                props.load(inputStream);
            }
            HikariConfig hikariConfig = new HikariConfig(props);
            hikariDataSource = new HikariDataSource(hikariConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("Http Netty : PoolUtils Initialization complete");
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭资源
     *
     * @param conn
     * @param stmt
     * @param rs
     */
    public void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {   // 关闭记录集
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {   // 关闭声明
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {  // 关闭连接对象
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
