package com.example.reward;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecompensaTest {

    @Test
    public void testRecompensaCommandCallsRewardService() {
        // Arrange
        Player player = mock(Player.class);
        RewardService rewardService = mock(RewardService.class);
        RecompensaCommand commandHandler = new RecompensaCommand(rewardService);

        // Act
        commandHandler.execute(player, "recompensa");

        // Assert
        verify(rewardService, times(1)).grantReward(player);
    }
}
