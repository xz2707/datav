package com.datav;

import com.alibaba.druid.pool.DruidDataSource;
import com.datav.common.pojo.HttpAddress;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 包含main函数，像普通java程序启动即可。
 * 包含和数据库相关的DataSource，SqlSeesion配置内容
 * 注：@MapperScan(“com.datav.mybatis.mapper”) 表示Mybatis的映射路径（package路径）
 */
@SpringBootApplication
@EnableAutoConfiguration
@ServletComponentScan
@ComponentScan(basePackages={"com.datav"})
@MapperScan("com.datav.mybatis.mapper")
@EnableConfigurationProperties({HttpAddress.class})
public class DatavApplication {
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(DatavApplication.class);


    /**
     * DataSource配置
     * 根据前缀“spring.datasource”从application.properties中匹配相关属性值
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DruidDataSource dataSource() {
        return new com.alibaba.druid.pool.DruidDataSource();
    }

    /**
     * 提供SqlSeesion
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * main start
     * @param args
     */
	public static void main(String[] args) {
        SpringApplication.run(DatavApplication.class, args);
        log.info("============= SpringBoot Start Success =============");
	}
}
