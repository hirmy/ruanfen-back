package com.ruanfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruanfen.model.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {

    List<Project> searchProjects(@Param("projectName") String projectName,
                                 @Param("projectType") String projectType,
                                 @Param("fieldOfResearch") String fieldOfResearch);
}
