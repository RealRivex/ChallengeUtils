package de.rivex.challengeutils.utils;

import de.rivex.challengeutils.gamerules.HP;
import de.rivex.challengeutils.main.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SettingsGUI implements Listener, CommandExecutor {

    //GUIs
    public static final Inventory settings_challenges = Bukkit.createInventory(null, 54, "§b§lChallenge-Settings");
    public static final Inventory settings_gamerules = Bukkit.createInventory(null, 54, "§b§lGamerule-Settings");

    //Settings states
    public static boolean breakChallenge;
    public static boolean placeChallenge;
    public static boolean craftingChallenge;
    public static boolean sneakChallenge;
    public static boolean forceYChallenge;
    public static boolean forceBlockChallenge;
    public static boolean forceItemChallenge;
    public static boolean forceMLGChallenge;
    public static boolean forceBiomeChallenge;
    public static int oneLineChallenge;
    public static int damageClearChallenge;

    //Gamerules states
    public static boolean cutcleanGamerule;
    public static boolean damageIndicatorGamerule;
    public static boolean timberGamerule;
    public static int hpGamerule = 20;

    public static void setItem(Integer slot, Material material, Inventory inventory, String displayName, String... lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }


    public static void updateGUI(Player player, Inventory gui) {

        setItem(1, Material.NETHERITE_PICKAXE, settings_challenges, "§6BlockBreak-Challenge", " ", returnSettingsState(breakChallenge));
        setItem(2, Material.COBBLESTONE, settings_challenges, "§6BlockPlace-Challenge", " ", returnSettingsState(placeChallenge));
        setItem(3, Material.CRAFTING_TABLE, settings_challenges, "§6NoCraftingTable-Challenge", " ", returnSettingsState(craftingChallenge));
        setItem(4, Material.GOLDEN_BOOTS, settings_challenges, "§6Sneak-Challenge", " ", returnSettingsState(sneakChallenge));
        setItem(5, Material.BONE_BLOCK, settings_challenges, "§6ForceY-Challenge", " ", returnSettingsState(forceYChallenge));
        setItem(6, Material.BROWN_WOOL, settings_challenges, "§6ForceBlock-Challenge", " ", returnSettingsState(forceBlockChallenge));
        setItem(7, Material.FLOWER_POT, settings_challenges, "§6ForceItem-Challenge", " ", returnSettingsState(forceItemChallenge));
        setItem(10, Material.WATER_BUCKET, settings_challenges, "§6ForceMLG-Challenge", " ", returnSettingsState(forceMLGChallenge));
        setItem(11, Material.GRASS_BLOCK, settings_challenges, "§6OneLine-Challenge", " ", returnSettingsState(oneLineChallenge, "oneLineChallenge"));
        setItem(12, Material.SAND, settings_challenges, "§6ForceBiome-Challenge", " ", returnSettingsState(forceBiomeChallenge));
        setItem(13, Material.GLASS_PANE, settings_challenges, "§6DamageClear-Challenge", " ", returnSettingsState(damageClearChallenge, "damageClearChallenge"));

        setItem(1, Material.IRON_INGOT, settings_gamerules, "§6CutClean", " ", returnSettingsState(cutcleanGamerule));
        setItem(2, Material.BARRIER, settings_gamerules, "§6Damage-Indicator", " ", returnSettingsState(damageIndicatorGamerule));
        setItem(3, Material.RED_DYE, settings_gamerules, "§6HP", " ", returnSettingsState(hpGamerule, "hpGamerule"));
        setItem(4, Material.ACACIA_LOG, settings_gamerules, "§6Timber", " ", returnSettingsState(timberGamerule));

        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> player.openInventory(gui), 1L);

    }

    public static String returnSettingsState(Boolean b) {
        if (b) {
            return "§atrue";
        } else {
            return "§cfalse";
        }
    }

    public static String returnSettingsState(int i, String s) {
        if (s.equalsIgnoreCase("oneLineChallenge")) {
            if (i == 0) {
                return "§cfalse";
            } else if (i == 1) {
                return "§aX-Coordinate";
            } else if (i == 2) {
                return "§aY-Coordinate";
            } else return "§cError, Checke deine Config.yml...";

        } else if (s.equalsIgnoreCase("hpGamerule")) {
            return "§a" + i + " HP";
        } else if (s.equalsIgnoreCase("damageClearChallenge")) {
            if (i == 0) {
                return "§cfalse";
            } else if (i == 1) {
                return "§aInventar des Auslösers wird gecleared.";
            } else if (i == 2) {
                return "§aInventar von allen Spielern wird gecleared";
            } else return "§cError, Checke deine Config.yml...";
        } else {
            return "§cError...";
        }
    }

    public static void switchSettingsState(Player player, Integer slot) {
        switch (slot) {
            case 1:
                breakChallenge = !breakChallenge;
                break;
            case 2:
                placeChallenge = !placeChallenge;
                break;
            case 3:
                craftingChallenge = !craftingChallenge;
                break;
            case 4:
                sneakChallenge = !sneakChallenge;
                break;
            case 5:
                forceYChallenge = !forceYChallenge;
                break;
            case 6:
                forceBlockChallenge = !forceBlockChallenge;
                break;
            case 7:
                forceItemChallenge = !forceItemChallenge;
                break;
            case 10:
                forceMLGChallenge = !forceMLGChallenge;
                break;
            case 11:
                if (oneLineChallenge == 2) {
                    oneLineChallenge = 0;
                    break;
                } else {
                    oneLineChallenge++;
                    break;
                }
            case 12:
                forceBiomeChallenge = !forceBiomeChallenge;
                break;
            case 13:
                if (damageClearChallenge == 2) {
                    damageClearChallenge = 0;
                    break;
                } else {
                    damageClearChallenge++;
                    break;
                }
            default:
                break;
        }
        updateGUI(player, settings_challenges);
    }

    public static void switchGamerulesStates(Player p, Integer i) {
        switch (i) {
            case 1:
                cutcleanGamerule = !cutcleanGamerule;
                break;
            case 2:
                damageIndicatorGamerule = !damageIndicatorGamerule;
                break;
            case 3:
                if (hpGamerule == 20) {
                    hpGamerule = 1;
                } else {
                    hpGamerule++;
                }
                HP.updateHP(hpGamerule);
                break;
            case 4:
                timberGamerule = !timberGamerule;
            default:
                break;
        }
        updateGUI(p, settings_gamerules);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (cmd.getName().equalsIgnoreCase("settings")) {
                    updateGUI(player, settings_challenges);
                } else if (cmd.getName().equalsIgnoreCase("gamerules")) {
                    updateGUI(player, settings_gamerules);
                }
            } else player.sendMessage("§cSyntax: §7/settings");
        } else sender.sendMessage(ChatColor.RED + "Dazu musst du ein Spieler sein...");
        return false;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            switch (event.getView().getTitle()) {
                case "§b§lChallenge-Settings":
                    event.setCancelled(true);
                    switchSettingsState(player, event.getSlot());
                    break;
                case "§b§lGamerule-Settings":
                    event.setCancelled(true);
                    switchGamerulesStates(player, event.getSlot());
                    break;
                default:
                    break;
            }
        }
    }
}