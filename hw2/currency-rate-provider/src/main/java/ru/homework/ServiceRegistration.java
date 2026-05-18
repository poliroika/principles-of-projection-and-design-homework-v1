package ru.homework;

import jakarta.annotation.PostConstruct;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class ServiceRegistration {
    private final CuratorFramework curatorFramework;
    private final String registryPath;
    private final int serverPort;

    public ServiceRegistration(
            CuratorFramework curatorFramework,
            @Value("${service.registry.path}") String registryPath,
            @Value("${server.port}") int serverPort
    ) {
        this.curatorFramework = curatorFramework;
        this.registryPath = registryPath;
        this.serverPort = serverPort;
    }

    @PostConstruct
    public void register() throws Exception {
        String instanceUrl = "http://localhost:" + serverPort;
        String instancePath = registryPath + "/instance-" + UUID.randomUUID();

        curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(instancePath, instanceUrl.getBytes(StandardCharsets.UTF_8));

        System.out.println("Registered provider in ZooKeeper: " + instanceUrl);
    }
}
