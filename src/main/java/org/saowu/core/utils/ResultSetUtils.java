package org.saowu.core.utils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.saowu.core.annotation.Column;
import org.saowu.core.annotation.Entity;

public class ResultSetUtils<T> {
    /**
     * ResultSet转Entity
     *
     * @param resultSet
     * @param outputClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> mapRersultSetToObject(ResultSet resultSet, Class outputClass) {
        List<T> outputList = null;
        try {
            // make sure resultset is not null
            if (resultSet != null) {
                // check if outputClass has 'Entity' annotation
                if (outputClass.isAnnotationPresent(Entity.class)) {
                    // get the resultset metadata
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    // get all the attributes of outputClass
                    Field[] fields = outputClass.getDeclaredFields();
                    while (resultSet.next()) {
                        T bean = (T) outputClass.getDeclaredConstructor().newInstance();
                        for (int i = 0; i < rsmd.getColumnCount(); i++) {
                            // getting the SQL column name
                            String columnName = rsmd.getColumnName(i + 1);
                            // reading the value of the SQL column
                            Object columnValue = resultSet.getObject(i + 1);
                            for (Field field : fields) {
                                if (field.isAnnotationPresent(Column.class)) {
                                    Column column = field.getAnnotation(Column.class);
                                    if (column.name().equalsIgnoreCase(columnName) && columnValue != null) {
                                        BeanUtils.setProperty(bean, field.getName(), columnValue);
                                        break;
                                    }
                                }
                            }
                        }
                        if (outputList == null) {
                            outputList = new ArrayList<T>();
                        }
                        outputList.add(bean);
                    }

                } else {
                    throw new RuntimeException("outputClass hasn't 'Entity' annotation");
                }
            } else {
                return null;
            }
        } catch (IllegalAccessException | InstantiationException | SQLException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return outputList;
    }
}