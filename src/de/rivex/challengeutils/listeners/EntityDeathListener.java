package de.rivex.challengeutils.listeners;

import de.rivex.challengeutils.gamerules.CutClean;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        CutClean.start(event.getEntity(), event);
    }
}
