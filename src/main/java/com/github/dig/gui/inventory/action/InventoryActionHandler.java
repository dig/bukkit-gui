package com.github.dig.gui.inventory.action;

import com.github.dig.gui.inventory.InventoryGui;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;

public abstract class InventoryActionHandler {

    public abstract Set<InventoryAction> getActions();

    public abstract void handle(InventoryClickEvent event, InventoryGui inventoryGui);
}
