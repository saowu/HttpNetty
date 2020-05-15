package org.saowu.core.db;

import org.saowu.core.annotation.Column;
import org.saowu.core.annotation.Table;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLUtils {
    /**
     * 插入sql
     *
     * @param aClass
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String insertSql(Class<T> aClass, T object) {
        //获取类注解上的表名
        Table table = aClass.getAnnotation(Table.class);
        String tableName = table.name();
        //获取属性属性注解
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuffer sql = new StringBuffer();
        StringBuffer p = new StringBuffer();
        sql.append("INSERT INTO ");
        sql.append(tableName);
        sql.append("( ");
        for (int i = 0; i < declaredFields.length; i++) {
            Column column = declaredFields[i].getAnnotation(Column.class);
            String name = column.name();
            sql.append(name);
            if (i == (declaredFields.length - 1)) {
                sql.append(" )");
            } else {
                sql.append(",");
            }
            try {
                declaredFields[i].setAccessible(true);
                Object o = declaredFields[i].get(object);
                if (o instanceof Integer || isNumeric((String) o)) {
                    p.append(o);
                } else {
                    p.append("'" + o + "'");
                }
                if (i == (declaredFields.length - 1)) {
                    p.append(" )");
                } else {
                    p.append(",");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sql.append(" VALUES (");
        sql.append(p.toString());
        System.err.println(sql.toString());
        return sql.toString();
    }

    /**
     * 查询sql
     *
     * @param aClass
     * @param map
     * @return
     */
    public static String querySql(Class<?> aClass, Map<String, Object> map) {
        //获取类注解上的表名
        Table table = aClass.getAnnotation(Table.class);
        String tableName = table.name();
        //获取属性属性注解
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        for (int i = 0; i < declaredFields.length; i++) {
            Column property = declaredFields[i].getAnnotation(Column.class);
            String name = property.name();
            sql.append(name);
            if (i == (declaredFields.length - 1)) {
                sql.append(" FROM ");
            } else {
                sql.append(",");
            }
        }
        sql.append(tableName);
        if (map != null && !map.isEmpty()) {
            sql.append(" WHERE ");
            int size = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Integer || isNumeric((String) entry.getValue())) {
                    sql.append(entry.getKey() + "=" + entry.getValue());
                } else {
                    sql.append(entry.getKey() + "= '" + entry.getValue() + "'");
                }
                size++;
                if (size < map.size()) {
                    sql.append(" AND ");
                }
            }
        }
        System.err.println(sql.toString());
        return sql.toString();
    }

    /**
     * 删除sql
     *
     * @param aClass
     * @param map
     * @return
     */
    public static String deleteSql(Class<?> aClass, Map<String, Object> map) {
        //获取类注解上的表名
        Table table = aClass.getAnnotation(Table.class);
        String tableName = table.name();
        //获取属性属性注解
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM ");
        sql.append(tableName);

        if (map != null && !map.isEmpty()) {
            sql.append(" WHERE ");
            int size = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Integer || isNumeric((String) entry.getValue())) {
                    sql.append(entry.getKey() + "=" + entry.getValue());
                } else {
                    sql.append(entry.getKey() + "= '" + entry.getValue() + "'");
                }
                size++;
                if (size < map.size()) {
                    sql.append(" AND ");
                }
            }
        }
        System.err.println(sql.toString());
        return sql.toString();
    }

    /**
     * 更新sql
     *
     * @param aClass
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String updateSql(Class<T> aClass, T object) {
        //获取类注解上的表名
        Table table = aClass.getAnnotation(Table.class);
        String tableName = table.name();
        //获取属性属性注解
        Field[] declaredFields = aClass.getDeclaredFields();
        StringBuffer sql = new StringBuffer();
        StringBuffer p = new StringBuffer();
        sql.append("UPDATE ");
        sql.append(tableName);
        sql.append(" SET ");
        for (int i = 0; i < declaredFields.length; i++) {
            Column column = declaredFields[i].getAnnotation(Column.class);
            declaredFields[i].setAccessible(true);
            String name = column.name();

            try {
                Object o = declaredFields[i].get(object);
                if (o != null) {
                    if (!"id".equals(name)) {
                        sql.append(name);
                        sql.append("=");
                        if (o instanceof Integer || isNumeric((String) o)) {
                            sql.append(o);
                        } else {
                            sql.append("'" + o + "'");
                        }
                        if (i != (declaredFields.length - 1)) {
                            sql.append(",");
                        }
                    } else {
                        p.append(name);
                        p.append("=");
                        p.append(o);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        sql.append(" WHERE ");
        sql.append(p.toString());

        System.err.println(sql.toString());
        return sql.toString();
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
