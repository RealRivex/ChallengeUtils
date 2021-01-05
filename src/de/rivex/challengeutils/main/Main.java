package de.rivex.challengeutils.main;

import de.rivex.challengeutils.challenges.*;
import de.rivex.challengeutils.gamerules.DamageIndicator;
import de.rivex.challengeutils.gamerules.Timber;
import de.rivex.challengeutils.listeners.ForceLoadChunks;
import de.rivex.challengeutils.listeners.JoinListener;
import de.rivex.challengeutils.listeners.QuitListener;
import de.rivex.challengeutils.utils.Reset;
import de.rivex.challengeutils.utils.SettingsGUI;
import de.rivex.challengeutils.utils.Timer;
import de.rivex.challengeutils.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    public static boolean devMode = false;
    public static UpdateChecker checker;
    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    private void deleteWorld(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null)
                return;
            for (File currentFile : files) {
                if (currentFile.isDirectory()) {
                    deleteWorld(currentFile);
                } else if (!currentFile.getName().equals("session.lock")) {
                    currentFile.delete();
                }
            }
        }
    }

    @Override
    public void onEnable() {
        plugin = this;
        register();
        saveDefaultConfig();
        checker = UpdateChecker.init(this, 85731);
    }

    @Override
    public void onLoad() {
        plugin = this;
        resetWorlds();
    }

    private void register() {
        getCommand("timer").setExecutor(new Timer());
        getCommand("settings").setExecutor(new SettingsGUI());
        getCommand("gamerules").setExecutor(new SettingsGUI());
        getCommand("reset").setExecutor(new Reset());

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new SettingsGUI(), this);
        Bukkit.getPluginManager().registerEvents(new Place(), this);
        Bukkit.getPluginManager().registerEvents(new DamageIndicator(), this);
        Bukkit.getPluginManager().registerEvents(new CraftingTable(), this);
        Bukkit.getPluginManager().registerEvents(new Break(), this);
        Bukkit.getPluginManager().registerEvents(new Sneak(), this);
        Bukkit.getPluginManager().registerEvents(new ForceMLG(), this);
        Bukkit.getPluginManager().registerEvents(new ForceLoadChunks(), this);
        Bukkit.getPluginManager().registerEvents(new OneLine(), this);
        Bukkit.getPluginManager().registerEvents(new Timber(), this);
        Bukkit.getPluginManager().registerEvents(new DamageClear(), this);
    }

    public void resetWorlds() {
        if (!this.getConfig().getBoolean("reset"))
            return;
        String worldName = this.getConfig().getString("worldName");
        String[] worlds = {worldName, worldName + "_nether", worldName + "_the_end"};
        for (String currentWorld : worlds) {
            File worldFolder = new File(currentWorld);
            deleteWorld(worldFolder);
        }
        Main.getPlugin().getConfig().set("reset", false);
        Main.getPlugin().saveConfig();
    }
}