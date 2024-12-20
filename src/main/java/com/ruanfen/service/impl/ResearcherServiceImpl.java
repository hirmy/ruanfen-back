package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.ResearcherMapper;
import com.ruanfen.model.Researcher;
import com.ruanfen.service.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResearcherServiceImpl extends ServiceImpl<ResearcherMapper, Researcher> implements ResearcherService {

    @Autowired
    private ResearcherMapper researcherMapper;

    @Override
    public void addResearcher(Researcher researcher) {
        researcherMapper.insert(researcher);
    }

    public List<Researcher> searchResearchers(String name, String fieldOfResearch, String institution, Boolean claimed) {
        return researcherMapper.searchResearchers(name, fieldOfResearch, institution, claimed);
    }
}
