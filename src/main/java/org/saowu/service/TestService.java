package org.saowu.service;

import org.saowu.core.annotation.Autowired;
import org.saowu.core.annotation.Service;
import org.saowu.dao.TestDao;
import org.saowu.entity.Files;

import java.util.List;
@Service
public class TestService {
    @Autowired
    private TestDao testDao;

    public List<Files> selectAll() {
        List<Files> filesList = testDao.selectAll();
        return filesList;
    }

}
