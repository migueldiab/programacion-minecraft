package com.example.reward;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RecompensaCommand implements CommandExecutor {
    private final RewardService rewardService;

    public RecompensaCommand(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        rewardService.grantReward(player);
        player.sendMessage("§aYou received a reward!");
        return true;
    }
}
