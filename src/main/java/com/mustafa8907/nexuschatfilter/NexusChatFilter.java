package com.mustafa8907.nexuschatfilter;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import com.mustafa8907.nexuschatfilter.listeners.ChatListener;
import com.mustafa8907.nexuschatfilter.manager.FilterManager;

public final class NexusChatFilter extends JavaPlugin implements CommandExecutor {

    private BukkitAudiences adventure;
    private FilterManager filterManager;

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        
        this.filterManager = new FilterManager(this);
        this.filterManager.loadConfigs();

        getServer().getPluginManager().registerEvents(new ChatListener(this, filterManager), this);

        getCommand("nexusfilter").setExecutor(this);

        printStartupLogo();
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        getLogger().info("NexusChatFilter has been disabled successfully.");
    }

    public BukkitAudiences getAdventure() {
        return adventure;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("nexusfilter")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("nexuschatfilter.admin")) {
                    filterManager.loadConfigs();
                    filterManager.sendMessage(sender, filterManager.getPrefix() + filterManager.getMessage("reload-success"));
                    return true;
                } else {
                    filterManager.sendMessage(sender, filterManager.getPrefix() + filterManager.getMessage("no-permission"));
                    return true;
                }
            }
            sender.sendMessage("§cUsage: /nexusfilter reload");
            return true;
        }
        return false;
    }

    private void printStartupLogo() {
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
        String green = "\u001B[32m";
        String reset = "\u001B[0m";

        System.out.println(cyan + "----N-E-X-U-S--S-E-T-U-P-S----" + reset);
        System.out.println(white + "               NexusChatFilter " + green + "(Active)" + reset);
        System.out.println(white + "       website: " + white + "mustafa8907.com.tr" + reset);
        System.out.println(white + "       author: " + white + "mustafa8907" + reset);
        System.out.println(white + "       discord: " + white + "discord.gg/nexussetups" + reset);
        System.out.println(cyan + "------------------------------------" + reset);
    }
}
