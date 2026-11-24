package com.example.reward.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import com.example.reward.RewardService;

public class DeathListener implements Listener {
    private final RewardService rewardService;

    public DeathListener(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // Check if a player killed it
        if (event.getEntity().getKiller() instanceof Player) {
            Player killer = (Player) event.getEntity().getKiller();
            String mobType = event.getEntityType().name().toLowerCase();
            
            // Call the ruby-backed reward service
            rewardService.grantReward(killer, mobType);
        }
    }
}
