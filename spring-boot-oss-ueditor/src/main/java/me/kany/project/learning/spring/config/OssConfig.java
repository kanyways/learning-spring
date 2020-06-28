/**
 * Project Name:learning-spring
 * File Name:OssConfig.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2020年06月28日 11:51
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName:OssConfig<br/>
 * Function: OSS的配置<br/>
 * Date:2020年06月28日 11:51<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Data
@Component
@ConfigurationProperties(prefix = OssConfig.OSS_ALIYUN_PREFIX)
public class OssConfig {
    public static final String OSS_ALIYUN_PREFIX = "oss.aliyun";

    private String accessKeyId;
    private String accessKeySecret;
    private String sysEndpoint;
    private String roleArn;
    private String roleSessionName;
    private String policy;
    private String bucketName;
    private String endpoint;
    private Integer expire;
    private Integer maxSize;
    private String callback;
    private String prefix;
}
