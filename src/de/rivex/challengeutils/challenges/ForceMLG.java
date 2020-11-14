package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.main.Main;
import de.rivex.challengeutils.utils.SettingsGUI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ForceMLG implements Listener {
    private static ArrayList<Player> passedList = new ArrayList<>();
    private static FileConfiguration config = Main.getPlugin().getConfig();
    private static ArrayList<Block> blockList = new ArrayList<>();
    private static HashMap<Block, Material> blockMap = new HashMap<>();
    private static HashMap<Player, Location> locMap = new HashMap<>();
    private static HashMap<Player, Location> teleportLocMap = new HashMap<>();
    private static HashMap<Player, ItemStack> itemMap = new HashMap<>();
    private static BukkitTask task;

    public static void start() {
        if (SettingsGUI.forceMLG) {
            int min = config.getInt("forceMLG.minTime");
            int max = config.getInt("forceMLG.maxTime");
            int minY = config.getInt("forceMLG.minY");
            int maxY = config.getInt("forceMLG.maxY");
            int rndTime = new Random().nextInt((max - min) + 1) + min;
            task = Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                Location location = new Location(Bukkit.getWorld("world"), 0, 200, 0);
                for (int x = (int) location.getX() - 15; x < (int) location.getX() + 15; x++) {
                    for (int z = (int) location.getZ() - 15; z < (int) location.getZ() + 15; z++) {
                        Block block = location.getWorld().getBlockAt(x, (int) location.getY(), z);
                        blockList.add(block);
                        blockMap.put(block, block.getType());
                        block.setType(Material.NETHER_BRICKS);
                    }
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    teleportLocMap.put(players, new Location(
                            Bukkit.getWorld("world"),
                            ThreadLocalRandom.current().nextInt(-14, 14),
                            200,
                            ThreadLocalRandom.current().nextInt(-14, 14)));
                    Location teleportLoc = teleportLocMap.get(players);
                    locMap.put(players, players.getLocation());
                    itemMap.put(players, players.getInventory().getItem(0));
                    passedList.add(players);
                    players.teleport(teleportLoc.add(0, ThreadLocalRandom.current().nextInt(minY, maxY), 0));
                    players.getInventory().setItem(0, new ItemStack(Material.WATER_BUCKET));
                    Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                        for (Block blocks : blockList) {
                            blocks.setType(blockMap.get(blocks));
                        }
                        players.teleport(locMap.get(players));
                        players.getInventory().setItem(0, itemMap.get(players));
                        passedList.remove(players);
                        players.sendMessage(config.getString("forceMLG.passedMessage"));
                        task.cancel();
                        start();
                    }, 150L);
                }
            }, rndTime);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (passedList.contains(player)) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage(config.getString("forceMLG.punishMessage"));
                players.setGameMode(GameMode.SPECTATOR);
                event.setDeathMessage(config.getString("common.deathMessage")
                        .replace("<player>", player.getName())
                        .replace("<cause>", player.getLastDamageCause().getCause().toString().replace("_", " ")));
            }
            task.cancel();
        }
    }
}