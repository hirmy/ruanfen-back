package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruanfen.model.Portal;
import com.ruanfen.model.Project;

public interface PortalService extends IService<Portal> {
    void addPortal(Portal portal);
    Integer findPortalIdByResearcher(int researcherId);
}
