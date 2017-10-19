package io.rscale.training.company;


import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.cloud.config.java.AbstractCloudConfig;


import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("cloud")
@Configuration
public class DataSourceConfig extends AbstractCloudConfig {

    private static final Logger logger = Logger.getLogger(DataSourceConfig.class);

    public DataSourceConfig() {
        logger.info(this.getClass() + " loaded");
    }

    @Bean
    public DataSource dataSource() throws Exception {
        DataSource dataSource = cloud().getSingletonServiceConnector(DataSource.class, null);
        if ( !isMySQL(dataSource)) {
            logger.error("MySQL required when running cloud profile.");
            throw new UnsatisfiedDependencyException("javax.sql.DataSource", "javax.sql.DataSource", "javax.sql.DataSource", "MySQL required when running cloud profile.");
        }
        return dataSource;
    }

    private boolean isMySQL(DataSource dataSource) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            return "MySQL".equalsIgnoreCase(connection.getMetaData().getDatabaseProductName());
        }
    }

}
