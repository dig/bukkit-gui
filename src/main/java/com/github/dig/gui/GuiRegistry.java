package com.github.dig.gui;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GuiRegistry implements Runnable {

    private final JavaPlugin plugin;
    private final Set<Gui> guis = new HashSet<>();
    private final Map<Gui, Listener> listeners = new HashMap<>();

    public GuiRegistry(@NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.runTaskTimer(plugin, this, 1l, 1l);
    }

    public void register(@NonNull Gui gui) {
        Listener listener = gui.createListener(this);
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

    private int tickCount;

    @Override
    public void run() {
        guis.stream()
                .filter(GuiTickable.class::isInstance)
                .map(GuiTickable.class::cast)
                .forEach(gui -> gui.tick(tickCount));
        tickCount++;
    }
}
