package net.opsucht.permission.bukkit;

import net.opsucht.permission.api.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import net.opsucht.permission.bukkit.providers.LuckPermsProvider;
import net.opsucht.permission.bukkit.providers.GroupManagerProvider;
import net.opsucht.permission.bukkit.providers.PEXProvider;

public final class BukkitPermissionPlugin extends JavaPlugin {
    // Starting
    @Override
    public void onEnable() {
        // Check what permission plugin is available
        if (getServer().getPluginManager().getPlugin("Luckperms") != null) {
            Permission.set(new LuckPermsProvider());
            getLogger().info("Using LuckPerms for permissions");
        } else if (getServer().getPluginManager().getPlugin("GroupManager") != null) {
            Permission.set(new GroupManagerProvider());
            getLogger().info("Using GroupManager for permissions");
        } else if (getServer().getPluginManager().getPlugin("PermissionsEx") != null) {
            Permission.set(new PEXProvider());
            getLogger().info("Using PermissionsEx for permissions");
        } else {
            getLogger().warning("No supported permission plugin found. Permissions will not work.");
        }
    }

    @Override
    public void onDisable() {
        Permission.set(null);
        getLogger().info("Permission API uninitialized");
    }

}
