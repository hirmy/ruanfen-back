package com.ruanfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruanfen.model.ReviewRecords;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRecordsMapper extends BaseMapper<ReviewRecords> {
}