package de.rivex.challengeutils.utils;

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

    //Settings states
    public static boolean breakChallenge;
    public static boolean placeChallenge;
    public static boolean craftingChallenge;
    public static boolean sneakChallenge;
    public static boolean forceYChallenge;
    public static boolean forceBlockChallenge;
    public static boolean forceItemChallenge;
    public static boolean randomDropsChallenge;
    public static boolean randomCraftingChallenge;

    //GUIs
    private final Inventory settings_challenges = Bukkit.createInventory(null, 54, "§b§lChallenge-Settings");

    private void setItem(int slot, Material material, Inventory inventory, String displayName) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }

    private void setItem(int slot, Material material, Inventory inventory, String displayName, String... lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }

    private void updateGUI(Player player, Inventory gui) {
        setItem(1, Material.NETHERITE_PICKAXE, settings_challenges, "§6BlockBreak-Challenge", " ", "" + returnSettingsState(breakChallenge));
        setItem(2, Material.COBBLESTONE, settings_challenges, "§6BlockPlace-Challenge", " ", "" + returnSettingsState(placeChallenge));
        setItem(3, Material.CRAFTING_TABLE, settings_challenges, "§6NoCraftingTable-Challenge", " ", "" + returnSettingsState(craftingChallenge));
        setItem(4, Material.GOLDEN_BOOTS, settings_challenges, "§6Sneak-Challenge", " ", "" + returnSettingsState(sneakChallenge));
        setItem(5, Material.BONE_BLOCK, settings_challenges, "§6ForceY-Challenge", " ", "" + returnSettingsState(forceYChallenge));
        setItem(6, Material.BROWN_WOOL, settings_challenges, "§6ForceBlock-Challenge", " ", "" + returnSettingsState(forceBlockChallenge));
        setItem(7, Material.FLOWER_POT, settings_challenges, "§6ForceItem-Challenge", " ", "" + returnSettingsState(forceItemChallenge));
        setItem(10, Material.NETHER_STAR, settings_challenges, "§6RandomDrops-Challenge", " ", "" + returnSettingsState(randomDropsChallenge));
        setItem(11, Material.STONE_SWORD, settings_challenges, "§6RandomCrafting-Challenge", " ", "" + returnSettingsState(randomCraftingChallenge));

        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> player.openInventory(gui), 1L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                updateGUI(player, settings_challenges);

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
                    switchSettingsState(event.getSlot());

                default:
            }
        }
    }

    private String returnSettingsState(boolean bool) {
        if (bool) {
            return "§atrue";
        } else {
            return "§cfalse";
        }
    }

    private void switchSettingsState(int slot) {
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
                randomDropsChallenge = !randomDropsChallenge;
                break;
            case 11:
                randomCraftingChallenge = !randomCraftingChallenge;
                break;
            default:
                break;
        }
        for (Player players : Bukkit.getOnlinePlayers()) {
            updateGUI(players, settings_challenges);
        }
    }
}