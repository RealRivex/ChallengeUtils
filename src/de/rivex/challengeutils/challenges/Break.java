package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.main.Main;
import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Break implements Listener {

    @EventHandler
    public void onPlace(BlockBreakEvent event) {

        if (SettingsGUI.breakChallenge && Timer.timerRunning) {
            Player player = event.getPlayer();
            event.setCancelled(true);
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage(Main.getPlugin().getConfig().getString("noBlockBreak.punishMessage")
                        .replace("<player>", player.getName())
                        .replace("<block>", event.getBlock().getType().toString().toLowerCase().replace("_", " "))
                        .replace("<BLOCK>", event.getBlock().getType().toString().toUpperCase().replace("_", " ")));
                players.setGameMode(GameMode.SPECTATOR);
            }
            Timer.reset();
        }
    }
}
