package com.example.reward;

import com.example.reward.model.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.io.*;
import java.util.*;

public class RewardService {
    private final DataManager dataManager;
    private static final long COOLDOWN_MS = 60000; // 60 seconds

    public RewardService() {
        File dataDir = new File("/home/madrax/src/ai/programacion-minecraft/data");
        this.dataManager = new DataManager(dataDir);
    }

    public boolean grantReward(Player player) {
        return grantReward(player, "default");
    }

    public boolean grantReward(Player player, String mobType) {
        UUID uuid = player.getUniqueId();
        PlayerData data = dataManager.getData(uuid);
        
        long currentTime = System.currentTimeMillis();
        
        if ((currentTime - data.lastGrantTime()) < COOLDOWN_MS) {
            long remaining = (COOLDOWN_MS - (currentTime - data.lastGrantTime())) / 1000;
            player.sendMessage("§cPlease wait " + remaining + " more seconds before claiming another reward.");
            return false;
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("ruby", "/home/madrax/src/ai/programacion-minecraft/lib/loot_manager.rb", mobType);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                String[] items = line.split(",");
                for (String itemId : items) {
                    if (!itemId.isEmpty()) {
                        player.getInventory().addItem(new ItemStack(Material.valueOf(itemId.trim())));
                    }
                }
            }
            process.waitFor();
            
            // Update statistics
            PlayerData updated = new PlayerData(uuid, currentTime, data.totalRewards() + 1);
            dataManager.updateAndSave(updated);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            player.getInventory().addItem(new ItemStack(Material.DIAMOND));
            return false;
        }
    }
}
