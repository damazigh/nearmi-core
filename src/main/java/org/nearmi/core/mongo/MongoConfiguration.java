package org.nearmi.core.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
/**
 * mongo configuration class that creates that handle database connection
 * @author A.Djebarri
 * @since 1.0
 */
@Configuration
@EnableReactiveMongoRepositories
public class MongoConfiguration extends AbstractReactiveMongoConfiguration {
    @Autowired
    private MongoResource mongoResource;

    @Override
    protected String getDatabaseName() {
        return this.mongoResource.getDbName();
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        StringBuilder sb = new StringBuilder("mongodb+srv://")
                .append(this.mongoResource.getDbUsername()).append(":")
                .append(this.mongoResource.getDbPassword()).append("@")
                .append(this.mongoResource.getCluster()).append("/")
                .append(this.mongoResource.getDbName());
        ConnectionString cs = new ConnectionString(sb.toString());
        builder.applyConnectionString(cs);
        builder.retryWrites(true);

    }
}
