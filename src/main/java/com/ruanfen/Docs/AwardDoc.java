package com.ruanfen.Docs;

import com.ruanfen.model.Award;
import lombok.Data;

@Data
public class AwardDoc {
    private long awardId; // award_id 对应的字段，类型为 keyword
    private String awardName; // award_name 对应的字段，类型为 text
    private String awardType; // award_type 对应的字段，类型为 text
    private String awardDate; // award_date 对应的字段，类型为 date
    private String awardDescription; // award_description 对应的字段，类型为 text

    public AwardDoc(Award award) {
        this.awardId = award.getAwardId();
        this.awardName = award.getAwardName();
        this.awardType = award.getAwardType();
        this.awardDate = award.getAwardDate().toString();
        this.awardDescription = award.getAwardDescription();
    }
}