package com.example.reward;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RewardService {
    private final Map<UUID, Long> lastGrantTimes = new ConcurrentHashMap<>();
    private static final long COOLDOWN_MS = 60000; // 60 seconds

    public boolean grantReward(Player player) {
        return grantReward(player, "default");
    }

    public boolean grantReward(Player player, String mobType) {
        long currentTime = System.currentTimeMillis();
        Long lastTime = lastGrantTimes.get(player.getUniqueId());

        if (lastTime != null && (currentTime - lastTime) < COOLDOWN_MS) {
            long remaining = (COOLDOWN_MS - (currentTime - lastTime)) / 1000;
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
            
            lastGrantTimes.put(player.getUniqueId(), System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            player.getInventory().addItem(new ItemStack(Material.DIAMOND));
            return false;
        }
    }
}
