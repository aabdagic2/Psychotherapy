package com.nwt.tim2.AppointmentManagement.Configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.google")
@Data
public class GoogleConfiguration {
    private  String projectId;
    private  String clientSecret;
    private  String tokenUri;
   // @Value("${GOOGLE_CLIENT_ID}")
    private String clientId;

   // @Value("${GOOGLE_REDIRECT_URI}")
    private String redirectUri;
    private final Environment env;

    @Autowired
    public GoogleConfiguration(Environment env) {
        this.env = env;
    }

    public String getClientId() {
        return env.getProperty("GOOGLE_CLIENT_ID");
    }

    public String getRedirectUri() {
        return env.getProperty("GOOGLE_REDIRECT_URIS");
    }
    public String getClientSecret() {
        return env.getProperty("GOOGLE_CLIENT_SECRET");
    }
}
