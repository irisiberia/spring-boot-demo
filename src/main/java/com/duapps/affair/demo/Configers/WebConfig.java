package com.duapps.affair.demo.Configers;import com.duapps.affair.demo.intercepters.ApiAuthIntercepter;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Configuration;import org.springframework.web.servlet.config.annotation.InterceptorRegistry;import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;/** * @Author he.zhou * @Date 2020-08-17 *///@Configurationpublic class WebConfig implements WebMvcConfigurer {    @Autowired    private ApiAuthIntercepter apiAuthIntercepter;    @Override    public void addInterceptors(InterceptorRegistry registry) {        registry.addInterceptor(apiAuthIntercepter).addPathPatterns("/users/**")                .excludePathPatterns("/users/on1");    }}