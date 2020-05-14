package org.saowu.core.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 初始化数据库链接池
 */
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
    }

    public Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}