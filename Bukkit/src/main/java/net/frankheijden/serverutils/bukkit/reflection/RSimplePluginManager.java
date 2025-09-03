package net.frankheijden.serverutils.bukkit.reflection;

import dev.frankheijden.minecraftreflection.MinecraftReflection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import dev.frankheijden.minecraftreflection.exceptions.MinecraftReflectionException;
import net.frankheijden.serverutils.bukkit.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class RSimplePluginManager {
    private static final MinecraftReflection reflection = MinecraftReflection.of(Bukkit.getPluginManager().getClass());
    private static final MinecraftReflection paperPluginManagerReflection;
    private static final MinecraftReflection paperInstanceManagerReflection;

    static {
        MinecraftReflection pluginManagerReflection;
        MinecraftReflection instanceManagerReflection;
        try {
            pluginManagerReflection =
                    MinecraftReflection.of("io.papermc.paper.plugin.manager.PaperPluginManagerImpl");
            instanceManagerReflection =
                    MinecraftReflection.of("io.papermc.paper.plugin.manager.PaperPluginInstanceManager");
        } catch (MinecraftReflectionException e) {
            pluginManagerReflection = null;
            instanceManagerReflection = null;
        }
        paperPluginManagerReflection = pluginManagerReflection;
        paperInstanceManagerReflection = instanceManagerReflection;
    }

    public static MinecraftReflection getReflection() {
        return reflection;
    }

    /**
     * Gets a list of plugins.
     * @param manager The SimplePluginManager instance to get plugins from.
     * @return list of plugins.
     */
    public static List<Plugin> getPlugins(Object manager) {
        if (paperPluginManagerReflection == null) {
            return reflection.get(manager, "plugins");
        }
        return paperInstanceManagerReflection.get(getInstanceManager(manager), "plugins");
    }

    /**
     * Removes the lookup name of the plugin.
     * This ensures the plugin cannot be found anymore in Bukkit#getPlugin(String name).
     * @param manager The SimplePluginManager instance to remove the lookup name from.
     * @param name The name of the plugin to remove.
     */
    public static void removeLookupName(Object manager, String name) {
        Map<String, Plugin> lookupNames;
        if (paperPluginManagerReflection == null) {
            lookupNames = reflection.get(manager, "lookupNames");
        } else {
            lookupNames = paperInstanceManagerReflection.get(getInstanceManager(manager), "lookupNames");
        }
        if (lookupNames == null) {
            ServerUtils.getInstance().getLogger()
                    .warning("Cannot remove lookup name '" + name + "' because lookupNames is null");
            return;
        }
        lookupNames.remove(name.replace(' ', '_'));
        lookupNames.remove(name.replace(' ', '_').toLowerCase(Locale.ENGLISH)); // Paper
    }

    private static Object getInstanceManager(Object manager) {
        Object paperPluginManager = reflection.get(manager, "paperPluginManager");
        return paperPluginManagerReflection.get(paperPluginManager, "instanceManager");
    }
}
