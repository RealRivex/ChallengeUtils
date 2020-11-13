package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomDrops implements Listener {
    private static HashMap<Material, Material> randomMaterials;
    private static Material[] materials = Material.values();

    public static void randomize() {
        if (SettingsGUI.randomDropsChallenge) {
            randomMaterials = Stream.of(materials)
                    .filter(mat -> mat != Material.AIR)
                    .collect(Collectors.toMap(Function.identity(),
                            mat -> materials[ThreadLocalRandom.current().nextInt(materials.length)],
                            (v1, v2) -> v1,
                            HashMap::new));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (SettingsGUI.randomDropsChallenge && Timer.timerRunning) {
            Material normalMaterial = event.getBlock().getType();
            Material randomMaterial = randomMaterials.get(normalMaterial);
            World world = event.getBlock().getWorld();
            world.dropItemNaturally(event.getBlock().getLocation(), new ItemStack(randomMaterial));
            System.out.println(randomMaterial);
        }
    }
}
