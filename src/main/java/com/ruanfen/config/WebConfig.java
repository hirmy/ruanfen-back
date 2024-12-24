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
            "/api/user/login",
            "/api/user/register",
            "/api/user/sendEmail",
            "/api/user/allUser",
            "/api/user/userInfo/find",

            //搜索无需登录(大概吧)
            "/api/search/article/allArticle",
            "/api/search/article",
            "/api/search/article/page",
            "/api/search/article/doc",
            "/api/search/article/cond",
            "/api/search/article/page/order",

            "/api/search/researcher/allResearcher",
            "/api/search/researcher",
            "/api/search/researcher/page",
            "/api/search/researcher/doc",
            "/api/search/researcher/cond",
            "/api/search/researcher/page/order",


            "/api/search/patent/allPatent",
            "/api/search/patent",
            "/api/search/patent/page",
            "/api/search/patent/doc",
            "/api/search/patent/cond",
            "/api/search/patent/page/order",


            "/api/search/project/allProject",
            "/api/search/project",
            "/api/search/project/page",
            "/api/search/project/doc",
            "/api/search/project/cond",

            "/api/project/find",
            "/api/project/allProjects",
            "/api/project/search",

            "/api/comment/findById",
            "/api/comment/find",

            "/api/portal/find",
            "/api/portal/allPortals",
            "/api/portal/find/withResearcher",

            "/api/article/find",
            "/api/article/allArticles",
            "/api/article/search",
            "/api/article/find/urls",
            "/api/article/addView",

            "/api/patent/find",
            "/api/patent/search",
            "/api/patent/addView",

            "/api/researcher/find",
            "/api/researcher/search",


    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns(excludeLoginPath);
    }
}