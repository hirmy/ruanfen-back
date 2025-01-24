package com.ruanfen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruanfen.model.Article;
import com.ruanfen.model.Researcher;
import com.ruanfen.model.Result;
import com.ruanfen.model.User;
import com.ruanfen.service.ResearcherService;
import com.ruanfen.service.SearchService;
import com.ruanfen.service.UserService;
import com.ruanfen.service.impl.MailServiceImpl;
import com.ruanfen.utils.JwtUtil;
import com.ruanfen.utils.Md5Util;
import com.ruanfen.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/researcher")
public class ResearcherController {
    @Autowired
    private ResearcherService researcherService;

    @Autowired
    private SearchService searchService;

    @PostMapping("/add")
    public Result addResearcher(@RequestBody Researcher researcher) throws IOException {
        researcherService.addResearcher(researcher);

        searchService.addResearcherDoc(researcher);
        return Result.success("研究人员添加成功");
    }

    @DeleteMapping("/remove")
    public Result removeResearcher(@RequestParam("researcherId") int researcherId) throws IOException {
        if (researcherService.getById(researcherId) == null) {
            return Result.error("研究人员不存在，无法删除");
        }

        boolean isRemoved = researcherService.removeById(researcherId);
        if (isRemoved) {
            searchService.removeResearcherDoc(researcherId);
            return Result.success("研究人员删除成功");
        }
        return Result.error("研究人员删除失败，请重试");
    }

    @PutMapping("/update")
    public Result updateResearcher(@RequestParam("researcherId") int researcherId, @RequestBody Researcher researcher) throws IOException {
        if (researcherService.getById(researcherId) == null) {
            return Result.error("研究人员不存在，无法更新");
        }
        boolean isUpdated = researcherService.updateById(researcher);
        if (isUpdated) {
            searchService.updateResearcherDoc(researcherId, researcher);
            return Result.success("研究人员信息更新成功");
        }
        return Result.error("研究人员信息更新失败，请重试");
    }

    @GetMapping("/find")
    public Result<Researcher> findResearcher(@RequestParam("researcherId") int researcherId) {
        Researcher researcher = researcherService.getById(researcherId);
        if (researcher == null) {
            return Result.error("研究人员不存在");
        }
        return Result.success(researcher);
    }

    @GetMapping("/search")
    public Result<List<Researcher>> searchResearcher(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "field_of_research", required = false) String fieldOfResearch,
            @RequestParam(value = "institution", required = false) String institution,
            @RequestParam(value = "claimed", required = false) Boolean claimed) {

        List<Researcher> researchers = researcherService.searchResearchers(name, fieldOfResearch, institution, claimed);
        return Result.success(researchers);
    }
}
