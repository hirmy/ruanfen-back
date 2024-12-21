package com.ruanfen.controller;

import com.ruanfen.model.Message;
import com.ruanfen.model.Result;
import com.ruanfen.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/add")
    public Result addMessage(@RequestBody Message message) {
        messageService.save(message);
        return Result.success("Message sent successfully", message.getMessageId());
    }

    @DeleteMapping("/{messageId}")
    public Result deleteMessage(@PathVariable Integer messageId) {
        boolean isRemoved = messageService.removeById(messageId);
        if (isRemoved) {
            return Result.success("Message deleted successfully");
        }
        return Result.error("Message deletion failed, please try again");
    }

    @PutMapping("/{messageId}/confirm")
    public Result confirmMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        message.setMessageId(messageId);
        boolean isUpdated = messageService.updateById(message);
        if (isUpdated) {
            return Result.success("Message status updated successfully");
        }
        return Result.error("Message status update failed, please try again");
    }

    @GetMapping("/{messageId}")
    public Result<Message> getMessage(@PathVariable Integer messageId) {
        Message message = messageService.getById(messageId);
        if (message == null) {
            return Result.error("Message not found");
        }
        return Result.success(message);
    }

    @GetMapping
    public Result<List<Message>> getAllMessages() {
        List<Message> messages = messageService.list();
        return Result.success(messages);
    }
}