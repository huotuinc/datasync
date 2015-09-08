package com.huobanplus.goodsync.datacenter.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by liual on 2015-09-01.
 */
@Configuration
@ComponentScan(basePackages = "com.huobanplus.goodsync.datacenter")
@ImportResource({"classpath:datasource_test.xml"})
@EnableJpaRepositories(basePackages = "com.huobanplus.goodsync.datacenter.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy
public class DataCenterConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTemplate getTemplate() {
        return new JdbcTemplate(dataSource);
    }
}
