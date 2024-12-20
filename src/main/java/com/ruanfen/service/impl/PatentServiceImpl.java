package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.PatentMapper;
import com.ruanfen.model.Patent;
import com.ruanfen.service.PatentService;
import org.springframework.stereotype.Service;

@Service
public class PatentServiceImpl extends ServiceImpl<PatentMapper, Patent> implements PatentService {
}
