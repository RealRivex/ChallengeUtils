package de.rivex.challengeutils.gamerules;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageIndicator implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {

            Player player = (Player) event.getEntity();
            double damageHearts = event.getFinalDamage() / 2;
            Bukkit.broadcastMessage("§b" + player.getName() + " §6hat §9" + damageHearts + " Herzen §6Schaden genommen §7[§9" + event.getCause() + "§7]");

        }
    }
}
