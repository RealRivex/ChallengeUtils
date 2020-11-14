package de.rivex.challengeutils.gamerules;

import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CutClean {

    public static void start(Block block) {
        if (SettingsGUI.cutcleanGamerule && Timer.timerRunning) {
            switch (block.getType()) {
                case IRON_ORE:
                    block.getDrops().clear();
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_INGOT));
                    break;
                case GOLD_ORE:
                    block.getDrops().clear();
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_INGOT));
                    break;
            }
        }
    }

    public static void start(LivingEntity entity, EntityDeathEvent event) {
        if (SettingsGUI.cutcleanGamerule) {
            switch (entity.getType()) {
                case PIG:
                    event.getDrops().clear();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.COOKED_PORKCHOP, 2));
                    break;
                case COW:
                    event.getDrops().clear();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.COOKED_BEEF, 2));
                    break;
                case SHEEP:
                    Sheep sheep = (Sheep) entity;
                    DyeColor color = sheep.getColor();
                    event.getDrops().clear();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.COOKED_BEEF, 2));
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.valueOf(color.toString().toUpperCase() + "_WOOL"), 1));
                    break;
                case RABBIT:
                    event.getDrops().clear();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.COOKED_RABBIT, 2));
                    break;
                case CHICKEN:
                    event.getDrops().clear();
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.COOKED_CHICKEN, 2));
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.FEATHER, 1));
                    break;
            }
        }
    }
}
