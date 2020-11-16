package com.github.dig.gui.inventory.action;

import com.github.dig.gui.inventory.InventoryGui;
import com.google.common.collect.ImmutableSet;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;

public class CollectToCursorHandler extends InventoryActionHandler {

    private final static Set<InventoryAction> actions = ImmutableSet.of(InventoryAction.COLLECT_TO_CURSOR);

    @Override
    public Set<InventoryAction> getActions() {
        return actions;
    }

    @Override
    public void handle(InventoryClickEvent event, InventoryGui inventoryGui) {
        event.setCancelled(true);
    }
}
