package com.moonkeyeu.etl.api.configuration.s3;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets")
@Data
public class S3Buckets {
    @Value("db-images")
    private String dbImages;
}
