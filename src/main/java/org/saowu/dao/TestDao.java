package org.saowu.dao;

import org.saowu.core.annotation.Autowired;
import org.saowu.core.annotation.Repository;
import org.saowu.core.utils.PoolUtils;
import org.saowu.core.utils.ResultSetUtils;
import org.saowu.entity.Files;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TestDao {

    @Autowired
    private PoolUtils poolUtils;

    public List<Files> selectAll() {
        try {
            Connection connection = poolUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM t_files");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Files> filesList = new ResultSetUtils<Files>().mapRersultSetToObject(resultSet, Files.class);
            return filesList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
