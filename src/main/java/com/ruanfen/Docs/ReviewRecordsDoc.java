package com.ruanfen.Docs;

import com.ruanfen.model.ReviewRecords;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewRecordsDoc {
    private long recordId; // record_id 对应的字段，类型为 keyword
    private int createrId; // creater_id 对应的字段，类型为 integer
    private int reviewerId; // reviewer_id 对应的字段，类型为 integer
    private LocalDateTime reviewDate; // review_date 对应的字段，类型为 date
    private String requestType; // request_type 对应的字段，类型为 text
    private String action; // action 对应的字段，类型为 text
    private String coment; // coment 对应的字段，类型为 text

    public ReviewRecordsDoc(ReviewRecords reviewRecords) {
        this.recordId = reviewRecords.getRecordId();
        this.createrId = reviewRecords.getCreaterId();
        this.reviewerId = reviewRecords.getReviewerId();
        this.reviewDate = reviewRecords.getReviewDate();
        this.requestType = reviewRecords.getRequestType();
        this.action = reviewRecords.getAction();
        this.coment = reviewRecords.getComent();
    }
}