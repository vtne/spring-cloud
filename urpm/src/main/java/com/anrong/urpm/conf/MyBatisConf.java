package com.anrong.urpm.conf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Created by liudh on 2017/3/10.
 */

@Configuration
// 扫描xxxMapper
@MapperScan("com.anrong.urpm.dao")
public class MyBatisConf {

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
       /* // 加载全局的配置文件
        bean.setConfigLocation(
                new DefaultResourceLoader().getResource("classpath:mybatisConfig/mybatis-config.xml"));*/
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mybaitsXml/*Mapper.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }


}
