package de.rivex.challengeutils.listeners;

import de.rivex.challengeutils.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(Main.getPlugin().getConfig().getString("common.quitMessage")
                .replace("<player>", player.getName()));
    }
}
