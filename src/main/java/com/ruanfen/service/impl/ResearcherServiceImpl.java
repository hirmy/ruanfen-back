package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @Override
    public String getNameById(int id) {
        Researcher researcher = this.getById(id);  // 使用 MyBatis-Plus 获取 Patent 实体
        return researcher != null ? researcher.getName() : null;  // 获取 name 字段
    }

    @Override
    public List<Researcher> searchResearchers(String name, String fieldOfResearch, String institution, Boolean claimed) {
        return researcherMapper.searchResearchers(name, fieldOfResearch, institution, claimed);
    }
    public String getNameByUrl(String url){
        QueryWrapper<Researcher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);

        Researcher researcher = researcherMapper.selectOne(queryWrapper);
        return researcher != null ? researcher.getName() : null;

    }
}
