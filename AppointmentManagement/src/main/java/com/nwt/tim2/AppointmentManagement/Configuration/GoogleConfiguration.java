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
        return "409514322651-su510k0g2c2grtqu2t4i4lgjpf1mh5lh.apps.googleusercontent.com";
    }

    public String getRedirectUri() {
        return "http://localhost:8081/route/token";
    }
    public String getClientSecret() {
        return "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";
    }
}
