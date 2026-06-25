package com.mustafa8907.nexuschatfilter.manager;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import com.mustafa8907.nexuschatfilter.NexusChatFilter;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterManager {

    private final NexusChatFilter plugin;
    private FileConfiguration config;
    private FileConfiguration messages;
    private FileConfiguration blockedWords;

    private Set<String> tier1Words;
    private Set<String> tier2Words;
    private Set<String> tier3Words;

    public FilterManager(NexusChatFilter plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        createFile("config.yml");
        createFile("messages.yml");
        createFile("Blocked-Words.yml");

        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        messages = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "messages.yml"));
        blockedWords = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "Blocked-Words.yml"));

        tier1Words = new HashSet<>(blockedWords.getStringList("tier-1"));
        tier2Words = new HashSet<>(blockedWords.getStringList("tier-2"));
        tier3Words = new HashSet<>(blockedWords.getStringList("tier-3"));
    }

    private void createFile(String name) {
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }
    }

    public boolean hasBypass(Player player) {
        if (player.isOp()) return true;
        List<String> bypassRoles = config.getStringList("settings.bypass-roles");
        for (String role : bypassRoles) {
            if (player.hasPermission("nexuschatfilter.bypass." + role)) {
                return true;
            }
        }
        return false;
    }

    public int getCapsPercentage(String message) {
        if (message.isEmpty()) return 0;
        int upperCaseCount = 0;
        int letterCount = 0;

        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                letterCount++;
                if (Character.isUpperCase(c)) {
                    upperCaseCount++;
                }
            }
        }
        if (letterCount == 0) return 0;
        return (upperCaseCount * 100) / letterCount;
    }

    public String checkBlockedWords(String message) {
        String lowerMsg = message.toLowerCase();
        
        for (String word : tier1Words) {
            if (lowerMsg.contains(word.toLowerCase())) return "tier-1";
        }
        for (String word : tier2Words) {
            if (lowerMsg.contains(word.toLowerCase())) return "tier-2";
        }
        for (String word : tier3Words) {
            if (lowerMsg.contains(word.toLowerCase())) return "tier-3";
        }
        return "clean";
    }

    public void sendMessage(CommandSender sender, String miniMessageText) {
        plugin.getAdventure().sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(miniMessageText));
    }

    public String getPrefix() {
        return messages.getString("messages.prefix", "");
    }

    public String getMessage(String path) {
        return messages.getString("messages." + path, "");
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
