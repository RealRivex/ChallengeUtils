package de.rivex.challengeutils.listeners;

import de.rivex.challengeutils.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("§a§l» §8" + event.getPlayer().getName());
        if (!player.hasPlayedBefore()) {
            player.sendMessage("§aUm die Challenges zu konfigurieren, nutze §6/settings");
            player.sendMessage(" ");
            player.sendMessage("§aUm den Timer zu nutzen, nutze §6/timer §7<§6set (sekunden)§7|§6resume§7|§6pause§7|§6reset§7>");
        }
        if (player.isOp()) {
            if (Main.devMode) {
                player.sendMessage("§aAktuell nutzt du eine Dev-Version von §9ChallengeUtils§a. Das bedeutet, es kann noch zu Bugs kommen. Solltest du Bugs finden melde diese im Support-Discord :)");
            }
        }
    }

}
