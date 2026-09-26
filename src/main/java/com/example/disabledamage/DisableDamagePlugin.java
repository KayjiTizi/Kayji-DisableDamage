package com.example.disabledamage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DisableDamagePlugin extends JavaPlugin implements Listener {

    private boolean disableTntDamage;
    private boolean disableAnchorDamage;
    private boolean pluginEnabled;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSettings();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("DisableDamagePlugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("DisableDamagePlugin disabled.");
    }

    private void loadSettings() {
        FileConfiguration config = getConfig();
        pluginEnabled = config.getBoolean("plugin_enabled", true);
        disableTntDamage = config.getBoolean("disable_tnt_damage", true);
        disableAnchorDamage = config.getBoolean("disable_respawn_anchor_damage", true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!pluginEnabled || !(event.getEntity() instanceof Player)) return;

        if (disableTntDamage && event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            event.setCancelled(true);
        }

        if (disableAnchorDamage && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                && event.getEntity().getLocation().getBlock().getType() == Material.RESPAWN_ANCHOR) {
            event.setCancelled(true);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "/disabledamage <tnt|anchor|on|off> <on|off>");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("disabledamage.use")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length == 1) {
            String main = args[0].toLowerCase();
            if (main.equals("on")) {
                pluginEnabled = true;
                getConfig().set("plugin_enabled", true);
                saveConfig();
                sender.sendMessage(ChatColor.GREEN + "Plugin is now enabled.");
                return true;
            } else if (main.equals("off")) {
                pluginEnabled = false;
                getConfig().set("plugin_enabled", false);
                saveConfig();
                sender.sendMessage(ChatColor.YELLOW + "Plugin is now disabled.");
                return true;
            }
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "/disabledamage <tnt|anchor|on|off> <on|off>");
            return true;
        }

        String type = args[0].toLowerCase();
        boolean enable = args[1].equalsIgnoreCase("on");

        switch (type) {
            case "tnt":
                disableTntDamage = enable;
                getConfig().set("disable_tnt_damage", enable);
                break;
            case "anchor":
                disableAnchorDamage = enable;
                getConfig().set("disable_respawn_anchor_damage", enable);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown type: " + type);
                return true;
        }

        saveConfig();
        sender.sendMessage(ChatColor.GREEN + "Updated " + type + " damage protection to: " + (enable ? "enabled" : "disabled"));
        return true;
    }
}
