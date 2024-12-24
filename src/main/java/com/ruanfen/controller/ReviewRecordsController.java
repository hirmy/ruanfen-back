package com.ruanfen.controller;

import com.ruanfen.model.ReviewRecords;
import com.ruanfen.model.Result;
import com.ruanfen.service.ReviewRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review-records")
public class ReviewRecordsController {
    @Autowired
    private ReviewRecordsService reviewRecordsService;

    @PostMapping("/add")
    public Result addReviewRecord(@RequestBody ReviewRecords reviewRecords) {
        boolean isSaved = reviewRecordsService.save(reviewRecords);
        if (isSaved) {
            return Result.success("Review record created successfully");
        } else {
            return Result.error("Failed to create Review record");
        }
    
    }

    @DeleteMapping("/{recordId}")
    public Result deleteReviewRecord(@PathVariable Integer recordId) {
        boolean isRemoved = reviewRecordsService.removeById(recordId);
        if (isRemoved) {
            return Result.success("Review record deleted successfully");
        }
        return Result.error("Review record deletion failed, please try again");
    }

    @PutMapping("/{recordId}")
    public Result updateReviewRecord(@PathVariable Integer recordId, @RequestBody ReviewRecords reviewRecords) {
        reviewRecords.setRecordId(recordId);
        boolean isUpdated = reviewRecordsService.updateById(reviewRecords);
        if (isUpdated) {
            return Result.success("Review record updated successfully");
        }
        return Result.error("Review record update failed, please try again");
    }

    @GetMapping("/{recordId}")
    public Result<ReviewRecords> getReviewRecord(@PathVariable Integer recordId) {
        ReviewRecords reviewRecords = reviewRecordsService.getById(recordId);
        if (reviewRecords == null) {
            return Result.error("Review record not found");
        }
        return Result.success(reviewRecords);
    }

    @GetMapping
    public Result<List<ReviewRecords>> getAllReviewRecords() {
        List<ReviewRecords> reviewRecordsList = reviewRecordsService.list();
        return Result.success(reviewRecordsList);
    }
}