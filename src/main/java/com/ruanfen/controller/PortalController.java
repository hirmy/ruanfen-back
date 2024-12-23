package com.ruanfen.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruanfen.model.Portal;
import com.ruanfen.model.Result;
import com.ruanfen.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/portal")
public class
PortalController {
    @Autowired
    private PortalService portalService;
    @PostMapping("/add")
    public Result addPortal(@RequestBody Portal portal)
    {
        portalService.addPortal(portal);
        return  Result.success();
    }

    @PostMapping("/remove")
    public Result removePortal(@RequestParam("portalId") int portalId){
        if(portalService.getById(portalId)==null){
            return  Result.error("无门户");
        }
        boolean isRemoved=portalService.removeById(portalId);
        if(isRemoved)
        {
            return Result.success("门户已经移除成功");
        }else {
            return Result.error("门户移除失败，请重新操作");
        }
    }
    @GetMapping("/find")
    public Result<Portal> findPortal(@RequestParam("portalId") int portalId) {
        Portal portal = portalService.getById(portalId);
        if (portal == null) {
            return Result.error("门户不存在");
        }
        return Result.success(portal);
    }
    @PostMapping("/update")
    public Result updatePortal(@RequestParam("portalId") int portalId, @RequestBody Portal portal){
        if(portalService.getById(portalId)==null){
            return Result.error("门户不存在，更新不了");
        }
        boolean isUpdated=portalService.updateById(portal);
        if(isUpdated){
            return Result.success();
        }
        return Result.error();
    }
    // 获取所有门户信息
    @GetMapping("/allPortals")
    public Result<List<Portal>> getAllPortals() {
        List<Portal> portalList = portalService.list();
        return Result.success(portalList);
    }

    @GetMapping("/find/withResearcher")
    //根据researcherId 找portal
    public Result<Integer> findPortalIdByResearcher(@RequestParam int researcherId){
        // 调用服务层方法查询
        Integer portalId = portalService.findPortalIdByResearcher(researcherId);

        if (portalId != null) {
            return Result.success(portalId); // 成功返回 Portal ID
        } else {
            return Result.error("未找到对应的 Portal");
        }
    }
}
