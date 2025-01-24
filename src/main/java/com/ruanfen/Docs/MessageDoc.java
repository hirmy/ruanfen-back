package com.ruanfen.Docs;

import com.ruanfen.model.Message;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDoc {
    private long messageId; // message_id 对应的字段，类型为 keyword
    private int senderId; // sender_id 对应的字段，类型为 integer
    private int receiverId; // receiver_id 对应的字段，类型为 integer
    private String category; // category 对应的字段，类型为 text
    private String title; // title 对应的字段，类型为 text
    private String content; // content 对应的字段，类型为 text
    private LocalDateTime createdTime; // created_time 对应的字段，类型为 date
    private LocalDateTime updatedTime; // updated_time 对应的字段，类型为 date
    private boolean ifConfirmed; // if_confirmed 对应的字段，类型为 boolean

    public MessageDoc(Message message) {
        this.messageId = message.getMessageId();
        this.senderId = message.getSenderId();
        this.receiverId = message.getReceiverId();
        this.category = message.getCategory();
        this.title = message.getTitle();
        this.content = message.getContent();
        this.createdTime = message.getCreatedTime();
        this.updatedTime = message.getUpdatedTime();
        this.ifConfirmed = message.getIfConfirmed();
    }
}