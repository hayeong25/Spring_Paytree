package com.api.paytree.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(value="com.api.paytree.mapper")
@EnableTransactionManagement
public class DatabaseConfig {
    @Primary
    @Bean(name="paytreeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.paytree")
    public DataSource pvboDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "paytreeSqlSessionFactory")
    public SqlSessionFactory pvboSqlSessionFactory(@Qualifier("paytreeDataSource")DataSource pvboDataSource) throws Exception {
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml");

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(pvboDataSource);
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "paytreeSqlSessionTemplate")
    public SqlSessionTemplate pvboSqlSessionTemplate(@Qualifier("paytreeSqlSessionFactory")SqlSessionFactory pvboSqlSessionFactory) {
        return new SqlSessionTemplate(pvboSqlSessionFactory);
    }

    @Primary
    @Bean(name = "paytreeDataSourceTransactionManager")
    public DataSourceTransactionManager pvboDataSourceTransactionManager(@Qualifier("paytreeDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}