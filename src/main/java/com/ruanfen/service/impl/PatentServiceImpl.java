package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.PatentMapper;
import com.ruanfen.model.Patent;
import com.ruanfen.service.PatentService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PatentServiceImpl extends ServiceImpl<PatentMapper, Patent> implements PatentService {

    @Autowired
    private PatentMapper patentMapper;

    public void addPatent(Patent patent) {
        patentMapper.insert(patent);
    }

    @Override
    public List<Patent> searchPatents(String patentName, String keywords, String fieldOfResearch, String applicationDateFrom, String applicationDateTo) {
        return patentMapper.searchPatents(patentName, keywords, fieldOfResearch, applicationDateFrom, applicationDateTo);
    }
}
