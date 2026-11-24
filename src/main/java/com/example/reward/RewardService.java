package com.example.reward;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RewardService {
    public void grantReward(Player player) {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND));
    }
}
