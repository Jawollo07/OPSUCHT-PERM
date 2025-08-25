package net.opsucht.bukkit.provider;

import net.opsucht.api.PermissionProvider;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.query.QueryOptions;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class LuckPermsProvider implements PermissionProvider {
    private final LuckPerms api;

    public LuckPermsProvider() {
        this.api = Bukkit.getServicesManager().getRegistration(LuckPerms.class).getProvider();
    }

    public @NotNull String getProviderName() {
        return "LuckPerms";
    }

    public boolean has(@NotNull UUID uuid, @NotNull String permission) {
        User user = api.getUserManager().loadUser(uuid).join();
        if (user == null)
            return false;

        return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    public void add(@NotNull UUID uuid, @NotNull String permission) {
        User user = api.getUserManager().loadUser(uuid).join();
        if (user != null) {
            user.data().add(Node.builder(permission).build());
            api.getUserManager().saveUser(user);

        }
    }

    public void remove(@NotNull UUID uuid, @NotNull String permission) {
        User user = api.getUserManager().loadUser(uuid).join();
        if (user != null) {
            user.data().remove(Node.builder(permission).build());
            api.getUserManager().saveUser(user);

        }
    }

    public @NotNull Set<String> getGroups(@NotNull UUID uuid) {
        User user = api.getUserManager().loadUser(uuid).join();
        if (user == null) {
            return Collections.emptySet();
        }
        return user.getNodes().stream()
                .filter(node -> node.getKey().startsWith("group."))
                .map(node -> node.getKey().substring(6))
                .collect(Collectors.toSet());
    }

    public @NotNull Set<String> getGroups() {
        return api.getGroupManager().getLoadedGroups().stream()
                .map(Group::getName)
                .collect(Collectors.toSet());
    }
}