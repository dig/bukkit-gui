package com.github.dig.gui.inventory.action;

import com.github.dig.gui.inventory.InventoryGui;
import com.github.dig.gui.state.ComponentClickState;
import com.github.dig.gui.state.GuiItemChangeState;
import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;

public class StandardClickHandler extends InventoryActionHandler {

    private final static Set<InventoryAction> ACTIONS = ImmutableSet.of(
            InventoryAction.CLONE_STACK,
            InventoryAction.DROP_ALL_CURSOR,
            InventoryAction.DROP_ALL_SLOT,
            InventoryAction.DROP_ONE_CURSOR,
            InventoryAction.DROP_ONE_SLOT,
            InventoryAction.PICKUP_ALL,
            InventoryAction.PICKUP_HALF,
            InventoryAction.PICKUP_ONE,
            InventoryAction.PICKUP_SOME,
            InventoryAction.PLACE_ALL,
            InventoryAction.PLACE_ONE,
            InventoryAction.PLACE_SOME,
            InventoryAction.SWAP_WITH_CURSOR
    );

    @Override
    public Set<InventoryAction> getActions() {
        return ACTIONS;
    }

    @Override
    public void handle(InventoryClickEvent event, InventoryGui inventoryGui) {
        if (event.getClickedInventory() == event.getView().getTopInventory()
                && !inventoryGui.isControllable(event.getSlot())) {
            event.setCancelled(true);
            inventoryGui.handleComponentClick(new ComponentClickState(
                    (Player) event.getWhoClicked(),
                    event.getCursor(), event.getCurrentItem(),
                    event.getRawSlot(), event.getSlotType(), event.getClick()));
        } else {
            inventoryGui.handleItemChange(new GuiItemChangeState(
                    (Player) event.getWhoClicked(),
                    event.getCursor(), event.getCurrentItem(),
                    event.getRawSlot(), event.getSlotType(), event.getClick()));
        }
    }
}
