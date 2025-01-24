package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.ruanfen.model.Article;
import com.ruanfen.model.Patent;
import com.ruanfen.model.Researcher;

import java.util.List;

public interface PatentService extends IService<Patent> {
    void addPatent(Patent patent);
    List<Patent> searchPatents(String patentName, String keywords, String fieldOfResearch, String applicationDateFrom, String applicationDateTo);

}
