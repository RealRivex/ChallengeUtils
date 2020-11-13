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
            System.out.println("1");
            Player player = event.getPlayer();
            System.out.println("2");
            if (!event.isSneaking()) {
                System.out.println("3");
                for (Player players : Bukkit.getOnlinePlayers()) {
                    System.out.println("4");
                    players.sendMessage("§cDer Spieler §b" + player.getName() + " §chat gesneakt.");
                    players.setGameMode(GameMode.SPECTATOR);
                    System.out.println("5");
                }
                Timer.reset();
                System.out.println("6");
            }
        }
    }
}
