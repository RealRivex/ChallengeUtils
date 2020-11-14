package de.rivex.challengeutils.listeners;

import de.rivex.challengeutils.gamerules.CutClean;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        CutClean.start(event.getBlock());
    }
}
