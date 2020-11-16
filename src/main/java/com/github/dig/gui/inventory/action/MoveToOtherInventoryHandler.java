package com.github.dig.gui.inventory.action;

import com.github.dig.gui.inventory.InventoryGui;
import com.github.dig.gui.state.GuiItemChangeState;
import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Set;

public class MoveToOtherInventoryHandler extends InventoryActionHandler {

    private final static Set<InventoryAction> actions = ImmutableSet.of(InventoryAction.MOVE_TO_OTHER_INVENTORY);

    @Override
    public Set<InventoryAction> getActions() {
        return actions;
    }

    @Override
    public void handle(InventoryClickEvent event, InventoryGui inventoryGui) {
        Inventory topInventory = event.getView().getTopInventory();
        Inventory bottomInventory = event.getView().getBottomInventory();

        GuiItemChangeState guiItemChangeState = new GuiItemChangeState(
                (Player) event.getWhoClicked(),
                event.getCursor(), event.getCurrentItem(),
                event.getRawSlot(), event.getSlotType(), event.getClick());

        if (event.getClickedInventory() == topInventory) {
            if (inventoryGui.isControllable(event.getRawSlot())) {
                inventoryGui.handleItemChange(guiItemChangeState);
            } else {
                event.setCancelled(true);
            }
        } else if (event.getClickedInventory() == bottomInventory) {
            event.setCancelled(true);
            inventoryGui.handleItemChange(guiItemChangeState);
        }
    }
}
