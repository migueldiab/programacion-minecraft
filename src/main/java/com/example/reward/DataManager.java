package com.example.reward;

import com.example.reward.model.PlayerData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {
    private final File dataFolder;
    private final Map<UUID, PlayerData> cache = new ConcurrentHashMap<>();

    public DataManager(File dataFolder) {
        this.dataFolder = dataFolder;
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        loadAll();
    }

    private void loadAll() {
        File[] files = dataFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;
        for (File file : files) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            String uuidStr = config.getString("uuid");
            if (uuidStr != null) {
                UUID uuid = UUID.fromString(uuidStr);
                long lastTime = config.getLong("last_grant_time");
                int rewards = config.getInt("total_rewards");
                cache.put(uuid, new PlayerData(uuid, lastTime, rewards));
            }
        }
    }

    public PlayerData getData(UUID uuid) {
        return cache.computeIfAbsent(uuid, id -> {
            PlayerData defaultData = new PlayerData(id, 0L, 0);
            save(defaultData);
            return defaultData;
        });
    }

    public void updateAndSave(PlayerData data) {
        cache.put(data.id(), data);
        save(data);
    }

    private void save(PlayerData data) {
        File file = new File(dataFolder, "player_" + data.id() + ".yml");
        FileConfiguration config = new YamlConfiguration();
        config.set("uuid", data.id().toString());
        config.set("last_grant_time", data.lastGrantTime());
        config.set("total_rewards", data.totalRewards());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
