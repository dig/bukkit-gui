package com.github.dig.gui;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GuiRegistry {

    private final JavaPlugin plugin;
    private final Set<Gui> guis;
    private final Map<Gui, Listener> listeners;

    public GuiRegistry(@NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.guis = new HashSet<>();
        this.listeners = new HashMap<>();
    }

    public void register(@NonNull Gui gui) {
        Listener listener = gui.createListener();
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        listeners.put(gui, listener);
        guis.add(gui);
    }

    public void unregister(@NonNull Gui gui) {
        if (guis.remove(gui)) {
            HandlerList.unregisterAll(listeners.get(gui));
            listeners.remove(gui);
        }
    }

    public boolean isRegistered(@NonNull Gui gui) {
        return guis.contains(gui);
    }
}
