package de.rivex.challengeutils.listeners;

import de.rivex.challengeutils.main.Main;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class ForceLoadChunks implements Listener {

    @EventHandler
    public void onLoad(WorldLoadEvent event) {
        World world = event.getWorld();

        if (world.getName().equals(Main.getPlugin().getConfig().getString("worldName"))) {
            world.getChunkAt(8, 8).load();
            world.setChunkForceLoaded(8, 8, true);
            world.getChunkAt(-8, -8).load();
            world.setChunkForceLoaded(-8, -8, true);
            world.getChunkAt(8, -8).load();
            world.setChunkForceLoaded(8, -8, true);
            world.getChunkAt(-8, 8).load();
            world.setChunkForceLoaded(-8, 8, true);
        }
    }
}
