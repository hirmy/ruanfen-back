package com.ruanfen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruanfen.model.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {
    @Select({
            "<script>",
            "SELECT * FROM project",
            "WHERE 1 = 1",
            "<if test='projectName != null'> AND project_name LIKE CONCAT('%', #{articleName}, '%') </if>",
            "<if test='projectType != null'> AND project_Type LIKE CONCAT('%', #{projectType}, '%') </if>",
            "<if test='fieldOfResearch != null'> AND field_of_research LIKE CONCAT('%', #{fieldOfResearch}, '%') </if>",
            "</script>"
    })
    List<Project> searchProjects(@Param("projectName") String projectName,
                                 @Param("projectType") String projectType,
                                 @Param("fieldOfResearch") String fieldOfResearch);
}
