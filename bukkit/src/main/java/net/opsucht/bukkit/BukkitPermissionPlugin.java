package net.opsucht.bukkit;

import net.opsucht.api.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import net.opsucht.bukkit.provider.LuckPermsProvider;

public final class BukkitPermissionPlugin extends JavaPlugin {
    // Starting
    @Override
    public void onEnable() {
        // Check what permission plugin is available
        if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            Permission.set(new LuckPermsProvider());
            getLogger().info("Using LuckPerms for permissions");
        } // else if (getServer().getPluginManager().getPlugin("GroupManager") != null) {
          // Permission.set(new GroupManagerProvider());
          // getLogger().info("Using GroupManager for permissions");
          // } // else if (getServer().getPluginManager().getPlugin("PermissionsEx") !=
          // null) {
          // Permission.set(new PEXProvider());
          // getLogger().info("Using PermissionsEx for permissions");
        else {
            getLogger().warning("No supported permission plugin found. Permissions will not work.");
        }
    }

    @Override
    public void onDisable() {
        Permission.set(null);
        getLogger().info("Permission API uninitialized");
    }

}
