package com.ruanfen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruanfen.model.Award;
import com.ruanfen.model.Result;
import com.ruanfen.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/awards")
public class AwardController {
    @Autowired
    private AwardService awardService;

    @PostMapping("/add")
    public Result<Award> addAward(@RequestBody Award award) {
        boolean isSaved = awardService.save(award);
        if (isSaved) {
            return Result.success("Award created successfully", award);
        } else {
            return Result.error("Failed to create award");
        }
    }

    @DeleteMapping("/{awardId}")
    public Result deleteAward(@PathVariable Integer awardId) {
        
        boolean isRemoved = awardService.removeById(awardId);
        if (isRemoved) {
            return Result.success("Award deleted successfully");
        } else {
            return Result.error("Failed to delete award");
        }
    }

    @PutMapping("/{awardId}")
    public Result updateAward(@PathVariable Integer awardId, @RequestBody Award award) {
        award.setAwardId(awardId);
        boolean isUpdated = awardService.updateById(award);
        if (isUpdated) {
            return Result.success("Award updated successfully");
        } else {
            return Result.error("Failed to update award");
        }
    }

    @GetMapping("/{awardId}")
    public Result<Award> getAward(@PathVariable Integer awardId) {
        Award award = awardService.getById(awardId);
        if (award != null) {
            return Result.success(award);
        } else {
            return Result.error("Award not found");
        }
    }

    @GetMapping
    public Result<List<Award>> getAwards(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         @RequestParam(required = false) String awardType,
                                         @RequestParam(required = false) String awardDateFrom,
                                         @RequestParam(required = false) String awardDateTo) {
        QueryWrapper<Award> wrapper = new QueryWrapper<>();
        if (awardType != null) {
            wrapper.eq("award_type", awardType);
        }
        if (awardDateFrom != null) {
            wrapper.ge("award_date", awardDateFrom);
        }
        if (awardDateTo != null) {
            wrapper.le("award_date", awardDateTo);
        }
        List<Award> awards = awardService.list(wrapper);
        return Result.success(awards);
    }
}