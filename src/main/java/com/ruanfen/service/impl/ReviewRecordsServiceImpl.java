package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.ReviewRecordsMapper;
import com.ruanfen.model.ReviewRecords;
import com.ruanfen.service.ReviewRecordsService;
import org.springframework.stereotype.Service;

@Service
public class ReviewRecordsServiceImpl extends ServiceImpl<ReviewRecordsMapper, ReviewRecords> implements ReviewRecordsService {
}