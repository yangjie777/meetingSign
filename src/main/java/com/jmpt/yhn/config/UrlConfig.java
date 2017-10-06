package com.jmpt.yhn.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by yhn on 2017/9/9.
 */
@Data
@Component
@ConfigurationProperties(prefix = "meetingSignUrl")
public class UrlConfig {
    private String meeting;
}
