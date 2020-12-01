package org.nearmi.core.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
/**
 * mongo configuration class that creates that handle database connection
 * @author A.Djebarri
 * @since 1.0
 */
@Configuration
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoClientConfiguration {
    @Autowired
    private MongoResource mongoResource;

    @Override
    protected String getDatabaseName() {
        return this.mongoResource.getDbName();
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        String sb = "mongodb+srv://" +
                this.mongoResource.getDbUsername() + ":" +
                this.mongoResource.getDbPassword() + "@" +
                this.mongoResource.getCluster() + "/" +
                this.mongoResource.getDbName();
        ConnectionString cs = new ConnectionString(sb);
        builder.applyConnectionString(cs);
        builder.retryWrites(true);

    }
}
