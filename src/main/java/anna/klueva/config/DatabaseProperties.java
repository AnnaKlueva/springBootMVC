package anna.klueva.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="database")
public class DatabaseProperties {
    private String dbDriver;
    private String username;
    private String password;
    private String url;
    private String additionalPropertiesFilename;

    public String getDBDriver() {
        return dbDriver;
    }

    public void setDBDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdditionalPropertiesFilename() {
        return additionalPropertiesFilename;
    }

    public void setAdditionalPropertiesFilename(String additionalPropertiesFilename) {
        this.additionalPropertiesFilename = additionalPropertiesFilename;
    }
}
