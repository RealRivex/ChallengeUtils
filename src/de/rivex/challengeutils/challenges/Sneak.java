package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Sneak implements Listener {

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (SettingsGUI.sneakChallenge && Timer.timerRunning) {
            Player player = event.getPlayer();
            if (!event.isSneaking()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage("§cDer Spieler §b" + player.getName() + " §chat gesneakt.");
                    players.setGameMode(GameMode.SPECTATOR);
                }
                Timer.reset();
            }
        }
    }
}
