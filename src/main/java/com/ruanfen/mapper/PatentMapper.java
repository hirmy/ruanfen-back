package com.ruanfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruanfen.model.Patent;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatentMapper extends BaseMapper<Patent> {
    List<Patent> searchPatents(@Param("patentName") String patentName,
                               @Param("keywords") String keywords,
                               @Param("fieldOfResearch") String fieldOfResearch,
                               @Param("applicationDateFrom") String applicationDateFrom,
                               @Param("applicationDateTo") String applicationDateTo);
}
