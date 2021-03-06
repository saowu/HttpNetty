package org.saowu.core.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ORMUtils {


    /**
     * @param poolUtils 连接池
     * @param aClass    映射类
     * @param map       映射参数
     * @param <T>
     * @return
     */
    public static <T> List<T> executeQuery(PoolUtils poolUtils, Class<T> aClass, Map<String, Object> map) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = poolUtils.getConnection();
            preparedStatement = connection.prepareStatement(SQLUtils.querySql(aClass, map));
            resultSet = preparedStatement.executeQuery();
            List<T> list = ResultSetUtils.mapRersultSetToObject(resultSet, aClass);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolUtils.close(connection, preparedStatement, resultSet);
        }
        return null;
    }


    /**
     * @param poolUtils
     * @param aClass
     * @param t
     * @param <T>
     * @return
     */
    public static <T> int executeInsert(PoolUtils poolUtils, Class<T> aClass, T t) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = poolUtils.getConnection();
            preparedStatement = connection.prepareStatement(SQLUtils.insertSql(aClass, t));
            int execute = preparedStatement.executeUpdate();
            return execute;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolUtils.close(connection, preparedStatement, null);
        }
        return 0;
    }


    /**
     * @param poolUtils
     * @param aClass
     * @param map
     * @param <T>
     * @return
     */
    public static <T> int executeDelete(PoolUtils poolUtils, Class<T> aClass, Map<String, Object> map) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = poolUtils.getConnection();
            preparedStatement = connection.prepareStatement(SQLUtils.deleteSql(aClass, map));
            int execute = preparedStatement.executeUpdate();
            return execute;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolUtils.close(connection, preparedStatement, null);
        }
        return 0;
    }


    /**
     * 更新
     *
     * @param poolUtils 连接池
     * @param <T>       映射类
     * @return 映射参数
     */


    /**
     * @param poolUtils
     * @param aClass
     * @param t
     * @param <T>
     * @return
     */
    public static <T> int executeUpdate(PoolUtils poolUtils, Class<T> aClass, T t) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = poolUtils.getConnection();
            preparedStatement = connection.prepareStatement(SQLUtils.updateSql(aClass, t));
            int execute = preparedStatement.executeUpdate();
            return execute;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            poolUtils.close(connection, preparedStatement, null);
        }
        return 0;
    }


}
