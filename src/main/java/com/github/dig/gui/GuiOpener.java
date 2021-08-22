package com.github.dig.gui;

import lombok.NonNull;
import org.bukkit.entity.Player;

public class GuiOpener {

    private final GuiRegistry guiRegistry;
    public GuiOpener(GuiRegistry guiRegistry) {
        this.guiRegistry = guiRegistry;
    }

    public void open(Player player, Gui gui) {
        player.closeInventory();
        
        gui.handleOpen(player);
        gui.showTo(player);

        if (!guiRegistry.isRegistered(gui)) {
            guiRegistry.register(gui);
        }
    }
}
