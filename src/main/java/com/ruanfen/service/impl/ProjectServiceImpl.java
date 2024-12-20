package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.ProjectMapper;
import com.ruanfen.model.Project;
import com.ruanfen.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    public void addProject(Project project){
        projectMapper.insert(project);
    }
    public List<Project> searchProjects(String prjectName, String projectType, String fieldOfResearch){
        return projectMapper.searchProjects(prjectName,projectType,fieldOfResearch);
    }
}
