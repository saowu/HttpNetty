package org.saowu.service;

import org.saowu.core.annotation.Autowired;
import org.saowu.core.annotation.Service;
import org.saowu.dao.TestDao;
import org.saowu.entity.Files;

import java.util.List;
import java.util.Map;

@Service
public class TestService {
    @Autowired
    private TestDao testDao;

    public List<Files> selectFiles(Map<String, Object> map) {
        List<Files> filesList = testDao.selectFiles(map);
        return filesList;
    }

    public boolean updateFiles(Map<String, Object> map) {
        Files files = new Files();
        files.setId(((List<String>) map.get("id")).get(0));
        files.setName(((List<String>) map.get("name")).get(0));
        files.setPath(((List<String>) map.get("path")).get(0));
        files.setType(((List<String>) map.get("type")).get(0));
        boolean filesList = testDao.updateFiles(files);
        return filesList;
    }

    public boolean deteleFiles(Map<String, Object> map) {
        boolean filesList = testDao.deleteFiles(map);
        return filesList;
    }

    public boolean insertFiles(Map<String, Object> map) {
        Files files = new Files();
        files.setId(((List<String>) map.get("id")).get(0));
        files.setName(((List<String>) map.get("name")).get(0));
        files.setPath(((List<String>) map.get("path")).get(0));
        files.setType(((List<String>) map.get("type")).get(0));
        boolean filesList = testDao.insertFiles(files);
        return filesList;
    }

}
