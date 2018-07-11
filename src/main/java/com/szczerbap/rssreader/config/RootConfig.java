package com.szczerbap.rssreader.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.szczerbap.rssreader.repository")
public class RootConfig {

    @Bean
    public DataSource createDS() {
        BasicDataSource ds = new BasicDataSource();
          ds.setUrl("jdbc:mysql://rssreadersql.mysql.database.azure.com:3306/rssreader?useSSL=true&requireSSL=false");
          ds.setUsername("przemeksz28@rssreadersql");
          ds.setPassword("DocWho28");
          ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

        //myDbConn = DriverManager.getConnection(url, "{your_username}", {your_password});

        /*
        ds.setUrl("jdbc:mysql://localhost:3306/mojabaza");
        ds.setUsername("admin");
        ds.setPassword("admin");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        */

        //BasicDataSource ds = DriverManager.getConnection("jdbc:mysql://rssreadersql.mysql.database.azure.com:3306/rssreader?useSSL=true&requireSSL=false", "przemeksz28@rssreadersql", "DocWho28");

        return ds;
    }

    @Bean
    public JpaVendorAdapter createVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter adapter, DataSource ds) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        emf.setJpaPropertyMap(properties);
        emf.setDataSource(ds);
        emf.setJpaVendorAdapter(adapter);
        emf.setPackagesToScan("com.szczerbap.rssreader.model");
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager(emf);
        return txManager;
    }
}

