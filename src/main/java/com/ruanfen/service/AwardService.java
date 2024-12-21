package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruanfen.model.Award;

import java.util.List;

public interface AwardService extends IService<Award> {
    List<Award> getAwards(int page, int pageSize, String awardType, String awardDateFrom, String awardDateTo);
    Award findByAwardName(String name);
    void addAward(String name, String type, String date, Integer winnerId, String description);
}