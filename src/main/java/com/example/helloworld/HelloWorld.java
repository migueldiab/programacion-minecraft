package com.example.helloworld;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class HelloWorld extends JavaPlugin implements Listener {

    private RewardService rewardService;

    @Override
    public void onEnable() {
        getLogger().info("HelloWorld plugin enabled!");
        rewardService = new RewardService();
        getCommand("recompensa").setExecutor(new RecompensaCommand(rewardService));
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("§aWelcome!");
    }
}
