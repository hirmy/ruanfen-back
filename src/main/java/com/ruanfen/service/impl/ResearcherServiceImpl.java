package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.ResearcherMapper;
import com.ruanfen.mapper.UserMapper;
import com.ruanfen.model.Researcher;
import com.ruanfen.model.User;
import com.ruanfen.service.ResearcherService;
import com.ruanfen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
