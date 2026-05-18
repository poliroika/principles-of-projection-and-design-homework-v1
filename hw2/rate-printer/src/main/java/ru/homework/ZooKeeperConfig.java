package ru.homework;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ZooKeeperConfig {
    @Bean(destroyMethod = "close")
    public CuratorFramework curatorFramework(@Value("${zookeeper.connect-string}") String connectString) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs((int) Duration.ofSeconds(10).toMillis())
                .connectionTimeoutMs((int) Duration.ofSeconds(3).toMillis())
                .retryPolicy(retryPolicy)
                .build();

        client.start();
        return client;
    }
}
