package org.nearmi.core.mongo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * wrapper class where properties defined under application-{profile} file are loaded
 * at runtime by spring
 * @author A.Djebarri
 * @since 1.0
 */
@ConfigurationProperties(prefix = "nearmi.config")
@Data
@Configuration
public class MongoResource {
    // database name
    private String dbName;
    // username used to connect database
    @Getter(AccessLevel.NONE)
    private String dbUsername;
    // password used to connect to database
    @Getter(AccessLevel.NONE)
    private String dbPassword;
    // cluster url
    private String cluster;

    public String getDbUsername() {
        return URLEncoder.encode(this.dbUsername, Charset.defaultCharset());
    }

    public String getDbPassword() {
        return URLEncoder.encode(this.dbPassword, Charset.defaultCharset());
    }
}
