package com.ruanfen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruanfen.model.Researcher;
import com.ruanfen.model.Result;
import com.ruanfen.model.User;
import com.ruanfen.service.ResearcherService;
import com.ruanfen.service.UserService;
import com.ruanfen.service.impl.MailServiceImpl;
import com.ruanfen.utils.JwtUtil;
import com.ruanfen.utils.Md5Util;
import com.ruanfen.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/researcher")
public class ResearcherController {
    @Autowired
    private ResearcherService researcherService;

    @PostMapping("/add")
    public Result addResearcher(@RequestBody Researcher researcher) {
        researcherService.addResearcher(researcher);
        return Result.success();
    }

}
