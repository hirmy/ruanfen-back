package com.ruanfen.controller;

import com.ruanfen.model.Project;
import com.ruanfen.model.Result;
import com.ruanfen.service.ProjectService;
import com.ruanfen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @PostMapping("/add")
    public Result addProject(@RequestBody Project project)
    {
        projectService.addProject(project);
        return  Result.success();
    }
    @PostMapping("/remove")
    public Result removeProject(@RequestParam("projectId") int projectId){
        if(projectService.getById(projectId)==null){
            return  Result.error("无项目");
        }
        boolean isRemoved=projectService.removeById(projectId);
        if(isRemoved)
        {
            return Result.success("项目已经移除成功");
        }else {
            return Result.error("项目移除失败，请重新操作");
        }
    }
    @PostMapping("/uodate")
    public Result updateArticle(@RequestParam("projectId") int projectId, @RequestBody Project project){
        if(projectService.getById(projectId)==null){
            return Result.error("项目不存在，更新不了");
        }
        boolean isUpdated=projectService.updateById(project);
        if(isUpdated){
            return Result.success();
        }
        return Result.error();
    }
    @GetMapping("/find")
    public Result<Project> findProject(@RequestParam("projectId") int projectId) {
        Project project = projectService.getById(projectId);
        if (project == null) {
            return Result.error("项目不存在");
        }
        return Result.success(project);
    }
    @GetMapping("allProjects")
    public Result<List<Project>> findAllProjects() {
        List<Project> projectList = projectService.list();
        return Result.success(projectList);
    }
    @GetMapping("/search")
    public Result<List<Project>> searchProject(@RequestParam(value = "project_name", required = false) String projectName,
            @RequestParam(value = "projectType", required = false) String projectType,
            @RequestParam(value = "field_of_research", required = false) String fieldOfResearch) {
        List<Project> projects = projectService.searchProjects(projectName,projectType,fieldOfResearch);
        return Result.success(projects);
    }
}
