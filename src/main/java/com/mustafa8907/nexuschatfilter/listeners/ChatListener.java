package com.mustafa8907.nexuschatfilter.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import com.mustafa8907.nexuschatfilter.NexusChatFilter;
import com.mustafa8907.nexuschatfilter.manager.FilterManager;

public class ChatListener implements Listener {

    private final NexusChatFilter plugin;
    private final FilterManager filterManager;

    public ChatListener(NexusChatFilter plugin, FilterManager filterManager) {
        this.plugin = plugin;
        this.filterManager = filterManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!filterManager.getConfig().getBoolean("settings.enabled")) return;
        if (filterManager.hasBypass(player)) return;

        if (filterManager.getConfig().getBoolean("anti-caps.enabled")) {
            int threshold = filterManager.getConfig().getInt("anti-caps.threshold-percentage");
            if (filterManager.getCapsPercentage(message) >= threshold) {
                String type = filterManager.getConfig().getString("anti-caps.type", "warning");

                if (type.equalsIgnoreCase("warning")) {
                    event.setMessage(message.toLowerCase());
                    filterManager.sendMessage(player, filterManager.getPrefix() + filterManager.getMessage("anti-caps-warning"));
                } else {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        String violationTier = filterManager.checkBlockedWords(event.getMessage());
        if (!violationTier.equals("clean")) {
            event.setCancelled(true);
            
            String action = filterManager.getConfig().getString("actions." + violationTier, "warn");

            if (action.equalsIgnoreCase("warn")) {
                filterManager.sendMessage(player, filterManager.getPrefix() + filterManager.getMessage("tier-warning"));
            } else {
                String commandToExecute = action.replace("%player%", player.getName());
                Bukkit.getScheduler().runTask(plugin, () -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandToExecute);
                });
            }
        }
    }
                  }
