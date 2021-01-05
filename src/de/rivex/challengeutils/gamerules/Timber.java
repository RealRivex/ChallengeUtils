package de.rivex.challengeutils.gamerules;

import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.List;

public class Timber implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (SettingsGUI.timberGamerule && Timer.timerRunning) {
            Block b = event.getBlock();
            World w = b.getWorld();

            if (blockIsLog(b)) {
                for (int i = b.getY(); blockIsLog(w.getBlockAt(b.getX(), i, b.getZ())); i++) {
                    w.getBlockAt(b.getX(), i, b.getZ()).breakNaturally();
                }
            }
        }
    }

    private boolean blockIsLog(Block b) {
        List<Material> l = Arrays.asList(Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG, Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG, Material.CRIMSON_STEM, Material.WARPED_STEM);
        return l.contains(b.getType());
    }
}
