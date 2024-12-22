package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruanfen.model.Researcher;

import java.util.List;

public interface ResearcherService extends IService<Researcher> {
    void addResearcher(Researcher researcher);
    String getNameById(int id);
<<<<<<< HEAD
    List<Researcher> searchResearchers(String name, String fieldOfResearch, String institution, Boolean claimed);
=======
    String getNameByUrl(String url);
>>>>>>> zmk
}
