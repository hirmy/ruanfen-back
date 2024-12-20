package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruanfen.model.Project;

import java.util.List;

public interface ProjectService extends IService<Project> {
    void addProject(Project project);
    List<Project> searchProjects(String prjectName, String keywords, String fieldOfResearch, String publishTimeFrom, String publishTimeTo);
}
