package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageClear implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(Timer.timerRunning) {
            if (!(event.getEntity() instanceof Player)) {
                return;
            }
            Player player = (Player) event.getEntity();
            if (event.getFinalDamage() > 0) {
                if (SettingsGUI.damageClearChallenge == 0) {
                    return;
                }
                if (SettingsGUI.damageClearChallenge == 1) {
                    player.getInventory().clear();
                } else if (SettingsGUI.damageClearChallenge == 2) {
                    for (Player players : Bukkit.getOnlinePlayers()) players.getInventory().clear();
                }
            }
        }
    }
}
