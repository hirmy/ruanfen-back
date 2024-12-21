package com.ruanfen.service.impl;

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
}
