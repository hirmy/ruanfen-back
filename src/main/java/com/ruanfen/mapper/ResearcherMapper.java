package com.ruanfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruanfen.model.Researcher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Repository
public interface ResearcherMapper extends BaseMapper<Researcher> {
    List<Researcher> searchResearchers(@Param("name") String name,
                                       @Param("fieldOfResearch") String fieldOfResearch,
                                       @Param("institution") String institution,
                                       @Param("claimed") Boolean claimed);
}