package com.github.dig.gui.inventory.action;

import com.github.dig.gui.inventory.InventoryGui;
import com.github.dig.gui.state.GuiItemChangeState;
import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Set;

public class HotbarSwapHandler extends InventoryActionHandler {

    private final static Set<InventoryAction> ACTIONS = ImmutableSet.of(
            InventoryAction.HOTBAR_SWAP,
            InventoryAction.HOTBAR_MOVE_AND_READD
    );

    @Override
    public Set<InventoryAction> getActions() {
        return ACTIONS;
    }

    @Override
    public void handle(InventoryClickEvent event, InventoryGui inventoryGui) {
        if (event.getClickedInventory() == event.getView().getTopInventory()
                && !inventoryGui.isControllable(event.getRawSlot())) {
            event.setCancelled(true);
        } else {
            inventoryGui.handleItemChange(new GuiItemChangeState(
                    (Player) event.getWhoClicked(),
                    event.getCursor(), event.getCurrentItem(),
                    event.getRawSlot(), event.getSlotType(), event.getClick()));
        }
    }
}
