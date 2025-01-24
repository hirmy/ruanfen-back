package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.PortalMapper;
import com.ruanfen.mapper.ProjectMapper;
import com.ruanfen.model.Portal;
import com.ruanfen.model.Project;
import com.ruanfen.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortalServiceImpl extends ServiceImpl<PortalMapper, Portal> implements PortalService {
    @Autowired
    private PortalMapper portalMapper;
    public void addPortal(Portal portal){
        portalMapper.insert(portal);
    }

    @Override
    public Integer findPortalIdByResearcher(int researcherId) {
        // 使用 MyBatis-Plus 的 QueryWrapper 查询
        return portalMapper.selectOne(
                new QueryWrapper<Portal>()
                        .select("portal_id") // 只查询需要的字段
                        .eq("Science_id", researcherId)
        ).getPortalId();
    }
}
