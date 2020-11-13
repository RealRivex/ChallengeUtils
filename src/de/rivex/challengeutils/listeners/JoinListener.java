package de.rivex.challengeutils.listeners;

import de.rivex.challengeutils.main.Main;
import de.rivex.challengeutils.utils.UpdateChecker;
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

            UpdateChecker.init(Main.getPlugin(), 80158).requestUpdateCheck().whenComplete((result, exception) -> {
                if (result.requiresUpdate()) {
                    if (player.isOp()) {
                        player.sendMessage("§aDie neueste Version von §9ChallengeUtils §aist installiert!");
                    }
                } else {
                    if (player.isOp()) {
                        player.sendMessage("§cEs gibt eine neuere Version von §9ChallengeUtils§c! Aktuelle Version: §9" + Main.getPlugin().getDescription().getVersion() + " §7| §cNeueste Version: §9" + result.getNewestVersion());
                    }
                }
            });
        }
    }

}
