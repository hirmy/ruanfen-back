package com.ruanfen.config;

import com.ruanfen.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    private String[] excludeLoginPath = new String[]{
            "/user/login",
            "/user/register",
            "/user/sendEmail",
            "/user/allUser",

            //搜索无需登录(大概吧)
            "/search/article/allArticle",
            "/search/article",
            "/search/article/page",
            "/search/article/doc",
            "/search/article/cond",

            "/search/researcher/allResearcher",
            "/search/researcher",
            "/search/researcher/page",
            "/search/researcher/doc",
            "/search/researcher/cond",


            "/search/patent/allPatent",
            "/search/patent",
            "/search/patent/page",
            "/search/patent/doc",
            "/search/patent/cond",


            "/search/project/allProject",
            "/search/project",
            "/search/project/page",
            "/search/project/doc",
            "/search/project/cond",

            "/project/find",
            "/project/allProjects",
            "/project/search",

            "/comment/findById",
            "/comment/find",

            "/portal/find",
            "/portal/allPortals",

            "/article/find",
            "/article/allArticles",
            "/article/search",

            "/patent/find",
            "/patent/search",

            "/researcher/find",
            "/researcher/search",


    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns(excludeLoginPath);
    }
}