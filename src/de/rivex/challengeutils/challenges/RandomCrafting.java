package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomCrafting implements Listener {
    private static HashMap<Material, Material> randomMaterials;
    private static Material[] materials = Material.values();

    public static void randomize() {
        if (SettingsGUI.randomCraftingChallenge) {
            randomMaterials = Stream.of(materials)
                    .filter(mat -> mat != Material.AIR)
                    .collect(Collectors.toMap(Function.identity(),
                            mat -> materials[ThreadLocalRandom.current().nextInt(materials.length)],
                            (v1, v2) -> v1,
                            HashMap::new));
        }
    }

    @EventHandler
    public void onCrafting(PrepareItemCraftEvent event) {
        if (SettingsGUI.randomCraftingChallenge && Timer.timerRunning) {
            Material mat = event.getRecipe().getResult().getType();
            int amount = event.getRecipe().getResult().getAmount();
            Material randomCrafting = randomMaterials.get(mat);
            if (randomCrafting.isItem()) {
                event.getInventory().setResult(new ItemStack(randomCrafting, amount));
            }
        }
    }
}
