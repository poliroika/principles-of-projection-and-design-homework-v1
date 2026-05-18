package ru.homework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProviderRegistry {
    private final CuratorFramework curatorFramework;
    private final String registryPath;

    public ProviderRegistry(
            CuratorFramework curatorFramework,
            @Value("${service.registry.path}") String registryPath
    ) {
        this.curatorFramework = curatorFramework;
        this.registryPath = registryPath;
    }

    public List<String> getProviderUrls() throws Exception {
        try {
            List<String> children = curatorFramework.getChildren().forPath(registryPath);
            List<String> urls = new ArrayList<>();

            for (String child : children) {
                byte[] data = curatorFramework.getData().forPath(registryPath + "/" + child);
                urls.add(new String(data, StandardCharsets.UTF_8));
            }

            Collections.sort(urls);
            return urls;
        } catch (KeeperException.NoNodeException e) {
            return List.of();
        }
    }
}
