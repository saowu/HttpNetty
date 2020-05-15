package org.saowu.dao;

import org.saowu.core.annotation.Autowired;
import org.saowu.core.annotation.Repository;
import org.saowu.core.db.ORMUtils;
import org.saowu.core.db.PoolUtils;
import org.saowu.entity.Files;

import java.util.List;
import java.util.Map;

@Repository
public class TestDao {

    @Autowired
    private PoolUtils poolUtils;

    public List<Files> selectFiles(Map<String, Object> map) {
        List<Files> filesList = ORMUtils.executeQuery(poolUtils, Files.class, map);
        return filesList;
    }

    public boolean updateFiles(Files files) {
        int i = ORMUtils.executeUpdate(poolUtils, Files.class, files);
        return i > 0;
    }

    public boolean insertFiles(Files files) {
        int i = ORMUtils.executeInsert(poolUtils, Files.class, files);
        return i > 0;
    }

    public boolean deleteFiles(Map<String, Object> map) {
        int i = ORMUtils.executeDelete(poolUtils, Files.class, map);
        return i > 0;
    }

}
