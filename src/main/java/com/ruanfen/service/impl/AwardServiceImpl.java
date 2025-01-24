package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.AwardMapper;
import com.ruanfen.model.Award;
import com.ruanfen.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award> implements AwardService {
    @Autowired
    private AwardMapper awardMapper;

    @Override
    public List<Award> getAwards(int page, int pageSize, String awardType, String awardDateFrom, String awardDateTo) {
        QueryWrapper<Award> wrapper = new QueryWrapper<>();
        if (awardType != null) {
            wrapper.eq("award_type", awardType);
        }
        if (awardDateFrom != null) {
            wrapper.ge("award_date", awardDateFrom);
        }
        if (awardDateTo != null) {
            wrapper.le("award_date", awardDateTo);
        }
        Page<Award> awardPage = new Page<>(page, pageSize);
        return awardMapper.selectPage(awardPage, wrapper).getRecords();
    }

    @Override
    public Award findByAwardName(String name) {
        QueryWrapper<Award> wrapper = new QueryWrapper<>();
        wrapper.eq("award_name", name);
        return awardMapper.selectOne(wrapper);
    }

    @Override
    public void addAward(String name, String type, String date, Integer winnerId, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Award name cannot be null or empty");
        }
        Award award = new Award();
        award.setAwardName(name);
        award.setAwardType(type);
        award.setAwardDate(LocalDate.parse(date));
        award.setWinnerId(winnerId);
        award.setAwardDescription(description);
        awardMapper.insert(award);
    }
}