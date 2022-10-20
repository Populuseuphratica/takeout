package com.killstan.takeout.cos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description:
 * @date 2022/8/2 11:56
 */
@Data
@Component
@ConfigurationProperties(prefix = "cos")
public class CosInfo {
    private String path;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String region;
    private String rootDirectory;
}
