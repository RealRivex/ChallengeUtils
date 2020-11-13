package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.main.Main;
import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;

public class ForceBlock {
    private static BukkitTask collectRunnableTask;
    private static ArrayList<Material> materials = new ArrayList<>();
    private static BukkitTask chooseInstruction;
    private static int collectTime;
    private static BukkitTask collectTask;
    private static FileConfiguration config = Main.getPlugin().getConfig();

    public static void start() {
        if (SettingsGUI.forceBlockChallenge) {
            int min = config.getInt("forceBlock.minTime");
            int max = config.getInt("forceBlock.maxTime");
            int rndTime = new Random().nextInt((max - min) + 1) + min;
            chooseInstruction = Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                addMaterials();
                Material block = materials.get(new Random().nextInt(materials.size()));
                Timer.showTimeTask.cancel();
                int minCollect = config.getInt("forceBlock.minCollectTime");
                int maxCollect = config.getInt("forceBlock.maxCollectTime");
                int rndTime2 = new Random().nextInt((maxCollect - minCollect) + 1) + minCollect;
                collectTime = rndTime2 / 20;
                collectRunnableTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                    collectTime--;
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aAnweisung: §9" + block.toString().replace("_", " ") + " §ain §9" + convertedTime(collectTime)));
                    }
                }, 0L, 20L);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage("§7[§bForceBlock§7] §aAnweisung: Stehe auf dem Block §9" + block.toString().toUpperCase().replace("_", " ") + " §ain §9" + convertedTime(collectTime));
                }
                collectTask = Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    Timer.showTime();
                    collectRunnableTask.cancel();
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == block) {
                            players.sendMessage(config.getString("forceBlock.passedMessage"));
                            if (!chooseInstruction.isCancelled()) {
                                chooseInstruction.cancel();
                            }
                            if (!collectTask.isCancelled()) {
                                collectTask.cancel();
                            }
                            start();
                        } else {
                            Timer.time = 0;
                            Timer.reset();
                            players.sendMessage(config.getString("forceBlock.punishMessage"));
                            players.setGameMode(GameMode.SPECTATOR);

                            if (!chooseInstruction.isCancelled()) {
                                chooseInstruction.cancel();
                            }
                            if (!collectTask.isCancelled()) {
                                collectTask.cancel();
                            }
                        }
                    }
                }, rndTime2);
            }, rndTime);
        }
    }


    private static String convertedTime(int collectTime) {
        int minutes = collectTime % 3600 / 60;
        int seconds = collectTime % 60;
        return String.format("§b%02d§7:§b%02d", minutes, seconds);
    }

    private static void addMaterials() {
        for (Material mat : Material.values()) {
            if (mat.isBlock()) {
                materials.add(mat);
            }
        }
    }
}
