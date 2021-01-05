package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OneLine implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (Timer.timerRunning) {
            Location getFrom = event.getFrom();
            Location getTo = event.getTo();
            if (SettingsGUI.oneLineChallenge == 0)
                return;
            if (SettingsGUI.oneLineChallenge == 1) {
                if (getFrom.getBlockX() != getTo.getBlockX())
                    event.setCancelled(true);
            } else if (SettingsGUI.oneLineChallenge == 2) {
                if (getFrom.getBlockY() != getTo.getBlockY())
                    event.setCancelled(true);
            }
        }
    }
}
