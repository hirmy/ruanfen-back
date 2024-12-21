package com.ruanfen.controller;

import com.ruanfen.model.Patent;
import com.ruanfen.model.Researcher;
import com.ruanfen.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruanfen.model.Result;

import java.util.List;

@RestController
@RequestMapping("/patent")
public class PatentController {
    @Autowired
    private PatentService patentService;


    @PostMapping("/add")
    public Result addPatent(@RequestBody Patent patent) {
        patentService.addPatent(patent);
        return Result.success();
    }

    @DeleteMapping("/remove")
    public Result removePatent(@RequestParam("patentId") int patentId) {
        if (patentService.getById(patentId) == null) {
            return Result.error("专利不存在，无法删除");
        }
        boolean isRemoved = patentService.removeById(patentId);

        if (isRemoved) {
            return Result.success("文章删除成功");
        }
        return Result.error("文章删除失败，请重试");
    }

    @PutMapping("/update")
    public Result updatePatent(@RequestParam("patentId") int patentId, @RequestBody Patent patent) {
        if (patentService.getById(patentId) == null) {
            return Result.error("专利不存在，无法更新");
        }

        boolean isUpdated = patentService.updateById(patent);
        if (isUpdated) {
            return Result.success("专利信息更新成功");
        }
        return Result.error("专利信息更新失败，请重试");
    }

    @GetMapping("/find")
    public Result<Patent> findPatent(@RequestParam("patentId") int patentId) {
        Patent patent = patentService.getById(patentId);
        if (patent == null) {
            return Result.error("专利不存在");
        }
        return Result.success(patent);
    }

    @GetMapping("/search")
    public Result<List<Patent>> searchPatent(
            @RequestParam(value = "patentName", required = false) String patentName,
            @RequestParam(value = "keywords", required = false) String keywords,
            @RequestParam(value = "fieldOfResearch", required = false) String fieldOfResearch,
            @RequestParam(value = "applicationDateFrom", required = false) String applicationDateFrom,
            @RequestParam(value = "applicationDateTo", required = false) String applicationDateTo
    ) {
        List<Patent> patents = patentService.searchPatents(patentName, keywords, fieldOfResearch, applicationDateFrom, applicationDateTo);
        return Result.success(patents);
    }
}
