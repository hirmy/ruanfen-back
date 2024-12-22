package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruanfen.model.Researcher;
import com.ruanfen.model.User;

public interface ResearcherService extends IService<Researcher> {
    void addResearcher(Researcher researcher);
    String getNameById(int id);
    String getNameByUrl(String url);
}
