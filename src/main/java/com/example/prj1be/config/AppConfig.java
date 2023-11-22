package com.example.prj1be.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
@Configuration
public class AppConfig {
    @Value("${aws.accessKeyId}")
    private String id;
    @Value("${aws.secretAccessKey}")
    private String key;
    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(id,key);
        AwsCredentialsProvider provider = StaticCredentialsProvider.create(credentials);
        S3Client s3 = S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(provider)
                .build();
        return s3;
    }
}
