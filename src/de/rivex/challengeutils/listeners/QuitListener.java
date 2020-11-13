package de.rivex.challengeutils.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        event.setQuitMessage("§c§l« §8" + event.getPlayer().getName());

    }
}
