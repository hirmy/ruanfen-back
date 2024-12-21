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

            //搜索无需登录(大概吧)
            "/search/article/allArticle",
            "/search/article",
            "/search/article/page",
            "/search/article/doc",

            "/search/researcher/allResearcher",
            "/search/researcher",
            "/search/researcher/page",
            "/search/researcher/doc",

            "/search/patent/allPatent",
            "/search/patent",
            "/search/patent/page",
            "/search/patent/doc",

            "/search/project/allProject",
            "/search/project",
            "/search/project/page",
            "/search/project/doc"

    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns(excludeLoginPath);
    }
}