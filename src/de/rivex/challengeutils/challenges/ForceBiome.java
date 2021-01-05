package de.rivex.challengeutils.challenges;

import de.rivex.challengeutils.main.Main;
import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;

public class ForceBiome implements Listener {

    private static BukkitTask collectRunnableTask;
    private static ArrayList<Biome> biomes = new ArrayList<>();
    private static BukkitTask chooseInstruction;
    private static int collectTime;
    private static BukkitTask collectTask;
    private static FileConfiguration config = Main.getPlugin().getConfig();
    private static ArrayList<Player> passedPlayers = new ArrayList<>();

    public static void start() {
        if (SettingsGUI.forceBiomeChallenge) {
            int min = config.getInt("forceBiome.maxTime");
            int max = config.getInt("forceBiome.maxTime");
            int rndTime = new Random().nextInt((max - min) + 1) + min;
            chooseInstruction = Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                addBiomes();
                Biome biome = biomes.get(new Random().nextInt(Biome.values().length));
                Timer.showTimeTask.cancel();
                int minCollect = config.getInt("forceBiome.minCollectTime");
                int maxCollect = config.getInt("forceBiome.maxCollectTime");
                int rndTime2 = new Random().nextInt((maxCollect - minCollect) + 1) + minCollect;
                collectTime = rndTime2 / 20;
                collectRunnableTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), () -> {
                    collectTime--;
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aAnweisung: §9" + biome.toString().replace("_", " ") + " §ain §9" + convertedTime(collectTime)));
                    }
                }, 0L, 20L);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage("§7[§bForceBiome§7] §aAnweisung: Stehe in dem Biom §9" + biome.toString().toUpperCase().replace("_", " ") + " §ain §9" + convertedTime(collectTime));
                }
                collectTask = Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                    Timer.showTime();
                    collectRunnableTask.cancel();
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getLocation().getBlock().getBiome().equals(biome)) {
                            passedPlayers.add(players);
                            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                                if (passedPlayers.size() == Bukkit.getOnlinePlayers().size()) {
                                    players.sendMessage(config.getString("forceBiome.passedMessage"));
                                    if (!chooseInstruction.isCancelled()) {
                                        chooseInstruction.cancel();
                                    }
                                    if (!collectTask.isCancelled()) {
                                        collectTask.cancel();
                                    }
                                    passedPlayers.clear();
                                }
                            }, 20L);
                            start();
                        } else {
                            Timer.time = 0;
                            Timer.reset();
                            players.sendMessage(config.getString("forceBiome.punishMessage"));
                            players.setGameMode(GameMode.SPECTATOR);

                            if (!chooseInstruction.isCancelled()) {
                                chooseInstruction.cancel();
                            }
                            if (!collectTask.isCancelled()) {
                                collectTask.cancel();
                            }
                            passedPlayers.clear();
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

    private static void addBiomes() {
        for (Biome biome : Biome.values()) {
            if (!biome.equals(Biome.THE_VOID)) {
                biomes.add(biome);
            }
        }
    }
}