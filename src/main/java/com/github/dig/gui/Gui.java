package com.github.dig.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public interface Gui {

    void showTo(Player player);

    void removeViewer(Player player);

    boolean isViewer(Player player);

    int getViewerCount();

    Listener createListener(GuiRegistry guiRegistry);

    default void handleOpen(Player player) {}

    default void handleClose(Player player) {}
}
