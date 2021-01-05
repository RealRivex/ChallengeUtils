package de.rivex.challengeutils.listeners;

import de.rivex.challengeutils.gamerules.HP;
import de.rivex.challengeutils.main.Main;
import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(Main.getPlugin().getConfig().getString("common.joinMessage")
                .replace("<player>", player.getName()));

        if (player.isOp()) {
            Main.checker.requestUpdateCheck().whenComplete((result, exception) -> {
                UpdateChecker.UpdateReason reason = result.getReason();
                if (result.requiresUpdate())
                    player.sendMessage("§cEs gibt ein Update von §9ChallengeUtils§c!");
                if (reason == UpdateChecker.UpdateReason.UNRELEASED_VERSION) {
                    player.sendMessage("§6Du nutzt eine noch nicht veröffentlichte Version von §9ChallengeUtils§6!");
                }
            });
        }
        HP.updateHP(SettingsGUI.hpGamerule);
    }
}