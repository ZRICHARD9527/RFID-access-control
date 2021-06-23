package com.hasakiii.rfid.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Z.Richard
 * @CreateTime: 2020/11/19 13:19
 * @Description:
 **/

@Configuration
public class DruidConfig {

    /**
     * 将自定义的容器传到SpringBoot中
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控功能 相当于 web.xml
    //因为SpringBoot内置了Servlet容器，所以没有web.xml ，替代方法ServletRegistrationBean
    @Bean
    public ServletRegistrationBean StatViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");//配置访问路径

        //后台需要有人登陆,账号密码配置
        HashMap<String ,String > initParameter=new HashMap<>();

        //添加配置
        //登陆key是固定的 loginUsername loginPassword
        initParameter.put("loginUsername","admin");
        initParameter.put("loginPassword","123456");

        //允许谁可以访问
        initParameter.put("allow","127.0.0.1");//allow表示允许访问，后面参数为空表示所有人可以访问,localhost 本地访问

        //禁止访问
        //initParameter.put("deny","127.0.0.1");//禁止ip访问
        bean.setInitParameters(initParameter);//配置初始化参数
        return bean;
    }

    //filter
    public FilterRegistrationBean WebStatFilter(){
        FilterRegistrationBean bean=new FilterRegistrationBean<>();

        bean.setFilter(new WebStatFilter());
        //过滤请求
        Map<String ,String > initParameter =new HashMap<>();

        //不进行统计
        initParameter.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParameter);
        return bean;
    }
}
